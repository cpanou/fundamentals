package gr.haec.employees.registry.employee;

import gr.haec.employees.registry.company.Company;
import gr.haec.employees.registry.company.CompanyRepository;
import gr.haec.employees.registry.department.Department;
import gr.haec.employees.registry.department.DepartmentRepository;
import gr.haec.employees.registry.exception.CompanyNotFoundException;
import gr.haec.employees.registry.exception.DepartmentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private CompanyRepository companyRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private EmployeeMapper mapper;

    public List<EmployeeResponse> getEmployeesForCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company == null)
            throw new CompanyNotFoundException();
        return company.getDepartmentList().stream()
                .flatMap(department -> department.getEmployeeList().stream())
                .map(employee -> mapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> getEmployeesForDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null)
            throw new DepartmentNotFoundException();
        return department.getEmployeeList().stream()
                .map(employee -> mapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    public EmployeeResponse registerEmployee(EmployeeRequest request) {
        Department department = departmentRepository.findById(request.getDepartment()).orElse(null);
        if(department == null)
            throw new DepartmentNotFoundException();
        Employee employee = mapper.fromRequest(request);
        employee.setDepartment(department);
        String encodedPass = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPass);
        return mapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if(employee == null)
            throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), new ArrayList<>());

    }
}

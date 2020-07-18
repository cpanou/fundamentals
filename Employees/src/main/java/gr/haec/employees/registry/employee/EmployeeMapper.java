package gr.haec.employees.registry.employee;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeMapper {

    private Function<Employee, EmployeeResponse> toResponse = employee -> EmployeeResponse.builder()
            .id(employee.getId())
            .username(employee.getUsername())
            .fullname(employee.getLastname() + " " + employee.getFirstname())
            .address(employee.getAddress())
            .phone(employee.getPhone())
            .workingPeriod( employee.getHireDate().toString() + " - " + (employee.getLeaveDate() != null ? employee.getLeaveDate().toString() : "PRESENT"))
            .status(employee.getLeaveDate() != null ? "INACTIVE" : "ACTIVE")
            .position(employee.getPosition())
            .departement(employee.getDepartment().getName())
            .build();

    private Function<EmployeeRequest, Employee> fromRequest = employeeRequest -> Employee.builder()
            .username(employeeRequest.getUsername())
            .lastname(employeeRequest.getLastname())
            .firstname(employeeRequest.getFirstname())
            .password(employeeRequest.getPassword())
            .address(employeeRequest.getAddress())
            .phone(employeeRequest.getPhone())
            .position(employeeRequest.getPosition())
            .build();


    public Employee fromRequest(EmployeeRequest employeeRequest) {
        return  this.fromRequest.apply(employeeRequest);
    }

    public EmployeeResponse toResponse(Employee employee) {
        return  this.toResponse.apply(employee);
    }

}

package gr.haec.employees.registry.employee;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> registerEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.registerEmployee(employeeRequest));
    }

    @GetMapping(value = "/department/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getEmployeesForDepartment(@PathVariable("id") Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesForDepartment(departmentId));
    }

    @GetMapping(value = "/company/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getEmployeesForCompany(@PathVariable("id") Long companyId) {
        return ResponseEntity.ok(employeeService.getEmployeesForCompany(companyId));
    }


}

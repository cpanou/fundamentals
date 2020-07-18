package gr.haec.employees.registry.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
    private Long id;
    private String fullname;
    private String username;
    private String address;
    private String phone;
    private String status;
    private String workingPeriod;
    private String position;
    private String departement;
}

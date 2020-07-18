package gr.haec.employees.registry.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String address;
    private String phone;
    private LocalDateTime hireDate;
    private LocalDateTime leaveDate;
    private String position;
    private Long department;
}

package gr.haec.employees.registry.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gr.haec.employees.registry.department.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String taxNumber;

    @OneToMany(mappedBy = "company")
    @JsonBackReference
    private List<Department> departmentList;
}

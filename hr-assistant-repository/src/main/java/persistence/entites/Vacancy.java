package persistence.entites;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt = new Date();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;


    @Builder
    public Vacancy(Long id, String title, String description, String requirements, String conditions, BigDecimal salary, Assignment assignment) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.conditions = conditions;
        this.salary = salary;
        this.assignment = assignment;
    }
}

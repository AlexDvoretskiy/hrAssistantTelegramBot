package persistence.entites;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@Table(name = "assignment_results")
public class AssignmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id")
    private Vacancy vacancy;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "passed_at")
    @Temporal(TemporalType.DATE)
    private Date passDate = new Date();


    @Builder
    public AssignmentResult(Candidate candidate, Vacancy vacancy, String fileName) {
        this.candidate = candidate;
        this.vacancy = vacancy;
        this.fileName = fileName;
    }
}

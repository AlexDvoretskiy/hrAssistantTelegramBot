package persistence.entites;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import persistence.enums.Stage;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "stage_id")
    @Enumerated(EnumType.ORDINAL)
    private Stage stage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "apply_date")
    private Date applyDate = new Date();

    @Column(name = "cv_file_name")
    private String cvFileName;

    @Column(name = "last_update")
    private Date lastUpdateDate = new Date();


    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinTable(name = "candidates_vacancies",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id"))
    private Collection<Vacancy> vacancyList;

    @OneToMany(mappedBy = "candidate", cascade=CascadeType.ALL)
    private List<AssignmentResult> assignmentResults;


    @Builder
    public Candidate(Long id, Long chatId, Stage stage, String firstName, String middleName, String lastName, Date dateOfBirth, String email, String phone, String cvFileName, Collection<Vacancy> vacancyList) {
        this.id = id;
        this.chatId = chatId;
        this.stage = stage;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.cvFileName = cvFileName;
        this.vacancyList = vacancyList;
    }
}

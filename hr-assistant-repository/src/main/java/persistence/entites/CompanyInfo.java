package persistence.entites;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "active")
    private boolean active = true;


    @Builder
    public CompanyInfo(Long id, String name, String description, String contacts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contacts = contacts;
    }
}

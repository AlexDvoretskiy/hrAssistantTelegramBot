package persistence.entites;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt = new Date();


    @Builder
    public Assignment(String fileName, String description, boolean active) {
        this.fileName = fileName;
        this.description = description;
        this.active = active;
    }
}

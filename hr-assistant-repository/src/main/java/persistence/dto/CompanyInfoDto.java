package persistence.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CompanyInfoDto {
    private Long id;
    private String name;
    private String description;
    private String contacts;

    @Builder
    public CompanyInfoDto(Long id, String name, String description, String contacts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contacts = contacts;
    }

    public String getHtmlDescription() {
        return  "<strong>О компании: </strong> \n" +
                description + "\n\n" +
                "<strong>Контакты: </strong> \n" +
                contacts;
    }
}

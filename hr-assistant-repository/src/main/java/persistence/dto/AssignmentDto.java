package persistence.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Data
@NoArgsConstructor
public class AssignmentDto {
    private Long id;
    private String fileName;
    private String description;
    private boolean active;

    @Builder
    public AssignmentDto(Long id, String fileName, String description, boolean active) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.active = active;
    }

    public AssignmentDto(String fileName, String description, boolean active) {
        this.fileName = fileName;
        this.description = description;
        this.active = active;
    }

    public AssignmentDto(String description) {
        this.description = description;
    }

    public boolean hasDescription() {
        return StringUtils.isNotEmpty(description);
    }
}

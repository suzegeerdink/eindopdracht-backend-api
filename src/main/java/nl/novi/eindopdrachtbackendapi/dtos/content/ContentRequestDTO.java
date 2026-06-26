package nl.novi.eindopdrachtbackendapi.dtos.content;

import jakarta.validation.constraints.*;

public class ContentRequestDTO {
    @NotBlank
    @Size(min = 1, max = 100)
    private String title;
    @NotBlank
    @Size(min = 10, max = 500)
    private String description;
    @NotNull
    @Min(0)
    @Max(21)
    private int ageClassification;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeClassification() {
        return ageClassification;
    }

    public void setAgeClassification(int ageClassification) {
        this.ageClassification = ageClassification;
    }
}

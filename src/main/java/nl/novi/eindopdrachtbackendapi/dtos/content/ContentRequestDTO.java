package nl.novi.eindopdrachtbackendapi.dtos.content;

import jakarta.validation.constraints.NotBlank;

public class ContentRequestDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
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

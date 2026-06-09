package nl.novi.eindopdrachtbackendapi.dtos.content;

import java.util.Objects;

public class ContentResponseDTO {
    private Long id;
    private String title;
    private String description;
    private int ageClassification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContentResponseDTO that = (ContentResponseDTO) o;
        return ageClassification == that.ageClassification &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, ageClassification);
    }
}

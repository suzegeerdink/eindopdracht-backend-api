package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "films")
public class FilmEntity extends ContentEntity {

    @Column(nullable = false)
    private int duration;

    public FilmEntity() {}

    public FilmEntity(String title, String description, int ageClassification, int duration) {
        super(title, description, ageClassification);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}

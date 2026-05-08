package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class SeriesEntity extends ContentEntity {

    @Column(nullable = false)
    private int numberOfEpisodes;

    @Column(nullable = false)
    private int numberOfSeasons;

    public SeriesEntity() {}

    public SeriesEntity(String title, String description, int ageClassification, int numberOfEpisodes, int numberOfSeasons) {
        super(title, description, ageClassification);
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
}

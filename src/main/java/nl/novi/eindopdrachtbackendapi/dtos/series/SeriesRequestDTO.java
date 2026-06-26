package nl.novi.eindopdrachtbackendapi.dtos.series;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import nl.novi.eindopdrachtbackendapi.dtos.content.ContentRequestDTO;

public class SeriesRequestDTO extends ContentRequestDTO {
    @NotNull
    @Positive
    private int numberOfEpisodes;
    @NotNull
    @Positive
    @Max(50)
    private int numberOfSeasons;

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

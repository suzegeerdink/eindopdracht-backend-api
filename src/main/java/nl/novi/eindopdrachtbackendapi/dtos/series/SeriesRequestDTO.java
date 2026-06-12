package nl.novi.eindopdrachtbackendapi.dtos.series;

import jakarta.validation.constraints.NotNull;
import nl.novi.eindopdrachtbackendapi.dtos.content.ContentRequestDTO;

public class SeriesRequestDTO extends ContentRequestDTO {
    @NotNull
    private int numberOfEpisodes;
    @NotNull
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

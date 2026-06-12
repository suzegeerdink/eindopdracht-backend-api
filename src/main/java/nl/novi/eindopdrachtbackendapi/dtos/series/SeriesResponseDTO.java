package nl.novi.eindopdrachtbackendapi.dtos.series;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;

public class SeriesResponseDTO extends ContentResponseDTO {
    private int numberOfEpisodes;
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

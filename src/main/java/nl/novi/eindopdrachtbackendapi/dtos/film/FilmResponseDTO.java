package nl.novi.eindopdrachtbackendapi.dtos.film;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;

public class FilmResponseDTO extends ContentResponseDTO {
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

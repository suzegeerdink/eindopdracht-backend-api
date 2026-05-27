package nl.novi.eindopdrachtbackendapi.dtos.film;

import jakarta.validation.constraints.NotNull;
import nl.novi.eindopdrachtbackendapi.dtos.content.ContentRequestDTO;

public class FilmRequestDTO extends ContentRequestDTO {
    @NotNull
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
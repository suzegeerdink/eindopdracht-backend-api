package nl.novi.eindopdrachtbackendapi.dtos.film;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import nl.novi.eindopdrachtbackendapi.dtos.content.ContentRequestDTO;

public class FilmRequestDTO extends ContentRequestDTO {
    @NotNull
    @Positive
    @Max(600)
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
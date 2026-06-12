package nl.novi.eindopdrachtbackendapi.dtos.genre;

import jakarta.validation.constraints.NotNull;

public class GenreRequestDTO {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.director.NewDirectorRequest;
import ru.yandex.practicum.filmorate.dto.director.UpdateDirectorRequest;
import ru.yandex.practicum.filmorate.model.Director;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorMapper {
    public static Director mapToDirector(NewDirectorRequest request) {
        Director director = new Director();
        director.setFirstName(request.getFirstName());
        director.setLastName(request.getLastName());
        director.setFilms(request.getFilms());
        return director;
    }

    public static DirectorDto mapToDirectorDto(Director director) {
        DirectorDto dto = new DirectorDto();
        dto.setId(director.getId());
        dto.setFirstName(director.getFirstName());
        dto.setLastName(director.getLastName());
        dto.setFilms(director.getFilms());
        return dto;
    }

    public static void updateDirectorFields(Director director, UpdateDirectorRequest request) {
        if (request.hasName()) {
            director.setFirstName(request.getFirstName());
        }

        if (request.hasLastName()) {
            director.setLastName(request.getLastName());
        }

        if (request.hasFilms()) {
            director.setFilms(request.getFilms());
        }
    }
}

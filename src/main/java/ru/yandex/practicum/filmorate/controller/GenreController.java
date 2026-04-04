package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public final class GenreController {
    private final GenreService genreService;

    /**
     *     GET    /genres
     *     GET    /genres/{id}
     */

    @GetMapping
    public List<GenreDto> getUsers() {
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable final long id) {
        return genreService.getById(id);
    }
}
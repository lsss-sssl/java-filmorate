package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public List<GenreDto> getAll() {
        return genreStorage.findAll().stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }

    public GenreDto getById(long genreId) {
        return GenreMapper.mapToGenreDto(findByIdOrThrow(genreId));
    }

    private Genre findByIdOrThrow(final long genreId) {
        return genreStorage.findById(genreId).orElseThrow(() -> new NotFoundException("Genre not Found"));
    }
}
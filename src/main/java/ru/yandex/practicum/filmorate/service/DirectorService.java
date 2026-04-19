package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.director.NewDirectorRequest;
import ru.yandex.practicum.filmorate.dto.director.UpdateDirectorRequest;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.DirectorMapper;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorService {
    private final DirectorStorage directorStorage;
    private final FilmStorage filmStorage;

    public List<DirectorDto> getAll() {
        log.debug("Request to get all directors");
        return directorStorage.findAll().stream().map(DirectorMapper::mapToDirectorDto).collect(Collectors.toList());
    }

    public DirectorDto findById(final long directorID) {
        log.debug("Request to find director by id={}", directorID);
        return DirectorMapper.mapToDirectorDto(findByIdOrThrow(directorID));
    }

    public List<FilmDto> findFilmsSorted(Long directorId, String sortBy) {
        log.debug("Request to get popular films: directorId={}, sortBy={}", directorId, sortBy);
        return directorStorage.findFilmsSorted(directorId, sortBy).stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public DirectorDto create(NewDirectorRequest request) {
        log.debug("Creating director: firstName{}, lastName{}", request.getName());

        Director director = DirectorMapper.mapToDirector(request);
        DirectorDto dto = DirectorMapper.mapToDirectorDto(directorStorage.save(director));
        log.info("Director created: id={}", dto.getId());
        return dto;
    }

    public DirectorDto updateDirector(UpdateDirectorRequest request) {
        log.debug("Updating director: id={}", request.getId());
        Director oldDirctor = findByIdOrThrow(request.getId());
        DirectorMapper.updateDirectorFields(oldDirctor, request);
        directorStorage.update(oldDirctor);
        return DirectorMapper.mapToDirectorDto(oldDirctor);
    }

    public void deleteDirector(long directorId) {
        log.debug("Deleting director with id{}", directorId);
        findByIdOrThrow(directorId);
        directorStorage.deleteById(directorId);
    }

    private Director findByIdOrThrow(final long directorId) {
        return directorStorage.findById(directorId)
                .orElseThrow(() -> new NotFoundException("director with id = " + directorId + " not found"));
    }
}

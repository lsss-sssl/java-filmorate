package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.director.NewDirectorRequest;
import ru.yandex.practicum.filmorate.dto.director.UpdateDirectorRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.DirectorMapper;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorService {
    private final DirectorStorage directorStorage;

    public List<DirectorDto> getAll() {
        log.debug("Request to get all directors");
        return directorStorage.findAll().stream()
                .map(DirectorMapper::mapToDirectorDto)
                .collect(Collectors.toList());
    }

    public DirectorDto getById(final long directorId) {
        log.debug("Request to find director by id={}", directorId);
        return DirectorMapper.mapToDirectorDto(findByIdOrThrow(directorId));
    }

    public DirectorDto create(NewDirectorRequest request) {
        log.debug("Creating director: name={}", request.getName());
        Director director = DirectorMapper.mapToDirector(request);
        DirectorDto dto = DirectorMapper.mapToDirectorDto(directorStorage.save(director));
        log.info("Director created: id={}", dto.getId());
        return dto;
    }

    public DirectorDto update(UpdateDirectorRequest request) {
        log.debug("Updating director: id={}", request.getId());
        Director oldDirctor = findByIdOrThrow(request.getId());
        DirectorMapper.updateDirectorFields(oldDirctor, request);
        directorStorage.update(oldDirctor);
        return DirectorMapper.mapToDirectorDto(oldDirctor);
    }

    @Transactional
    public void deleteDirector(long directorId) {
        log.debug("Deleting director with id={}", directorId);
        findByIdOrThrow(directorId);
        directorStorage.deleteById(directorId);
    }

    private Director findByIdOrThrow(final long directorId) {
        return directorStorage.findById(directorId)
                .orElseThrow(() -> new NotFoundException("Director not found by id=" + directorId));
    }
}
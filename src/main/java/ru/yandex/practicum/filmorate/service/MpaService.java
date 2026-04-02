package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public List<MpaDto> getAll() {
        return mpaStorage.findAll().stream()
                .map(MpaMapper::mapToMpaDto)
                .collect(Collectors.toList());
    }

    public MpaDto getById(final long mpaId) {
        return MpaMapper.mapToMpaDto(findByIdOrThrow(mpaId));
    }

    private Mpa findByIdOrThrow(final long mpaId) {
        return mpaStorage.findById(mpaId).orElseThrow(() -> new NotFoundException("Mpa not found"));
    }
}
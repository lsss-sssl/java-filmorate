package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public final class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<MpaDto> getUsers() {
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public MpaDto getById(@PathVariable final long id) {
        return mpaService.getById(id);
    }
}
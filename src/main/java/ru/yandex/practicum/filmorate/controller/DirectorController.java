package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.director.NewDirectorRequest;
import ru.yandex.practicum.filmorate.dto.director.UpdateDirectorRequest;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService service;

    @GetMapping
    public List<DirectorDto> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public  DirectorDto getDirectorDyId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DirectorDto addDirector(@RequestBody NewDirectorRequest request) {
       return service.create(request);
    }

    @PutMapping()
    public DirectorDto updateDirector(@RequestBody UpdateDirectorRequest request) {
        return  service.updateDirector(request);
    }

    @DeleteMapping("{id}")
    public void deleteDirector(@PathVariable Long id) {
        service.deleteDirector(id);
    }
}

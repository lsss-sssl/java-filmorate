package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
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
public final class DirectorController {
    private final DirectorService directorService;

    /**
     *     GET    /directors
     *     GET    /directors/{id}
     *     POST   /directors
     *     PUT    /directors
     *     DELETE /directors/{id}
     */

    @GetMapping
    public List<DirectorDto> getAll() {
        return directorService.getAll();
    }

    @GetMapping("/{id}")
    public  DirectorDto getDirectorById(@PathVariable long id) {
        return directorService.getById(id);
    }

    @PostMapping
    public DirectorDto create(@Valid @RequestBody NewDirectorRequest request) {
       return directorService.create(request);
    }

    @PutMapping()
    public DirectorDto update(@Valid @RequestBody UpdateDirectorRequest request) {
        return  directorService.update(request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        directorService.deleteDirector(id);
    }
}
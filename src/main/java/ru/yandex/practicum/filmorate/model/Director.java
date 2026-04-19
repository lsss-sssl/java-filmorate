package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Set;

@Data
public class Director {
    private  long id;
    private String name;
    private Set<Film> films;
}
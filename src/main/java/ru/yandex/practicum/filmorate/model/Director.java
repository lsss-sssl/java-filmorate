package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Set;

@Data
public class Director {
    private  long id;
    private String firstName;
    private String lastName;
    private Set<Film> films;
}
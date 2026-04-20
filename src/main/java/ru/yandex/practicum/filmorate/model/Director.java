package ru.yandex.practicum.filmorate.model;

import lombok.Data;


@Data
public class Director {
    private  long id;
    private String name;

    public static Director fromId(long id) {
        Director director = new Director();
        director.setId(id);
        return director;
    }
}
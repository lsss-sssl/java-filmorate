package ru.yandex.practicum.filmorate.dto.event;

import lombok.Data;

@Data
public class EventDto {
    private Long timestamp;
    private Long userId;
    private String eventType;
    private String operation;
    private Long eventId;
    private Long entityId;
}
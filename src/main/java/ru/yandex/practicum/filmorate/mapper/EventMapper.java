package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.event.EventDto;
import ru.yandex.practicum.filmorate.model.Event;

public final class EventMapper {
    public static EventDto mapToEventDto(Event event) {
        EventDto dto = new EventDto();
        dto.setTimestamp(event.getTimestamp());
        dto.setUserId(event.getUserId());
        dto.setEventType(event.getEventType());
        dto.setOperation(event.getOperation());
        dto.setEventId(event.getEventId());
        dto.setEntityId(event.getEntityId());
        return dto;
    }

    public static Event mapToEvent(long userId, String eventType, String operation, long entityId) {
        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setUserId(userId);
        event.setEventType(eventType);
        event.setOperation(operation);
        event.setEntityId(entityId);
        return event;
    }
}
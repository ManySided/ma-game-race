package ru.make.game.racing.server.event.listener;

import ru.make.game.racing.server.event.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}

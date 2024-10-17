package ru.make.game.racing.server.event;

import ru.make.game.racing.server.networking.session.PlayerSession;

public interface PlayerEvent extends Event{
    PlayerSession getSession();
}

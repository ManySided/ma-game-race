package ru.make.game.racing.server.networking.message;

import ru.make.game.racing.server.networking.session.PlayerSession;

public interface PlayerMessage extends Message {
    PlayerSession session();

    void setSession(PlayerSession session);
}

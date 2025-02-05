package ru.make.game.racing.server.networking.mode;

import ru.make.game.racing.server.networking.session.Session;

import java.util.Collection;

public interface GameChannelMode {
    String GAME_CHANNEL_MODE = "GAME_CHANNEL_MODE";
    String OUT_OF_ROOM_CHANNEL_MODE = "OUT_OF_ROOM_CHANNEL_MODE";

    String getModeName();

    <T extends Session> void apply(T playerSession);

    <T extends Session> void apply(T playerSession,
                                   boolean clearExistingProtocolHandlers);

    <T extends Session> void apply(Collection<T> playerSessions);
}

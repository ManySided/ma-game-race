package ru.make.game.racing.server.event;

import lombok.Getter;
import lombok.Setter;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Setter
@Getter
public class GameRoomJoinEvent extends AbstractPlayerEvent {
    public GameRoomJoinEvent(PlayerSession session) {
        super(session);
    }
}

package ru.make.game.racing.server.event;

import lombok.Getter;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Getter
public class KeyDownPlayerEvent extends AbstractPlayerEvent {
    private final String inputId;
    private final Boolean state;

    public KeyDownPlayerEvent(PlayerSession session, String inputId, Boolean state) {
        super(session);
        this.inputId = inputId;
        this.state = state;
    }
}
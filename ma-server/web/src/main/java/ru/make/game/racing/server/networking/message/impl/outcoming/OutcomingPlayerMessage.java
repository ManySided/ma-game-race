package ru.make.game.racing.server.networking.message.impl.outcoming;

import lombok.Getter;
import lombok.Setter;
import ru.make.game.racing.server.networking.message.PlayerMessage;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Getter
@Setter
public class OutcomingPlayerMessage extends OutcomingMessage implements PlayerMessage {
    private transient PlayerSession playerSession;

    public OutcomingPlayerMessage(int type) {
        super(type, null);
    }

    public OutcomingPlayerMessage(PlayerSession playerSession, int type, Object source) {
        super(type, source);
        this.playerSession = playerSession;
    }

    @Override
    public PlayerSession session() {
        return playerSession;
    }

    @Override
    public void setSession(PlayerSession session) {
        this.playerSession = session;
    }
}
package ru.make.game.racing.server.networking.message.impl.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.make.game.racing.server.networking.message.PlayerMessage;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Getter
@Setter
@NoArgsConstructor
public class IncomingPlayerMessage extends IncomingMessage implements PlayerMessage {
    private transient PlayerSession playerSession;

    public IncomingPlayerMessage(PlayerSession playerSession, int type, String source) {
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

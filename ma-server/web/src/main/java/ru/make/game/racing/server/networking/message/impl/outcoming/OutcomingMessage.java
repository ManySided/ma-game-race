package ru.make.game.racing.server.networking.message.impl.outcoming;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.make.game.racing.server.networking.message.impl.AbstractMessage;
import ru.make.game.racing.server.networking.message.Message;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OutcomingMessage extends AbstractMessage implements Message, Serializable {
    public OutcomingMessage(int type) {
        super(type, null);
    }

    public OutcomingMessage(int type, Object data) {
        super(type, data);
    }
}

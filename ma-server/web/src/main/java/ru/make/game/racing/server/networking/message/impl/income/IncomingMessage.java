package ru.make.game.racing.server.networking.message.impl.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.make.game.racing.server.networking.message.impl.AbstractMessage;
import ru.make.game.racing.server.networking.message.Message;

@Getter
@Setter
@NoArgsConstructor
public class IncomingMessage extends AbstractMessage implements Message {
    public IncomingMessage(int type, String data) {
        super(type, data);
    }
}
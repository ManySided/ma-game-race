package ru.make.game.racing.server.networking.message.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.make.game.racing.server.networking.message.Message;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractMessage implements Message, Serializable {
    protected int type;
    protected Object data;
}

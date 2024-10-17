package ru.make.game.racing.server.networking.message;

import java.io.Serializable;

public interface Message extends Serializable {
    int getType();

    void setType(int type);

    Object getData();

    void setData(Object data);
}

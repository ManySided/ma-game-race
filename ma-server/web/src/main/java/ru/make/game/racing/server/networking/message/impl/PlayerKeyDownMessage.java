package ru.make.game.racing.server.networking.message.impl;

import lombok.Data;

@Data
public class PlayerKeyDownMessage {
    private String inputId;
    private boolean state;
}

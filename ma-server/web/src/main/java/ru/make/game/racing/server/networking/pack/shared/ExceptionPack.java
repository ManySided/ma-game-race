package ru.make.game.racing.server.networking.pack.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.make.game.racing.server.networking.pack.SharedPack;

@Data
@AllArgsConstructor
public class ExceptionPack implements SharedPack {
    private String message;
}

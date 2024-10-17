package ru.make.game.racing.server.networking.pack.init;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.make.game.racing.server.networking.pack.InitPack;

@Data
@AllArgsConstructor
public class GameSettingsPack implements InitPack {
    private Long loopRate;
}
package ru.make.game.racing.server.networking.pack.init;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.make.game.racing.server.game.model.Vector;
import ru.make.game.racing.server.networking.pack.InitPack;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInitPack implements InitPack {
    private Long id;
    private Vector position;
}

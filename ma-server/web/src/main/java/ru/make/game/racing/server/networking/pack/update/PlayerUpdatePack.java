package ru.make.game.racing.server.networking.pack.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.make.game.racing.server.game.model.Vector;
import ru.make.game.racing.server.networking.pack.UpdatePack;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUpdatePack implements UpdatePack {
    private Long id;
    private Vector position;
}

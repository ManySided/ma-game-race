package ru.make.game.racing.server.networking.pack.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.make.game.racing.server.networking.pack.UpdatePack;

import java.util.Collection;

@Data
@AllArgsConstructor
public class GameUpdatePack implements UpdatePack {
    private PrivatePlayerUpdatePack player;
    private Collection<PlayerUpdatePack> players;
}
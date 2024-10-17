package ru.make.game.racing.server.networking.pack.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.make.game.racing.server.networking.pack.PrivateUpdatePack;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivatePlayerUpdatePack implements PrivateUpdatePack {
    private Long id;
}
package ru.make.game.racing.server.game.model;

import ru.make.game.racing.server.game.room.GameRoom;
import ru.make.game.racing.server.networking.session.PlayerSession;

public interface Player extends Entity<Long>{
    interface PlayerFactory<CM, P extends Player, GR extends GameRoom> {
        P create(Long nextId, CM initialData, GR gameRoom, PlayerSession playerSession);
    }
}

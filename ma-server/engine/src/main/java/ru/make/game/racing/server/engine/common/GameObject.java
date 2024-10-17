package ru.make.game.racing.server.engine.common;

import ru.make.game.racing.server.engine.dictionary.GameObjectStatus;
import ru.make.game.racing.server.engine.game.GameAction;

import java.util.List;
import java.util.UUID;

public interface GameObject {
    List<GameObject> loop(GameAction action);

    UUID getId();

    Position getPosition();

    GameObjectStatus getStatus();
}

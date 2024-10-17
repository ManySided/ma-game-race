package ru.make.game.racing.server.engine.game;

import lombok.RequiredArgsConstructor;
import ru.make.game.racing.server.engine.common.AbstractGameObject;
import ru.make.game.racing.server.engine.common.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Obstacle extends AbstractGameObject {
    public Obstacle(long x, long y, long size) {
        super(x, y, size);
    }

    @Override
    public List<GameObject> loop(GameAction action) {
        return new ArrayList<>();
    }
}

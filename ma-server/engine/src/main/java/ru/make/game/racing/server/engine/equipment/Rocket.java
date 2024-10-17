package ru.make.game.racing.server.engine.equipment;

import ru.make.game.racing.server.engine.common.AbstractGameObject;
import ru.make.game.racing.server.engine.common.GameObject;
import ru.make.game.racing.server.engine.game.GameAction;

import java.util.ArrayList;
import java.util.List;

public class Rocket extends AbstractGameObject {
    public static final long SIZE = 10;

    public Rocket(long x, long y) {
        super(x, y, SIZE);
    }

    @Override
    public List<GameObject> loop(GameAction action) {
        return new ArrayList<>();
    }
}

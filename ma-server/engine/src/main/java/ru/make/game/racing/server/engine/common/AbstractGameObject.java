package ru.make.game.racing.server.engine.common;

import ru.make.game.racing.server.engine.dictionary.GameObjectStatus;

import java.util.UUID;

public abstract class AbstractGameObject implements GameObject {
    private final UUID objectId;
    private final Position position;
    private GameObjectStatus status;

    public AbstractGameObject(long x, long y, long size) {
        this.objectId = UUID.randomUUID();
        this.position = new Position(x, y, size);
        this.status = GameObjectStatus.LIFE;
    }

    @Override
    public UUID getId() {
        return this.objectId;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public GameObjectStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameObjectStatus status) {
        this.status = status;
    }
}

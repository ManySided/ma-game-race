package ru.make.game.racing.server.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.make.game.racing.server.game.room.GameRoom;
import ru.make.game.racing.server.networking.pack.IInitPackSupplier;
import ru.make.game.racing.server.networking.pack.IUpdatePackSupplier;
import ru.make.game.racing.server.networking.pack.InitPack;
import ru.make.game.racing.server.networking.pack.UpdatePack;

@Accessors(chain = true)
@Getter
@Setter
public abstract class GameEntity <ID,GR extends GameRoom,
        IP extends InitPack, UP extends UpdatePack> extends AbstractEntity<ID>  implements
        Initializable<IP>,
        IUpdatePackSupplier<UP>,
        IInitPackSupplier<IP> {
    protected Boolean isMoving;
    protected Boolean isAlive;
    protected Vector position;
    protected Vector velocity;
    protected Vector acceleration;
    protected GR gameRoom;

    public GameEntity(ID id, GR gameRoom) {
        super(id);
        isMoving = false;
        isAlive = true;
        position = new Vector(0.0, 0.0);
        velocity = new Vector(0.0,0.0);
        acceleration = new Vector(0.0,0.0);
        this.gameRoom = gameRoom;
    }

    @Override
    public IP init() {
        return getInitPack();
    }
}

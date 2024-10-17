package ru.make.game.racing.server.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.make.game.racing.server.game.room.GameRoom;
import ru.make.game.racing.server.networking.pack.IPrivateUpdatePackSupplier;
import ru.make.game.racing.server.networking.pack.InitPack;
import ru.make.game.racing.server.networking.pack.PrivateUpdatePack;
import ru.make.game.racing.server.networking.pack.UpdatePack;
import ru.make.game.racing.server.networking.session.PlayerSession;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractPlayer<GR extends GameRoom,
        IP extends InitPack, UP extends UpdatePack, PUP extends PrivateUpdatePack>
        extends GameEntity<Long, GR, IP, UP>
        implements Player, Updatable, IPrivateUpdatePackSupplier<PUP> {
    protected PlayerSession session;
    protected Map<Direction, Boolean> movingState;

    public AbstractPlayer(Long id, GR gameRoom, PlayerSession session) {
        super(id, gameRoom);
        this.session = session;
        this.movingState = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(direction -> direction, direction -> false));
    }

    @Override
    public IP init() {
        return getInitPack();
    }
}

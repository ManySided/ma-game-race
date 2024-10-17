package ru.make.game.racing.server.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.config.Constants;
import ru.make.game.racing.server.event.GameRoomJoinEvent;
import ru.make.game.racing.server.game.room.DefaultGameRoom;
import ru.make.game.racing.server.networking.pack.init.PlayerInitPack;
import ru.make.game.racing.server.networking.pack.update.PlayerUpdatePack;
import ru.make.game.racing.server.networking.pack.update.PrivatePlayerUpdatePack;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Accessors(chain = true)
@Getter
@Setter
public class DefaultPlayer extends AbstractPlayer<DefaultGameRoom, PlayerInitPack, PlayerUpdatePack, PrivatePlayerUpdatePack> {
    @Component
    public static class DefaultPlayerFactory implements Player.PlayerFactory<GameRoomJoinEvent,
            DefaultPlayer, DefaultGameRoom> {
        @Override
        public DefaultPlayer create(Long nextId, GameRoomJoinEvent initialData, DefaultGameRoom gameRoom, PlayerSession playerSession) {
            return new DefaultPlayer(nextId, gameRoom, playerSession);
        }
    }

    public DefaultPlayer(Long id, DefaultGameRoom gameRoom, PlayerSession session) {
        super(id, gameRoom, session);
    }

    public void updateState(Direction direction, Boolean state) {
        movingState.put(direction, state);
        isMoving = this.movingState.containsValue(true);
    }

    @Override
    public void update() {
        velocity.setX(isMoving && movingState.get(Direction.RIGHT) ?
                Constants.ABS_PLAYER_SPEED : (isMoving && movingState.get(Direction.LEFT) ?
                -Constants.ABS_PLAYER_SPEED : 0.0));
        velocity.setY(isMoving && movingState.get(Direction.UP) ?
                -Constants.ABS_PLAYER_SPEED : (isMoving && movingState.get(Direction.DOWN) ?
                Constants.ABS_PLAYER_SPEED : 0.0));

        position.sum(velocity);
    }

    @Override
    public PlayerUpdatePack getUpdatePack() {
        return new PlayerUpdatePack(id, position);
    }

    @Override
    public PlayerInitPack getInitPack() {
        return new PlayerInitPack(id, position);
    }

    @Override
    public PlayerInitPack init() {
        return getInitPack();
    }

    @Override
    public PrivatePlayerUpdatePack getPrivateUpdatePack() {
        return new PrivatePlayerUpdatePack(id);
    }
}

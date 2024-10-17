package ru.make.game.racing.server.game.room;

import lombok.extern.slf4j.Slf4j;
import ru.make.game.racing.server.config.ApplicationProperties;
import ru.make.game.racing.server.event.KeyDownPlayerEvent;
import ru.make.game.racing.server.game.map.GameMap;
import ru.make.game.racing.server.game.model.DefaultPlayer;
import ru.make.game.racing.server.game.model.Direction;
import ru.make.game.racing.server.game.model.Vector;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingMessage;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingPlayerMessage;
import ru.make.game.racing.server.networking.pack.init.GameRoomStartPack;
import ru.make.game.racing.server.networking.pack.init.GameSettingsPack;
import ru.make.game.racing.server.networking.pack.update.GameUpdatePack;
import ru.make.game.racing.server.networking.pack.update.PlayerUpdatePack;
import ru.make.game.racing.server.networking.session.PlayerSession;
import ru.make.game.racing.server.service.GameRoomManagementService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class DefaultGameRoom extends AbstractGameRoom {
    private final GameMap gameMap;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final ApplicationProperties.RoomProperties roomProperties;

    public DefaultGameRoom(GameMap gameMap, UUID gameRoomId,
                           GameRoomManagementService gameRoomManagementService, ScheduledExecutorService schedulerService,
                           ApplicationProperties.RoomProperties roomProperties) {
        super(gameRoomId, gameRoomManagementService, schedulerService);
        this.gameMap = gameMap;
        this.roomProperties = roomProperties;
    }

    @Override
    public void onRoomCreated(List<PlayerSession> playerSessions) {
        if (playerSessions != null) {
            playerSessions.forEach(session -> {
                DefaultPlayer defaultPlayer = (DefaultPlayer) session.getPlayer();
                defaultPlayer.setPosition(new Vector(100.0, 100.0));
                gameMap.addPlayer(defaultPlayer);
            });
            super.onRoomCreated(playerSessions);
        }

        sendBroadcast(new OutcomingMessage(MessageType.JOIN_SUCCESS,
                new GameSettingsPack(roomProperties.getLoopRate())));
        log.debug("Room {} has been created", key());
    }

    @Override
    public void onRoomStarted() {
        this.started.set(false);
        sendBroadcast(new OutcomingMessage(MessageType.ROOM_START, new GameRoomStartPack()));
        log.debug("Room {} has been started", key());
    }

    @Override
    public void onBattleStarted() {
        this.started.set(true);
        sendBroadcast(new OutcomingPlayerMessage(MessageType.BATTLE_START));
        log.debug("Room {}. Battle has been started", key());
    }

    @Override
    public void onBattleEnded() {
        sendBroadcast(new OutcomingMessage(MessageType.ROOM_CLOSE));
        log.debug("Room {} has been ended", key());
    }

    //room's game loop
    @Override
    public void update() {
        if (!started.get()) return;
        final List<PlayerUpdatePack> playerUpdatePackList =
                gameMap.getPlayers()
                        .stream()
                        .peek(DefaultPlayer::update)
                        .map(DefaultPlayer::getUpdatePack)
                        .collect(Collectors.toList());

        for (DefaultPlayer currentPlayer : gameMap.getPlayers()) {
            final PlayerSession session = currentPlayer.getSession();
            send(session, new OutcomingPlayerMessage(session, MessageType.UPDATE,
                    new GameUpdatePack(
                            currentPlayer.getPrivateUpdatePack(),
                            playerUpdatePackList)));
        }
    }

    public void onPlayerKeyDown(KeyDownPlayerEvent event) {
        if (!started.get()) return;
        DefaultPlayer player = (DefaultPlayer) event.getSession().getPlayer();
        if (!player.getIsAlive()) return;
        Direction direction = Direction.valueOf(event.getInputId());
        player.updateState(direction, event.getState());
    }

    @Override
    public void onDestroy(List<PlayerSession> playerSessions) {
        onDestroy(playerSessions, playerSession -> gameMap.removePlayer((DefaultPlayer) playerSession.getPlayer()));
    }
}

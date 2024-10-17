package ru.make.game.racing.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.make.game.racing.server.config.ApplicationProperties;
import ru.make.game.racing.server.event.GameRoomJoinEvent;
import ru.make.game.racing.server.game.map.GameMap;
import ru.make.game.racing.server.game.model.DefaultPlayer;
import ru.make.game.racing.server.game.model.Player;
import ru.make.game.racing.server.game.room.DefaultGameRoom;
import ru.make.game.racing.server.game.room.GameRoom;
import ru.make.game.racing.server.networking.handler.WebsocketHandler;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingMessage;
import ru.make.game.racing.server.networking.mode.MainGameChannelMode;
import ru.make.game.racing.server.networking.mode.OutOfRoomChannelMode;
import ru.make.game.racing.server.networking.session.PlayerSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameRoomManagementService implements WebsocketHandler {
    private final Map<UUID, DefaultGameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private final Queue<GameRoomJoinEvent> sessionQueue = new ConcurrentLinkedQueue<>();

    private final MainGameChannelMode gameChannelMode;
    private final OutOfRoomChannelMode outOfRoomChannelMode;
    private final Player.PlayerFactory<GameRoomJoinEvent, DefaultPlayer, DefaultGameRoom> playerFactory;
    private final ApplicationProperties applicationProperties;
    private final ScheduledExecutorService schedulerService;

    public Optional<DefaultGameRoom> getRoomByKey(UUID key) {
        return Optional.ofNullable(gameRoomMap.get(key));
    }

    public void addPlayerToWait(GameRoomJoinEvent event) {
        sessionQueue.add(event);
        send(event, new OutcomingMessage(MessageType.JOIN_WAIT));

        if (sessionQueue.size() < applicationProperties.getRoom().getMaxPlayers())
            return;

        final GameMap gameMap = new GameMap();
        final DefaultGameRoom room = new DefaultGameRoom(gameMap,
                UUID.randomUUID(), GameRoomManagementService.this, schedulerService, applicationProperties.getRoom());
        gameRoomMap.put(room.key(), room);

        final List<PlayerSession> playerSessions = new ArrayList<>();
        while (playerSessions.size() != applicationProperties.getRoom().getMaxPlayers()) {
            final GameRoomJoinEvent evt = sessionQueue.remove();
            final PlayerSession ps = evt.getSession();
            ps.setRoomKey(room.key());
            ps.setPlayer(playerFactory.create(gameMap.nextPlayerId(), evt, room, ps));
            playerSessions.add(ps);
        }

        gameChannelMode.apply(playerSessions);
        room.onRoomCreated(playerSessions);
        room.start(applicationProperties.getRoom().getStartDelay(),
                applicationProperties.getRoom().getEndDelay(),
                applicationProperties.getRoom().getLoopRate());
    }

    public void onBattleEnd(GameRoom room) {
        gameRoomMap.remove(room.key());
        outOfRoomChannelMode.apply(room.close());
    }

    public void removePlayerFromWaitQueue(PlayerSession session) {
        sessionQueue.removeIf(event -> event.getSession().equals(session));
    }
}

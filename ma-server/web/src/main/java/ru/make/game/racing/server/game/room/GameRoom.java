package ru.make.game.racing.server.game.room;

import ru.make.game.racing.server.networking.message.Message;
import ru.make.game.racing.server.networking.session.PlayerSession;
import ru.make.game.racing.server.networking.session.Session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public interface GameRoom extends Runnable {
    void start(long startDelay, long endDelay, long loopRate);

    void onRoomCreated(List<PlayerSession> playerSessions);

    void onRoomStarted();

    void onBattleStarted();

    void onBattleEnded();

    void onDestroy(List<PlayerSession> playerSessions);

    void onDestroy(List<PlayerSession> playerSessions, Consumer<PlayerSession> callback);

    void onDisconnect(PlayerSession session);

    void update();

    Collection<PlayerSession> sessions();

    int currentPlayersCount();

    Optional<PlayerSession> getPlayerSessionBySessionId(Session session);

    UUID key();

    void send(PlayerSession playerSession, Message message);

    void sendBroadcast(Message message);

    void sendBroadcast(Function<PlayerSession, Message> function);

    Collection<PlayerSession> close();

    ScheduledExecutorService getRoomExecutorService();
}

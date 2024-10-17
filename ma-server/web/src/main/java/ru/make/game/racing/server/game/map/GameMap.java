package ru.make.game.racing.server.game.map;

import ru.make.game.racing.server.game.model.DefaultPlayer;
import ru.make.game.racing.server.game.model.GameEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameMap {
    private final Map<Long, DefaultPlayer> players = new ConcurrentHashMap<>();

    public GameMap() {
    }

    public DefaultPlayer getPlayerById(Long id) {
        return players.get(id);
    }

    public Collection<DefaultPlayer> getPlayers() {
        return players.values();
    }

    public void addPlayer(DefaultPlayer player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(DefaultPlayer player) {
        players.remove(player.getId());
    }

    public Long nextPlayerId() {
        return (System.currentTimeMillis() << 20) | (System.nanoTime() & ~9223372036854251520L);
    }

    public Long alivePlayers() {
        return players.values().stream().filter(GameEntity::getIsAlive).count();
    }
}

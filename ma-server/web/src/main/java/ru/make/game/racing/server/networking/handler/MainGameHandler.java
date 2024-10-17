package ru.make.game.racing.server.networking.handler;

import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.event.KeyDownPlayerEvent;
import ru.make.game.racing.server.event.dispatcher.EventDispatcher;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.PlayerMessage;
import ru.make.game.racing.server.service.GameRoomManagementService;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MainGameHandler extends AbstractGameHandler<PlayerMessage> {
    private GameRoomManagementService gameRoomManagementService;

    public MainGameHandler() {
        super(new EventDispatcher<>());
        addEventListener(MessageType.PLAYER_KEY_DOWN, KeyDownPlayerEvent.class,
                event -> gameRoomManagementService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerKeyDown(event)));
    }

    @Autowired
    public void setGameRoomService(@Lazy GameRoomManagementService gameRoomManagementService) {
        this.gameRoomManagementService = gameRoomManagementService;
    }
}

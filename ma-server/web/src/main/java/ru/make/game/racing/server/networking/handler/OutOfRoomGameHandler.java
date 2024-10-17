package ru.make.game.racing.server.networking.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.event.GameRoomJoinEvent;
import ru.make.game.racing.server.event.dispatcher.EventDispatcher;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.income.IncomingPlayerMessage;
import ru.make.game.racing.server.networking.session.PlayerSession;
import ru.make.game.racing.server.service.GameRoomManagementService;

@Slf4j
@Component
@ChannelHandler.Sharable
public class OutOfRoomGameHandler extends AbstractGameHandler<IncomingPlayerMessage> {
    protected GameRoomManagementService gameRoomManagementService;

    public OutOfRoomGameHandler() {
        super(new EventDispatcher<>());
        addEventListener(MessageType.JOIN, GameRoomJoinEvent.class,
                event -> gameRoomManagementService.addPlayerToWait(event));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        gameRoomManagementService.removePlayerFromWaitQueue(PlayerSession.getPlayerSessionFromChannel(ctx));
    }

    @Autowired
    public void setGameRoomManager(@Lazy GameRoomManagementService gameRoomManagementService) {
        this.gameRoomManagementService = gameRoomManagementService;
    }
}

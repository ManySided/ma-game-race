package ru.make.game.racing.server.networking.handler;

import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.event.AuthEvent;
import ru.make.game.racing.server.event.dispatcher.EventDispatcher;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.income.IncomingMessage;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingMessage;
import ru.make.game.racing.server.networking.mode.OutOfRoomChannelMode;
import ru.make.game.racing.server.service.AuthService;

@Slf4j
@ChannelHandler.Sharable
@Component
public class InitialGameHandler extends AbstractGameHandler<IncomingMessage> {
    public InitialGameHandler(OutOfRoomChannelMode outOfRoomChannelMode, AuthService authService) {
        super(new EventDispatcher<>());

        addEventListener(MessageType.AUTHENTICATION, AuthEvent.class, event -> {
            outOfRoomChannelMode.apply(authService.authenticate(event.getChannel(), event.getBearerToken()));
            send(event.getChannel(), new OutcomingMessage(MessageType.AUTHENTICATION));
        });
    }
}
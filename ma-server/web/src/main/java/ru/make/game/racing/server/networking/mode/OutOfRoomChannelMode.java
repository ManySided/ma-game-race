package ru.make.game.racing.server.networking.mode;

import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.config.ServerConstants;
import ru.make.game.racing.server.networking.adapter.TextWebsocketDecoder;
import ru.make.game.racing.server.networking.adapter.TextWebsocketEncoder;
import ru.make.game.racing.server.networking.handler.OutOfRoomGameHandler;
import ru.make.game.racing.server.networking.session.Session;

@Slf4j
@Component
public class OutOfRoomChannelMode extends AbstractGameChannelMode {
    private final TextWebsocketDecoder textWebsocketDecoder;
    private final TextWebsocketEncoder textWebsocketEncoder;
    private final OutOfRoomGameHandler handler;

    public OutOfRoomChannelMode(TextWebsocketDecoder textWebsocketDecoder,
                                TextWebsocketEncoder textWebsocketEncoder,
                                OutOfRoomGameHandler handler) {
        super(GameChannelMode.OUT_OF_ROOM_CHANNEL_MODE);
        this.textWebsocketDecoder = textWebsocketDecoder;
        this.textWebsocketEncoder = textWebsocketEncoder;
        this.handler = handler;
    }

    @Override
    public <T extends Session> void apply(T playerSession, boolean clearExistingProtocolHandlers) {
        apply(playerSession);
    }

    @Override
    public <T extends Session> void apply(T playerSession) {
        log.trace("Going to apply {} on session: {}", getModeName(), playerSession);

        ChannelPipeline pipeline = playerSession
                .getPipeLineOfConnection();

        if (pipeline.names().contains(ServerConstants.INIT_HANDLER_NAME))
            pipeline.remove(ServerConstants.INIT_HANDLER_NAME);
        if (pipeline.names().contains(ServerConstants.EVENT_HANDLER))
            pipeline.remove(ServerConstants.EVENT_HANDLER);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_DECODER))
            pipeline.addLast(ServerConstants.TXT_WS_DECODER, textWebsocketDecoder);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_ENCODER))
            pipeline.addLast(ServerConstants.TXT_WS_ENCODER, textWebsocketEncoder);
        pipeline.addLast(ServerConstants.EVENT_HANDLER, handler);
    }
}

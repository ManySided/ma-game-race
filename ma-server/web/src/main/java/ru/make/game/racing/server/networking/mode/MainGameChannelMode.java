package ru.make.game.racing.server.networking.mode;

import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.config.ServerConstants;
import ru.make.game.racing.server.networking.adapter.TextWebsocketDecoder;
import ru.make.game.racing.server.networking.adapter.TextWebsocketEncoder;
import ru.make.game.racing.server.networking.handler.MainGameHandler;
import ru.make.game.racing.server.networking.session.Session;

@Slf4j
@Component
public class MainGameChannelMode extends AbstractGameChannelMode {
    private final TextWebsocketDecoder textWebsocketDecoder;
    private final TextWebsocketEncoder textWebsocketEncoder;
    private final MainGameHandler baseGameHandler;

    public MainGameChannelMode(TextWebsocketDecoder textWebsocketDecoder, TextWebsocketEncoder textWebsocketEncoder, MainGameHandler baseGameHandler) {
        //super(com.tfkfan.nettywebgame.networking.mode.GameChannelMode.GAME_CHANNEL_MODE);
        // TODO тут прописал такой вот, посмотрим будет ли работать
        super(GameChannelMode.GAME_CHANNEL_MODE);
        this.textWebsocketDecoder = textWebsocketDecoder;
        this.textWebsocketEncoder = textWebsocketEncoder;
        this.baseGameHandler = baseGameHandler;
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
        if (pipeline.names().contains(ServerConstants.EVENT_HANDLER))
            pipeline.remove(ServerConstants.EVENT_HANDLER);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_DECODER))
            pipeline.addLast(ServerConstants.TXT_WS_DECODER, textWebsocketDecoder);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_ENCODER))
            pipeline.addLast(ServerConstants.TXT_WS_ENCODER, textWebsocketEncoder);
        if (!pipeline.names().contains(ServerConstants.EVENT_HANDLER))
            pipeline.addLast(ServerConstants.EVENT_HANDLER, baseGameHandler);
        if (pipeline.names().contains(ServerConstants.INIT_HANDLER_NAME))
            pipeline.remove(ServerConstants.INIT_HANDLER_NAME);
    }
}
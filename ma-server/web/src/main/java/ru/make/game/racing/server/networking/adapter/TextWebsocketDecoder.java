package ru.make.game.racing.server.networking.adapter;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.networking.message.Message;
import ru.make.game.racing.server.networking.message.PlayerMessage;
import ru.make.game.racing.server.networking.message.impl.income.IncomingMessage;
import ru.make.game.racing.server.networking.message.impl.income.IncomingPlayerMessage;
import ru.make.game.racing.server.networking.session.PlayerSession;

import java.util.List;
import java.util.Objects;

@ChannelHandler.Sharable
@Component
public class TextWebsocketDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    private final Gson gson = new Gson();

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame frame, List<Object> out) {
        final String json = frame.text();
        final PlayerSession ps = PlayerSession.getPlayerSessionFromChannel(ctx.channel());
        if (Objects.nonNull(ps)) {
            PlayerMessage playerMsg = gson.fromJson(json, IncomingPlayerMessage.class);
            playerMsg.setSession(ps);
            out.add(playerMsg);
        } else {
            Message msg = gson.fromJson(json, IncomingMessage.class);
            out.add(msg);
        }
    }
}

package ru.make.game.racing.server.networking.adapter;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import ru.make.game.racing.server.networking.message.Message;

import java.util.List;

@ChannelHandler.Sharable
@Component
public class TextWebsocketEncoder extends MessageToMessageEncoder<Message> {
    private final Gson gson = new Gson();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
        out.add(new TextWebSocketFrame(gson.toJson(msg)));
    }
}
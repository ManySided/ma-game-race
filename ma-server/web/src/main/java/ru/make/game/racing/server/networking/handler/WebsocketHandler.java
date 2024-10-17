package ru.make.game.racing.server.networking.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import ru.make.game.racing.server.event.PlayerEvent;
import ru.make.game.racing.server.networking.message.Message;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingMessage;
import ru.make.game.racing.server.networking.pack.shared.ExceptionPack;

public interface WebsocketHandler {
    default <E extends PlayerEvent> ChannelFuture send(E event, Message message) {
        return event.getSession().getChannel().writeAndFlush(message);
    }
    default ChannelFuture send(Channel channel, Message message) {
        return channel.writeAndFlush(message);
    }

    default ChannelFuture sendFailure(ChannelHandlerContext ctx, String message) {
        return send(ctx.channel(), new OutcomingMessage(MessageType.FAILURE, new ExceptionPack(message)));
    }
}

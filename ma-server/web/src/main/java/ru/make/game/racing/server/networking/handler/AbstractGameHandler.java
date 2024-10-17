package ru.make.game.racing.server.networking.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import ru.make.game.racing.server.event.Event;
import ru.make.game.racing.server.event.dispatcher.EventDispatcher;
import ru.make.game.racing.server.event.listener.EventListener;
import ru.make.game.racing.server.networking.message.Message;
import ru.make.game.racing.server.networking.message.MessageType;
import ru.make.game.racing.server.networking.message.impl.outcoming.OutcomingMessage;
import ru.make.game.racing.server.networking.pack.shared.ExceptionPack;

@Slf4j
public abstract class AbstractGameHandler<T extends Message> extends SimpleChannelInboundHandler<T>
        implements WebsocketHandler {
    private final EventDispatcher<T> eventDispatcher;

    protected AbstractGameHandler(EventDispatcher<T> eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public <A extends Event> void addEventListener(int messageType, Class<A> eventType,
                                                   EventListener<A> eventListener) {
        eventDispatcher.addEventListener(messageType, eventType, eventListener);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, T msg) {
        eventDispatcher.fireEvent(ctx, msg);
    }

    protected void closeChannelWithFailure(ChannelHandlerContext ctx, String message) {
        closeChannelWithFailure(ctx.channel(), message);
    }

    protected void closeChannelWithFailure(Channel channel, String message) {
        send(channel, new OutcomingMessage(MessageType.FAILURE, new ExceptionPack(message)))
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        sendFailure(ctx, cause.getMessage());
    }
}
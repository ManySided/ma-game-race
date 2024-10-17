package ru.make.game.racing.server.event.dispatcher;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import ru.make.game.racing.server.config.Constants;
import ru.make.game.racing.server.event.AbstractEvent;
import ru.make.game.racing.server.event.AbstractPlayerEvent;
import ru.make.game.racing.server.event.Event;
import ru.make.game.racing.server.event.listener.EventListener;
import ru.make.game.racing.server.networking.message.Message;
import ru.make.game.racing.server.networking.message.PlayerMessage;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher<T extends Message> {
    protected final Gson objectMapper = new Gson();

    private final Map<Integer, Class<? extends Event>> typeToEventType = new HashMap<>();
    private final Map<Integer, EventListener<? extends Event>> eventListenerMap = new HashMap<>();

    public <A extends Event> void addEventListener(int messageType, Class<A> eventType,
                                                   EventListener<? extends Event> eventListener) {
        typeToEventType.put(messageType, eventType);
        eventListenerMap.put(messageType, eventListener);
    }

    public void fireEvent(ChannelHandlerContext ctx, T message) {
        if (!typeToEventType.containsKey(message.getType()))
            return;

        Event event = objectMapper.fromJson(message.getData() != null ? message.getData().toString() : Constants.EMPTY_JSON,
                typeToEventType.get(message.getType()));
        if (event instanceof AbstractPlayerEvent && message instanceof PlayerMessage)
            ((AbstractPlayerEvent) event).setSession(((PlayerMessage) message).session());
        if (event instanceof AbstractEvent)
            ((AbstractEvent) event).setChannel(ctx.channel());

        if (!eventListenerMap.containsKey(message.getType()))
            throw new RuntimeException("Incorrect message type");

        fireEvent(event, eventListenerMap.get(message.getType()));
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void fireEvent(E event, EventListener<? extends Event> eventListener) {
        ((EventListener<E>) eventListener).onEvent(event);
    }
}

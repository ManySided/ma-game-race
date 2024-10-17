package ru.make.game.racing.server.networking.mode;

import io.netty.channel.ChannelPipeline;
import ru.make.game.racing.server.networking.ChannelUtil;
import ru.make.game.racing.server.networking.session.Session;

import java.util.Collection;

public abstract class AbstractGameChannelMode implements GameChannelMode {
    final String modeName;

    public AbstractGameChannelMode(String modeName) {
        super();
        this.modeName = modeName;
    }

    @Override
    public String getModeName() {
        return modeName;
    }

    @Override
    public  <T extends Session> void apply(T playerSession, boolean clearExistingProtocolHandlers) {
        if (clearExistingProtocolHandlers) {
            ChannelPipeline pipeline = playerSession
                    .getPipeLineOfConnection();
            ChannelUtil.clearPipeline(pipeline);
        }
        apply(playerSession);
    }

    @Override
    public  <T extends Session> void apply(Collection<T> playerSessions) {
        playerSessions.forEach(this::apply);
    }
}

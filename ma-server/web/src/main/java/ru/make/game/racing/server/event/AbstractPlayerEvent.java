package ru.make.game.racing.server.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.make.game.racing.server.networking.session.PlayerSession;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPlayerEvent extends AbstractEvent implements PlayerEvent {
    private PlayerSession session;
}

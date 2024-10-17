package ru.make.game.racing.server.event;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.make.game.racing.server.service.AuthService;

@Getter
@Setter
@NoArgsConstructor
public class AuthEvent extends AbstractEvent{
    @SerializedName(AuthService.TOKEN_FIELD)
    private String bearerToken;

    public AuthEvent(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
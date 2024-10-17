package ru.make.game.racing.server.engine.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MachineTransmission {
    TRANSMISSION_N(0),
    TRANSMISSION_1(1),
    TRANSMISSION_2(2),
    TRANSMISSION_3(3),
    TRANSMISSION_4(4),
    TRANSMISSION_5(5);

    private final int order;

    public static MachineTransmission getNext(MachineTransmission transmission) {
        var values = MachineTransmission.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(transmission) && values.length < (i + 1)) {
                return values[i];
            }
        }
        return transmission;
    }
}

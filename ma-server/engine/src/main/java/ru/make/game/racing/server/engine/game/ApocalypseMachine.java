package ru.make.game.racing.server.engine.game;

import ru.make.game.racing.server.engine.common.AbstractGameObject;
import ru.make.game.racing.server.engine.common.GameObject;
import ru.make.game.racing.server.engine.equipment.EquipmentElement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApocalypseMachine extends AbstractGameObject {
    public static final long MACHINE_SIZE = 40;
    private final UUID gamer;
    private final List<EquipmentElement> equipment;

    private Double speed;
    private MachineTransmission transmission;

    public ApocalypseMachine(UUID gamer, long positionX, long positionY) {
        super(positionX, positionY, MACHINE_SIZE);
        this.gamer = gamer;

        this.transmission = MachineTransmission.TRANSMISSION_N;

        this.equipment = new ArrayList<>();
        this.equipment.add(EquipmentElement.TOMBSTONE);
        this.equipment.add(EquipmentElement.ROCKET);
        this.equipment.add(EquipmentElement.ROCKET);
    }

    @Override
    public List<GameObject> loop(GameAction action) {
        List<GameObject> result = new ArrayList<>();
        switch (action) {
            case ACTION_MOTION -> {

            }
            case ACTION_MOVE_LEFT -> {

            }
            case ACTION_MOVE_RIGHT -> {

            }
            case ACTION_UP_TRANSMISSION -> {
                this.transmission = MachineTransmission.getNext(this.transmission);
            }
            case ACTION_FIRE_ROCKET -> {

            }
            case ACTION_DROP_TOMBSTONE -> {

            }
        }
        return result;
    }

    private void changePosition() {

    }
}

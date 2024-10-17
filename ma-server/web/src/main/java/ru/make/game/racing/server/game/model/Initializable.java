package ru.make.game.racing.server.game.model;

import ru.make.game.racing.server.networking.pack.InitPack;

public interface Initializable<T extends InitPack> {
    T init();
}
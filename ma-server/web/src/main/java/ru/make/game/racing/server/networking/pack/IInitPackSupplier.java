package ru.make.game.racing.server.networking.pack;

public interface IInitPackSupplier<T extends InitPack> {
    T getInitPack();
}
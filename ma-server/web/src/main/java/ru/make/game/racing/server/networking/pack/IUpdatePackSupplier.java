package ru.make.game.racing.server.networking.pack;

public interface IUpdatePackSupplier<T extends UpdatePack> {
    T getUpdatePack();
}
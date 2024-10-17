package ru.make.game.racing.server.networking.pack;

public interface IPrivateUpdatePackSupplier<T extends PrivateUpdatePack> {
    T getPrivateUpdatePack();
}
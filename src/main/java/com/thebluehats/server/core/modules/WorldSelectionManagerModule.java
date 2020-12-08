package com.thebluehats.server.core.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thebluehats.server.game.managers.game.WorldSelectionManager;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldSelectionManagerModule extends AbstractModule {
    @Provides
    @Singleton
    static WorldSelectionManager provideWorldSelectionManager(JavaPlugin plugin) {
        return new WorldSelectionManager(plugin);
    }

    @Override
    protected void configure() {
    }
}

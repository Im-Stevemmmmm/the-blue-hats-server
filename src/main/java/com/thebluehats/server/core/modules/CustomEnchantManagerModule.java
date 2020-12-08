package com.thebluehats.server.core.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thebluehats.server.game.managers.enchants.CustomEnchantManager;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomEnchantManagerModule extends AbstractModule {
    @Provides
    @Singleton
    static CustomEnchantManager provideCustomEnchantManager(JavaPlugin plugin) {
        return new CustomEnchantManager(plugin);
    }

    @Override
    protected void configure() {
    }
}

package com.thebluehats.server.core.modules

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.thebluehats.server.core.TheBlueHatsServerPlugin
import com.thebluehats.server.game.events.GameEvent
import com.thebluehats.server.game.events.GameEventManager
import com.thebluehats.server.game.managers.enchants.CustomEnchant
import com.thebluehats.server.game.managers.enchants.CustomEnchantManager
import com.thebluehats.server.game.utils.PluginLifecycleListener
import com.thebluehats.server.game.utils.Registerer

class RegistererModule : AbstractModule() {
    override fun configure() {}

    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideCustomEnchantManager(customEnchantManager: CustomEnchantManager): Registerer<CustomEnchant> {
            return customEnchantManager
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providePluginLifecycleListenerRegisterer(theBlueHatsServerPlugin: TheBlueHatsServerPlugin): Registerer<PluginLifecycleListener> {
            return theBlueHatsServerPlugin
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideGameEventRegisterer(gameEventManager: GameEventManager):
            Registerer<GameEvent> {
            return gameEventManager
        }
    }
}
package com.thebluehats.server.game.enchants

import com.google.inject.Inject
import com.thebluehats.server.game.managers.combat.templates.ArrowDamageTrigger
import com.thebluehats.server.game.managers.combat.templates.EnchantHolder
import com.thebluehats.server.game.managers.enchants.DamageTriggeredEnchant
import com.thebluehats.server.game.managers.enchants.EnchantGroup
import com.thebluehats.server.game.managers.enchants.EnchantProperty
import com.thebluehats.server.game.managers.enchants.processedevents.DamageEventEnchantData
import com.thebluehats.server.game.utils.EnchantLoreParser
import com.thebluehats.server.game.utils.PlayerHealth.Utils.addHealth
import org.bukkit.Material
import java.util.*

class Parasite @Inject constructor(arrowDamageTrigger: ArrowDamageTrigger) :
    DamageTriggeredEnchant(arrayOf(arrowDamageTrigger)) {
    private val healAmount = EnchantProperty(0.5, 1.0, 2.0)

    override val name: String get() = "Parasite"
    override val enchantReferenceName: String get() = "parasite"
    override val isDisabledOnPassiveWorld: Boolean get() = false
    override val enchantGroup: EnchantGroup get() = EnchantGroup.B
    override val isRareEnchant: Boolean get() = false
    override val enchantItemTypes: Array<Material> get() = arrayOf(Material.BOW)
    override val enchantHolder: EnchantHolder get() = EnchantHolder.DAMAGER

    override fun execute(data: DamageEventEnchantData) {
        data.damager addHealth healAmount.getValueAtLevel(data.level)
    }

    override fun getDescription(level: Int): ArrayList<String> {
        val enchantLoreParser = EnchantLoreParser("Heal <red>{0}</red> on arrow hit")

        enchantLoreParser.setSingleVariable("0.25❤", "0.5❤", "1.0❤")

        return enchantLoreParser.parseForLevel(level)
    }
}

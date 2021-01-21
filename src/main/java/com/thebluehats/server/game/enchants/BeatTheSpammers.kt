package com.thebluehats.server.game.enchants

import com.google.inject.Inject
import com.thebluehats.server.game.managers.combat.CalculationMode
import com.thebluehats.server.game.managers.combat.DamageManager
import com.thebluehats.server.game.managers.combat.templates.DamageEnchantTrigger
import com.thebluehats.server.game.managers.combat.templates.EnchantHolder
import com.thebluehats.server.game.managers.combat.templates.PlayerDamageTrigger
import com.thebluehats.server.game.managers.enchants.DamageTriggeredEnchant
import com.thebluehats.server.game.managers.enchants.EnchantGroup
import com.thebluehats.server.game.managers.enchants.EnchantProperty
import com.thebluehats.server.game.managers.enchants.processedevents.DamageEventEnchantData
import com.thebluehats.server.game.utils.EnchantLoreParser
import com.thebluehats.server.game.utils.EntityValidator
import org.bukkit.Material
import java.util.*

class BeatTheSpammers @Inject constructor(
    private val damageManager: DamageManager,
    playerDamageTrigger: PlayerDamageTrigger
) : DamageTriggeredEnchant(
    arrayOf(playerDamageTrigger), arrayOf(damageManager)
) {
    private val damageAmount = EnchantProperty(.10f, .25f, .40f)

    override val name = "Beat the Spammers"
    override val enchantReferenceName = "Beatthespammers"
    override val isDisabledOnPassiveWorld = false
    override val enchantGroup = EnchantGroup.A
    override val isRareEnchant = false
    override val enchantItemTypes = arrayOf(Material.BOW)
    override val enchantHolder = EnchantHolder.DAMAGER

    override fun execute(data: DamageEventEnchantData) {
        if (data.damagee.inventory.itemInHand.type == Material.BOW) {
            damageManager.addDamage(
                data.event, damageAmount.getValueAtLevel(data.level).toDouble(),
                CalculationMode.ADDITIVE
            )
        }
    }

    override fun getDescription(level: Int): ArrayList<String> {
        val enchantLoreParser = EnchantLoreParser(
            "Deal <red>{0}</red> damage vs. players<br/>holding a bow"
        )

        enchantLoreParser.setSingleVariable("+10%", "+25%", "+40%")

        return enchantLoreParser.parseForLevel(level)
    }
}
package com.thebluehats.server.game.enchants

import com.google.inject.Inject
import com.thebluehats.server.game.managers.combat.DamageManager
import com.thebluehats.server.game.managers.combat.templates.ArrowDamageTrigger
import com.thebluehats.server.game.managers.combat.templates.DamageEnchantTrigger
import com.thebluehats.server.game.managers.combat.templates.EnchantHolder
import com.thebluehats.server.game.managers.enchants.DamageTriggeredEnchant
import com.thebluehats.server.game.managers.enchants.EnchantGroup
import com.thebluehats.server.game.managers.enchants.EnchantProperty
import com.thebluehats.server.game.managers.enchants.processedevents.DamageEventEnchantData
import com.thebluehats.server.game.utils.EnchantLoreParser
import com.thebluehats.server.game.utils.EntityValidator
import org.bukkit.Material
import java.util.*

class RingArmor @Inject constructor(private val damageManager: DamageManager, arrowDamageTrigger: ArrowDamageTrigger) :
    DamageTriggeredEnchant(
        arrayOf<DamageEnchantTrigger>(arrowDamageTrigger), arrayOf<EntityValidator>(
            damageManager
        )
    ) {
    private val damageReductionAmount = EnchantProperty(.20f, .40f, .60f)
    override fun execute(data: DamageEventEnchantData) {
        val event = data.event
        val level = data.level
        damageManager.reduceDamageByPercentage(event, damageReductionAmount.getValueAtLevel(level).toDouble())
    }

    override fun getName(): String {
        return "Ring Armor"
    }

    override fun getEnchantReferenceName(): String {
        return "Ringarmor"
    }

    override fun getDescription(level: Int): ArrayList<String> {
        val enchantLoreParser = EnchantLoreParser(
            "Recieve <blue>-{0}%</blue> damage from<br/>arrows"
        )
        enchantLoreParser.setSingleVariable("20", "40", "60")
        return enchantLoreParser.parseForLevel(level)
    }

    override fun isDisabledOnPassiveWorld(): Boolean {
        return false
    }

    override fun getEnchantGroup(): EnchantGroup {
        return EnchantGroup.A
    }

    override fun isRareEnchant(): Boolean {
        return false
    }

    override fun getEnchantItemTypes(): Array<Material> {
        return arrayOf(Material.LEATHER_LEGGINGS)
    }

    override fun getEnchantHolder(): EnchantHolder {
        return EnchantHolder.DAMAGEE
    }
}
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

class Chipping @Inject constructor(private val damageManager: DamageManager, arrowDamageTrigger: ArrowDamageTrigger) :
    DamageTriggeredEnchant(
        arrayOf<DamageEnchantTrigger>(arrowDamageTrigger), arrayOf<EntityValidator>(
            damageManager
        )
    ) {
    private val damageAmount = EnchantProperty(0.5f, 1.0f, 1.5f)
    override fun execute(data: DamageEventEnchantData) {
        val damaged = data.damagee
        val level = data.level
        damageManager.doTrueDamage(damaged, damageAmount.getValueAtLevel(level).toDouble())
    }

    override fun getName(): String {
        return "Chipping"
    }

    override fun getEnchantReferenceName(): String {
        return "Chipping"
    }

    override fun getDescription(level: Int): ArrayList<String> {
        val enchantLoreParser = EnchantLoreParser("Deals <red>{0}</red> extra true damage")
        enchantLoreParser.setSingleVariable("0.5❤ ", "1.0❤ ", "1.5❤ ")
        return enchantLoreParser.parseForLevel(level)
    }

    override fun isDisabledOnPassiveWorld(): Boolean {
        return false
    }

    override fun getEnchantGroup(): EnchantGroup {
        return EnchantGroup.B
    }

    override fun isRareEnchant(): Boolean {
        return false
    }

    override fun getEnchantItemTypes(): Array<Material> {
        return arrayOf(Material.BOW)
    }

    override fun getEnchantHolder(): EnchantHolder {
        return EnchantHolder.DAMAGER
    }
}
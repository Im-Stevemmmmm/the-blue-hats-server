package com.thebluehats.server.game.enchants;

import java.util.ArrayList;
import java.util.UUID;

import com.google.inject.Inject;
import com.thebluehats.server.game.enchants.processedevents.PostEventTemplateResult;
import com.thebluehats.server.game.managers.combat.CalculationMode;
import com.thebluehats.server.game.managers.combat.DamageManager;
import com.thebluehats.server.game.managers.combat.templates.PlayerHitPlayerTemplate;
import com.thebluehats.server.game.managers.combat.templates.TargetPlayer;
import com.thebluehats.server.game.managers.enchants.DamageEnchant;
import com.thebluehats.server.game.managers.enchants.EnchantGroup;
import com.thebluehats.server.game.managers.enchants.EnchantProperty;
import com.thebluehats.server.game.managers.enchants.HitCounter;
import com.thebluehats.server.game.utils.LoreBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class ComboDamage implements DamageEnchant {
    private final EnchantProperty<Float> damageAmount = new EnchantProperty<>(.2f, .3f, .45f);
    private final EnchantProperty<Integer> hitsNeeded = new EnchantProperty<>(4, 3, 3);

    private final DamageManager damageManager;
    private final HitCounter hitCounter;
    private final PlayerHitPlayerTemplate playerHitPlayerTemplate;

    @Inject
    public ComboDamage(DamageManager damamgeManager, HitCounter hitCounter,
            PlayerHitPlayerTemplate playerHitPlayerTemplate) {
        this.damageManager = damamgeManager;
        this.hitCounter = hitCounter;
        this.playerHitPlayerTemplate = playerHitPlayerTemplate;
    }

    @Override
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        playerHitPlayerTemplate.run(this, event, TargetPlayer.DAMAGER, PlayerInventory::getItemInMainHand,
                damageManager);
    }

    @Override
    public void execute(PostEventTemplateResult data, int level) {
        Player damager = data.getDamager();
        UUID playerUuid = damager.getUniqueId();

        hitCounter.addOne(playerUuid);

        if (hitCounter.hasHits(damager, hitsNeeded.getValueAtLevel(level))) {
            damager.playSound(damager.getLocation(), Sound.ENTITY_DONKEY_HURT, 1, 0.5f);
            damageManager.addDamage(data.getEvent(), damageAmount.getValueAtLevel(level), CalculationMode.ADDITIVE);
        }
    }

    @Override
    public String getName() {
        return "Combo: Damage";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Combodamage";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder().declareVariable("fourth", "third", "third").declareVariable("+20%", "+30%", "+45%")
                .write("Every ").setColor(ChatColor.YELLOW).writeVariable(0, level).resetColor().write(" strike deals")
                .next().setColor(ChatColor.RED).writeVariable(1, level).resetColor().write(" damage").build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.A;
    }

    @Override
    public boolean isRareEnchant() {
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLDEN_SWORD };
    }
}

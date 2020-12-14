package com.thebluehats.server.game.enchants;

import java.util.ArrayList;
import java.util.UUID;

import com.google.inject.Inject;
import com.thebluehats.server.api.models.PitDataModel;
import com.thebluehats.server.api.repos.Repository;
import com.thebluehats.server.game.enchants.processedevents.PostEventTemplateResult;
import com.thebluehats.server.game.managers.combat.CalculationMode;
import com.thebluehats.server.game.managers.combat.DamageManager;
import com.thebluehats.server.game.managers.combat.templates.PlayerHitPlayerTemplate;
import com.thebluehats.server.game.managers.combat.templates.TargetPlayer;
import com.thebluehats.server.game.managers.enchants.DamageEnchant;
import com.thebluehats.server.game.managers.enchants.EnchantGroup;
import com.thebluehats.server.game.managers.enchants.EnchantProperty;
import com.thebluehats.server.game.utils.LoreBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class Billionaire implements DamageEnchant {
    private final EnchantProperty<Double> damageIncrease = new EnchantProperty<>(1.33D, 1.67D, 2D);
    private final EnchantProperty<Integer> goldNeeded = new EnchantProperty<>(100, 200, 350);

    private final Repository<UUID, PitDataModel> pitDataRepository;
    private final DamageManager damageManager;
    private final PlayerHitPlayerTemplate playerHitPlayerTemplate;

    @Inject
    public Billionaire(Repository<UUID, PitDataModel> pitDataRepository, DamageManager damageManager,
            PlayerHitPlayerTemplate playerHitPlayerTemplate) {
        this.pitDataRepository = pitDataRepository;
        this.damageManager = damageManager;
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
        UUID key = data.getDamager().getUniqueId();

        PitDataModel playerData = pitDataRepository.findUnique(key);
        float gold = playerData.getGold();

        if (gold < goldNeeded.getValueAtLevel(level))
            return;

        pitDataRepository.update(key, model -> model.setGold(gold - goldNeeded.getValueAtLevel(level)));

        damageManager.addDamage(data.getEvent(), damageIncrease.getValueAtLevel(level), CalculationMode.MULTIPLICATIVE);

        damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.73f);
    }

    @Override
    public String getName() {
        return "Billionaire";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Billionaire";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder().declareVariable("1.33", "1.67", "2").declareVariable("100g", "200g", "350g")
                .setColor(ChatColor.GRAY).write("Hits with this sword deals ").setColor(ChatColor.RED)
                .writeVariable(0, level).write("x").next().setColor(ChatColor.RED).write("damage ")
                .setColor(ChatColor.GRAY).write("but cost ").setColor(ChatColor.GOLD).writeVariable(1, level).build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.B;
    }

    @Override
    public boolean isRareEnchant() {
        return true;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLDEN_SWORD };
    }
}
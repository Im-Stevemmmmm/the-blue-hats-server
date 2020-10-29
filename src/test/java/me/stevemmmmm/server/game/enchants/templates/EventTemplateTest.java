package me.stevemmmmm.server.game.enchants.templates;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.stevemmmmm.server.game.enchants.CustomEnchant;
import me.stevemmmmm.server.game.enchants.Wasp;
import me.stevemmmmm.server.game.managers.BowManager;
import me.stevemmmmm.server.game.utils.LoreBuilder;

public class EventTemplateTest {
    @Mock
    private ItemStack item;
    @Mock
    private ItemMeta meta;

    private CustomEnchant enchant;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        enchant = new Wasp(new BowManager());

        when(item.getItemMeta()).thenReturn(meta);
        when(meta.getLore()).thenReturn(new LoreBuilder().write(ChatColor.BLUE, "Wasp").build());
    }

    @Test
    public void testPlayerHitPlayerTemplate() {
        EventTemplate template = new PlayerHitPlayer();
        Player damager = mock(Player.class);
        Player damagee = mock(Player.class);

        PlayerInventory inventory = mock(PlayerInventory.class);
        World world = mock(World.class);

        when(damagee.getInventory()).thenReturn(inventory);
        when(damagee.getWorld()).thenReturn(world);
        when(damager.getWorld()).thenReturn(world);

        when(world.getName()).thenReturn("");

        when(inventory.getItemInMainHand()).thenReturn(item);

        assertEquals(true, template.run(enchant, damager, damagee, inv -> inv.getItemInMainHand(), level -> {
        }));
    }

    @Test
    public void testArrowHitPlayerTemplate() {
        EventTemplate template = new ArrowHitPlayer();
        Arrow arrow = mock(Arrow.class);
        Player damager = mock(Player.class);
        Player damagee = mock(Player.class);

        PlayerInventory inventory = mock(PlayerInventory.class);
        World world = mock(World.class);

        when(arrow.getShooter()).thenReturn(damager);
        when(damagee.getInventory()).thenReturn(inventory);
        when(damagee.getWorld()).thenReturn(world);
        when(damager.getWorld()).thenReturn(world);

        when(world.getName()).thenReturn("");

        when(inventory.getItemInMainHand()).thenReturn(item);

        assertEquals(true, template.run(enchant, arrow, damagee, inv -> inv.getItemInMainHand(), level -> {
        }));
    }
}
package me.cristike.enchants.events;

import me.cristike.enchants.Main;
import me.cristike.enchants.utility.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class IClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (p.getInventory() != e.getClickedInventory()) return;
        if (e.getCurrentItem().getType() == Material.AIR) return;
        if (!Main.enchants.containsValue(e.getCursor())) return;
        if (!Util.canEnchant(e.getCurrentItem(), e.getCursor())) return;
        e.setCancelled(true);

        String enchantName = Util.getEnchantName(e.getCursor());
        int enchantLevel = Util.getEnchantLevel(e.getCursor());
        e.getCurrentItem().addUnsafeEnchantment(Enchantment.getByName(enchantName.toUpperCase()), enchantLevel);
        p.setItemOnCursor(new ItemStack(Material.AIR));
    }
}

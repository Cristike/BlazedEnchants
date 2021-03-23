package me.cristike.enchants;

import me.cristike.enchants.commands.EnchantBook;
import me.cristike.enchants.events.IClickEvent;
import me.cristike.enchants.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static HashMap<String, ItemStack> enchants = new HashMap<>();
    public static HashMap<String, List<String>> supportedItems = new HashMap<>();

    @Override
    public void onEnable() {
        load();
    }

    @Override
    public void onDisable() {
        unload();
        Bukkit.getConsoleSender().sendMessage(Util.color("&7[&bVanillaEnchantCommand&7] &fThe plugin has been &cdisabled"));
    }

    private void load() {
        saveDefaultConfig();
        instance = this;
        Util.loadEnchants();
        events();
        commands();
        Bukkit.getConsoleSender().sendMessage(Util.color("&7[&bVanillaEnchantCommand&7] &fThe plugin has been &aenabled"));
    }

    private void events() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new IClickEvent(), this);
    }

    private void commands() {
        this.getCommand("enchantbook").setExecutor(new EnchantBook());
    }

    private void unload() {

    }
}

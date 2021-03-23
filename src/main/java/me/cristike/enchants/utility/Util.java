package me.cristike.enchants.utility;

import me.cristike.enchants.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String toRomanCharacter(int number) {
        switch (number) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            default:
                return "V";
        }
    }

    public static void loadEnchants() {
        for (String key : Main.instance.getConfig().getConfigurationSection("Enchants").getKeys(false)) {
            int maxLevel = Main.instance.getConfig().getInt("Enchants." + key + ".MaxLevel");
            for (int i = 1; i <= maxLevel; i++) {
                ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
                StringBuilder hiddenLevel = new StringBuilder();
                for (char c : String.valueOf(i).toCharArray())
                    hiddenLevel.append(ChatColor.COLOR_CHAR).append(c);

                String displayName = color(Main.instance.getConfig().getString("Enchants." + key + ".DisplayName") + " " + hiddenLevel.toString());
                displayName = displayName.replace("{level}", toRomanCharacter(i));
                String description = color(Main.instance.getConfig().getString("Enchants." + key + ".Description"));
                List<String> lore = Main.instance.getConfig().getStringList("Item Lore");
                for (int k = 0; k < lore.size(); k++)
                    lore.set(k,
                            color(lore.get(k))
                                    .replace("{level}", toRomanCharacter(i))
                                    .replace("{description}", description)
                    );
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(displayName);
                meta.setLore(lore);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);

                List<String> supportedItems = Main.instance.getConfig().getStringList("Enchants." + key + ".Items");
                if (supportedItems.contains("Armor")) {
                    supportedItems.remove("Armor");
                    supportedItems.add("Helmet");
                    supportedItems.add("Chestplate");
                    supportedItems.add("Leggings");
                    supportedItems.add("Boots");
                }
                if (supportedItems.contains("Tools")) {
                    supportedItems.remove("Tools");
                    supportedItems.add("Axe");
                    supportedItems.add("Pickaxe");
                    supportedItems.add("Shovel");
                }

                Main.enchants.put(key + "_" + i, item);
                Main.supportedItems.put(key + "_" + i, supportedItems);
            }
        }
    }

    public static boolean isInteger(String input) {
        try {
            int i = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean canEnchant(ItemStack item, ItemStack enchantBook) {
        String enchantName = "";
        for (String key : Main.enchants.keySet())
            if (Main.enchants.get(key).isSimilar(enchantBook)) {
                enchantName = key;
                break;
            }
        List<String> materialName = Arrays.asList(item.getType().toString().toLowerCase().split("_"));

        for (String it : Main.supportedItems.get(enchantName))
            if (materialName.contains(it.toLowerCase()) || item.getType().toString().toLowerCase().replaceAll("_", " ").equals(it.toLowerCase()))
                return true;
        return false;
    }

    public static String getEnchantName(ItemStack enchantBook) {
        String enchantId = "";
        StringBuilder name = new StringBuilder();

        for (String key : Main.enchants.keySet())
            if (Main.enchants.get(key).isSimilar(enchantBook)) {
                enchantId = key;
                break;
            }

        String[] words = enchantId.split("_");
        for (int i = 0; i < words.length; i++)
            if (i != words.length-1)
                name.append(words[i]).append("_");

        return name.substring(0, name.length()-1);
    }

    public static int getEnchantLevel(ItemStack enchantBook) {
        String enchantId = "";
        for (String key : Main.enchants.keySet())
            if (Main.enchants.get(key).isSimilar(enchantBook)) {
                enchantId = key;
                break;
            }
        String[] words = enchantId.split("_");

        return Integer.parseInt(words[words.length-1]);
    }
}

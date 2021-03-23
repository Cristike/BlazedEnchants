package me.cristike.enchants.commands;

import me.cristike.enchants.Main;
import me.cristike.enchants.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantBook implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("enchantbook")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                giveItem(args, sender);
            }
            else {
                giveItem(args, sender);
            }
        }

        return true;
    }

    private void giveItem(String[] args, CommandSender sender) {
        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("give")) {
                Player t = Bukkit.getPlayer(args[1]);
                if (t != null && t.isOnline()) {
                    if (Main.instance.getConfig().getConfigurationSection("Enchants").contains(args[2])) {
                        if (Util.isInteger(args[3])) {
                            if (Main.enchants.containsKey(args[2] + "_" + args[3])) {
                                if (Util.isInteger(args[4])) {
                                    int quantity = Integer.parseInt(args[4]);
                                    if (quantity > 0 && quantity < 36) {
                                        for (int i = 1; i <= Integer.parseInt(args[4]); i++)
                                            t.getInventory().addItem(Main.enchants.get(args[2] + "_" + args[3]));
                                        sender.sendMessage(Util.color(Main.instance.getConfig().getString("EnchantBooksAdded")));
                                    }
                                    else sender.sendMessage(Util.color(Main.instance.getConfig().getString("QuantityOutOfBounds")));
                                }
                                else sender.sendMessage(Util.color(Main.instance.getConfig().getString("ArgumentNotANumber")));
                            }
                            else sender.sendMessage(Util.color(Main.instance.getConfig().getString("LevelOutOfBounds")));
                        }
                        else sender.sendMessage(Util.color(Main.instance.getConfig().getString("ArgumentNotANumber")));
                    }
                    else sender.sendMessage(Util.color(Main.instance.getConfig().getString("InvalidEnchant")));
                }
                else sender.sendMessage(Util.color(Main.instance.getConfig().getString("InvalidPlayer")));
            }
            else sender.sendMessage(Util.color(Main.instance.getConfig().getString("Usage")));
        }
        else sender.sendMessage(Util.color(Main.instance.getConfig().getString("Usage")));
    }
}

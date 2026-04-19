package com.berg.maskedLoot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.addon.JavaAddon;
import com.berg.maskedLoot.command.Commands;
import com.berg.maskedLoot.config.MaskedLootConfig;
import com.berg.maskedLoot.listener.InventoryListener;

public final class MaskedLoot extends JavaAddon {

    private static MaskedLoot instance;
    private MaskedLootConfig maskedConfig;

    @Override
    public void onLoad() {
        instance = this;
        maskedConfig = new MaskedLootConfig(getLogger());
        Commands.register();
    }

    @Override
    public void onEnable() {
        Plugin hostPlugin = BAirDropX.getInstance();
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), hostPlugin);
        getLogger().info("Скрытие лута включено");
        getLogger().info("Конфиг: " + maskedConfig.getAddonFolder().getAbsolutePath() + "/config.yml");
    }

    @Override
    public void onDisable() {
        getLogger().info("Скрытие лута отключено");
    }

    public void reloadMaskedConfig(CommandSender sender) {
        maskedConfig.reload();
        if (sender != null) {
            sender.sendMessage(ChatColor.GREEN + "[MaskedLoot] конфиг успешно перезагружен!");
        }
    }

    public static MaskedLoot getInstance() {
        return instance;
    }

    public MaskedLootConfig getMaskedConfig() {
        return maskedConfig;
    }
}
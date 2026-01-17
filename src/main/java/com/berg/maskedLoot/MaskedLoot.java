package com.berg.maskedLoot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.addon.JavaAddon;
import com.berg.maskedLoot.command.Commands;
import com.berg.maskedLoot.listener.InventoryListener;

public final class MaskedLoot extends JavaAddon {

    private static MaskedLoot instance;

    @Override
    public void onLoad() {
        instance = this;
        new Commands();
    }

    @Override
    public void onEnable() {
        Plugin hostPlugin = BAirDropX.getInstance();
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), hostPlugin);
        getLogger().info("Скрытие лута включено!");
    }

    @Override
    public void onDisable() {}

    public static MaskedLoot getInstance() {
        return instance;
    }
}
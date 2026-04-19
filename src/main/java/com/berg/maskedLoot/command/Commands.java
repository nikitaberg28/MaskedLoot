package com.berg.maskedLoot.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.command.CommandRegistry;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import com.berg.maskedLoot.MaskedLoot;
import com.berg.maskedLoot.config.MaskedLootConfig;
import com.berg.maskedLoot.util.ItemSerializer;

import java.util.List;
import java.util.Random;

public class Commands {

    public static final NamespacedKey HIDDEN_ITEM_KEY =
            new NamespacedKey(BAirDropX.getInstance(), "masked_loot_data");

    private static final Random random = new Random();

    public static void register() {
        // Команда маскировки инвентаря
        CommandRegistry.registerCommand(
                new Command("[MASK-LOOT-INV]")
                        .executor((eventObj, args) -> {
                            Event event = (Event) eventObj;
                            Inventory inv = event.getAirDrop().getInventoryManager().getInventory();
                            MaskedLootConfig cfg = MaskedLootConfig.getInstance();
                            maskInventory(inv);
                            int delay = cfg.getSmoothAddDelay();
                            int total = cfg.getSmoothAddTicks();
                            for (int tick = delay; tick <= total; tick += delay) {
                                final int t = tick;
                                Bukkit.getScheduler().runTaskLater(
                                        BAirDropX.getInstance(),
                                        () -> maskInventory(inv),
                                        t
                                );
                            }
                        })
        );

        CommandRegistry.registerCommand(
                new Command("[MASK-LOOT-RELOAD]")
                        .executor((eventObj, args) -> {
                            MaskedLoot.getInstance().reloadMaskedConfig(null);
                        })
        );
    }

    /**
     * маскирует все немаскированные предметы в инвентаре
     */
    public static void maskInventory(Inventory inv) {
        MaskedLootConfig cfg = MaskedLootConfig.getInstance();
        List<Material> masks = cfg.getMaskMaterials();
        String itemName = ChatColor.translateAlternateColorCodes('&', cfg.getHiddenItemName());

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack realItem = inv.getItem(i);

            if (realItem == null || realItem.getType() == Material.AIR) continue;
            if (isMasked(realItem)) continue; // уже замаскирован = скипаем

            // случайный материал
            Material maskMat = masks.get(random.nextInt(masks.size()));
            ItemStack maskedItem = new ItemStack(maskMat);

            ItemMeta meta = maskedItem.getItemMeta();
            if (meta != null) {
                String serialized = ItemSerializer.toBase64(realItem);
                meta.getPersistentDataContainer().set(
                        HIDDEN_ITEM_KEY,
                        PersistentDataType.STRING,
                        serialized
                );
                meta.setDisplayName(itemName);
                maskedItem.setItemMeta(meta);
            }

            inv.setItem(i, maskedItem);
        }
    }

    /**
     * чекаю замаскирован?
     */
    public static boolean isMasked(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null &&
                meta.getPersistentDataContainer().has(HIDDEN_ITEM_KEY, PersistentDataType.STRING);
    }

    /**
     * разворачиваем премдет
     */
    public static ItemStack unmask(ItemStack item) {
        if (!isMasked(item)) return null;
        try {
            String encoded = item.getItemMeta()
                    .getPersistentDataContainer()
                    .get(HIDDEN_ITEM_KEY, PersistentDataType.STRING);
            return ItemSerializer.fromBase64(encoded);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
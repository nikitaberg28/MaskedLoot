package com.berg.maskedLoot.command;

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
import com.berg.maskedLoot.util.ItemSerializer;
import java.util.Random;

public class Commands {
    public static final NamespacedKey HIDDEN_ITEM_KEY = new NamespacedKey(BAirDropX.getInstance(), "masked_loot_data");
    private static final Random random = new Random();

    static {
        CommandRegistry.registerCommand((new Command("[MASK-LOOT-INV]"))
                .executor((eventObj, args) -> {
                    Event event = (Event) eventObj;
                    Inventory inv = event.getAirDrop().getInventoryManager().getInventory();

                    for (int i = 0; i < inv.getSize(); i++) {
                        ItemStack realItem = inv.getItem(i);

                        if (realItem == null || realItem.getType() == Material.AIR) continue;
                        Material maskMat = random.nextBoolean() ? Material.GUNPOWDER : Material.NAUTILUS_SHELL;
                        ItemStack maskedItem = new ItemStack(maskMat);

                        ItemMeta meta = maskedItem.getItemMeta();
                        if (meta != null) {
                            String serialized = ItemSerializer.toBase64(realItem);
                            meta.getPersistentDataContainer().set(HIDDEN_ITEM_KEY, PersistentDataType.STRING, serialized);
                            meta.setDisplayName("§7Скрытый предмет");
                            maskedItem.setItemMeta(meta);
                        }
                        inv.setItem(i, maskedItem);
                    }
                }));
    }
}
package com.berg.maskedLoot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import com.berg.maskedLoot.command.Commands;
import com.berg.maskedLoot.util.ItemSerializer;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || !clickedItem.hasItemMeta()) return;
        ItemMeta meta = clickedItem.getItemMeta();
        if (meta.getPersistentDataContainer().has(Commands.HIDDEN_ITEM_KEY, PersistentDataType.STRING)) {
            String encodedData = meta.getPersistentDataContainer().get(Commands.HIDDEN_ITEM_KEY, PersistentDataType.STRING);

            try {
                ItemStack realItem = ItemSerializer.fromBase64(encodedData);
                event.setCurrentItem(realItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
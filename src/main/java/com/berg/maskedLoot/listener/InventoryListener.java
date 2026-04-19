package com.berg.maskedLoot.listener;

import com.berg.maskedLoot.command.Commands;
import com.berg.maskedLoot.config.MaskedLootConfig;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        revealIfMasked(event.getCurrentItem(), event);
        revealCursorIfMasked(event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!MaskedLootConfig.getInstance().isDragRevealEnabled()) return;

        ItemStack cursor = event.getOldCursor();
        if (cursor == null) return;
        ItemStack real = Commands.unmask(cursor);
        if (real != null) {
            event.getView().setCursor(real);
        }
    }

     // фикс для аддона spreadingitems по запросу https://goo.su/2zpK4
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPickupItem(EntityPickupItemEvent event) {
        if (!MaskedLootConfig.getInstance().isPickupRevealEnabled()) return;
        if (!(event.getEntity() instanceof Player)) return;

        Item itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItemStack();
        ItemStack real = Commands.unmask(stack);
        if (real != null) {
            itemEntity.setItemStack(real);
        }
    }
    private void revealIfMasked(ItemStack item, InventoryClickEvent event) {
        if (item == null) return;
        ItemStack real = Commands.unmask(item);
        if (real != null) {
            event.setCurrentItem(real);
        }
    }

    private void revealCursorIfMasked(InventoryClickEvent event) {
        ItemStack cursor = event.getCursor();
        if (cursor == null) return;
        ItemStack real = Commands.unmask(cursor);
        if (real != null) {
            event.getView().setCursor(real);
        }
    }
}
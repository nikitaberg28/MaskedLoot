package com.berg.maskedLoot.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.by1337.bairx.BAirDropX;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MaskedLootConfig {

    private static MaskedLootConfig instance;
    private final File addonFolder;
    private final File configFile;
    private FileConfiguration config;
    private final Logger logger;

    private String hiddenItemName;
    private List<Material> maskMaterials;
    private int smoothAddDelay;
    private int smoothAddTicks;
    private boolean pickupRevealEnabled;
    private boolean dragRevealEnabled;

    public MaskedLootConfig(Logger logger) {
        this.logger = logger;
        this.addonFolder = new File(BAirDropX.getInstance().getDataFolder(), "addons/MaskedLoot");
        this.configFile = new File(addonFolder, "config.yml");
        instance = this;
        load();
    }

    public void load() {
        if (!addonFolder.exists()) {
            addonFolder.mkdirs();
        }
        if (!configFile.exists()) {
            createDefaultConfigFile();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        hiddenItemName = config.getString("hidden-item-name", "&7Скрытый предмет");
        smoothAddDelay = config.getInt("smooth-add-item.check-delay-ticks", 2);
        smoothAddTicks = config.getInt("smooth-add-item.total-check-ticks", 40);
        pickupRevealEnabled = config.getBoolean("reveal-on-pickup", true);
        dragRevealEnabled = config.getBoolean("reveal-on-drag", true);

        maskMaterials = new ArrayList<>();
        List<String> matNames = config.getStringList("mask-materials");
        for (String name : matNames) {
            try {
                maskMaterials.add(Material.valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException e) {
                logger.warning("[MaskedLoot] Неизвестный материал: " + name);
            }
        }

        if (maskMaterials.isEmpty()) {
            maskMaterials.add(Material.GUNPOWDER);
            maskMaterials.add(Material.NAUTILUS_SHELL);
            logger.warning("[MaskedLoot] mask-materials пуст или все значения невалидны. Используются дефолтные.");
        }

        logger.info("[MaskedLoot] Конфиг загружен. Материалов масок: " + maskMaterials.size());
    }

    public void reload() {
        load();
        logger.info("[MaskedLoot] Конфиг перезагружен!");
    }

    private void createDefaultConfigFile() {
        String content =
                "# by nikitaberg -> https://nikitaberg.ru\n\n" +
                        "# название замаскированного предмета\n" +
                        "hidden-item-name: '&7Скрытый предмет'\n\n" +
                        "# материалы, которые используются как маска для предмета\n" +
                        "mask-materials:\n" +
                        "  - GUNPOWDER\n" +
                        "  - NAUTILUS_SHELL\n\n" +
                        "# настройки совместимости с smooth_add_item\n" +
                        "# аддон будет повторно маскировать предметы в течение total-check-ticks тиков\n" +
                        "# с интервалом check-delay-ticks, пока анимация добавляет предметы\n" +
                        "smooth-add-item:\n" +
                        "  check-delay-ticks: 1\n" +
                        "  total-check-ticks: 300\n\n" +
                        "# раскрывать маску когда игрок подбирает предмет с земли?\n" +
                        "# включи если используешь аддон SpreadingItems\n" +
                        "reveal-on-pickup: true\n\n" +
                        "# раскрывать маску при перетаскивании в инвентаре?\n" +
                        "reveal-on-drag: true\n";

        try {
            Files.write(configFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.severe("Не удалось создать config.yml: " + e.getMessage());
        }
    }

    public static MaskedLootConfig getInstance() { return instance; }
    public String getHiddenItemName() { return hiddenItemName; }
    public List<Material> getMaskMaterials() { return maskMaterials; }
    public int getSmoothAddDelay() { return smoothAddDelay; }
    public int getSmoothAddTicks() { return smoothAddTicks; }
    public boolean isPickupRevealEnabled() { return pickupRevealEnabled; }
    public boolean isDragRevealEnabled() { return dragRevealEnabled; }
    public File getAddonFolder() { return addonFolder; }
}
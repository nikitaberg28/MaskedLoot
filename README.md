# MaskedLoot - Аддон на скрытие лута для BAirDropX

![JavaSpigotPaper](https://img.shields.io/badge/Paper/Spigot-Java-orange.svg)
[![BAirDropX](https://img.shields.io/badge/BAirDropX-1.0.2-green.svg)](https://github.com/By1337/BAirDropX)

Аддон добавляет функционал "Скрытия лута в ивентах" аналогичный проекту FunTime для BAirDropX

## Установка
1. Скачайте `MaskedLoot.jar`
2. Поместите в папку `plugins/BAirDropX/addons/`
3. Перезапустите сервер и вставьте команду `[MASK-LOOT-INV]` в listeners в ивенте open

Пример OPEN в `listeners.yml`:
```yaml
  open:
    description: '&fГенерирует лут в аирдропе'
    event: 'open'
    commands:
      - '[INV_MANAGER] [GENERATE_LOOT]'
      - '[MASK-LOOT-INV]' # <- вот эта команда
      - '[HOLOGRAM] [REMOVE] holoTimeToOpen'
      - '[HOLOGRAM] [CREATE] hologramTimeToEnd holoTimeToEnd'
```

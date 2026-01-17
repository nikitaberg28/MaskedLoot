# MaskedLoot - Аддон на скрытие лута для BAirDropX

![JavaSpigotPaper](https://img.shields.io/badge/Paper/Spigot-Java-orange.svg)
[![BAirDropX](https://img.shields.io/badge/BAirDropX-1.0.2-green.svg)](https://github.com/By1337/BAirDropX)

Аддон добавляет функционал "Скрытия лута в ивентах" аналогичный проекту FunTime для BAirDropX

## Установка
1. Скачайте `MaskedLoot.jar`
2. Поместите в папку `plugins/BAirDropX/addons/`
3. Перезапустите сервер и вставьте команду `[MASK-LOOT-INV]` в listeners в ивенте open

## Скриншоты
<img width="432" height="267" alt="image" src="https://github.com/user-attachments/assets/9ed731bd-4239-43d5-a6ab-4e1369bbbbd8" />
<img width="410" height="468" alt="image" src="https://github.com/user-attachments/assets/91bbd292-8a48-42c5-997d-8ef62b9fc059" />


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

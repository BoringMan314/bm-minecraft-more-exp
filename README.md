# [B.M] Minecraft 更多經驗

[![Paper](https://img.shields.io/badge/Paper-26.3-2D2D2D)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![GitHub](https://img.shields.io/badge/GitHub-bm--minecraft--more--exp-181717?logo=github)](https://github.com/BoringMan314/bm-minecraft-more-exp)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

適用於 **Minecraft Paper 26.3** 的插件：玩家挖掘自然生成的實心方塊時，可獲得設定數量的經驗值。

*适用于 **Minecraft Paper 26.3** 的插件：挖掘自然生成的实体方块即可获得经验值。*<br>
*Minecraft Paper 26.3 向け：自然生成された固体ブロックを採掘すると経験値を獲得できます。*<br>
*A **Minecraft Paper 26.3** plugin that awards experience for mining naturally generated solid blocks.*

> **說明**：Minecraft 不會保存方塊是否由玩家放置的原生標記。插件會追蹤啟用後玩家放置的方塊，避免反覆放置與挖掘取得經驗。

---

## 目錄

- [功能](#功能)
- [系統需求](#系統需求)
- [安裝方式](#安裝方式)
- [指令與權限](#指令與權限)
- [設定檔](#設定檔)
- [本機建置](#本機建置)
- [專案結構](#專案結構)
- [版本與多語系](#版本與多語系)
- [資料與隱私說明](#資料與隱私說明)
- [授權](#授權)
- [問題與建議](#問題與建議)

---

## 功能

- 挖掘符合條件的自然實心方塊時掉落設定數量的經驗值。
- 追蹤玩家放置方塊，避免藉由放置、挖掘循環刷經驗。
- 可設定功能開關、每方塊經驗、允許的遊戲模式與權限要求。
- OP 或控制台可即時開關、調整數值、查看狀態與重載設定。
- 內建四種語系 JAR。

---

## 系統需求

- **Paper 26.3** 伺服器。
- **Java 25**。

---

## 安裝方式

1. 從 [`dist/`](dist/) 選擇所需語系 JAR。
2. 將 JAR 放入 Paper 伺服器的 `plugins/` 資料夾。
3. 啟動伺服器後，設定檔建立於 `plugins/bm-minecraft-more-exp/`。

> 請勿同時安裝多個語系 JAR。

---

## 指令與權限

| 指令 | 可使用者 | 說明 |
|---|---|---|
| `/bm-minecraft-more-exp` | 所有人 | 顯示指令說明。 |
| `/bm-minecraft-more-exp 0\|1` | OP／控制台 | 關閉或開啟挖礦經驗。 |
| `/bm-minecraft-more-exp info\|status\|reload` | OP／控制台 | 顯示資訊、狀態或重載設定與語系。 |
| `/bm-minecraft-more-exp set <數量>` | OP／控制台 | 設定每個自然方塊給予的經驗。 |

| 權限 | 預設 | 說明 |
|---|---|---|
| `bm-minecraft-more-exp.receive` | `true` | 在啟用權限要求時允許獲得經驗。 |
| `bm-minecraft-more-exp.admin` | `op` | 允許使用管理指令。 |

---

## 設定檔

| 設定 | 預設值 | 說明 |
|---|---:|---|
| `enabled` | `true` | 是否啟用挖礦經驗。 |
| `experience-per-block` | `1` | 每個方塊給予的經驗。 |
| `allowed-game-modes` | `SURVIVAL` | 可獲得經驗的遊戲模式。 |
| `require-receive-permission` | `false` | 是否要求 `.receive` 權限。 |
| `track-player-placed-blocks` | `true` | 是否追蹤玩家放置方塊。 |
| `untracked-blocks-count-as-natural` | `true` | 未記錄方塊是否視為自然生成。 |

完整設定請見 [`config.yml`](src/main/resources/config.yml)，玩家文字位於 [`lang/`](src/main/resources/lang/)。

---

## 本機建置

```bat
build.bat
```

建置完成後會在 `dist/` 產生四份語系 JAR。

---

## 專案結構

| 路徑 | 說明 |
|---|---|
| [`src/main/java/bm.minecraft.more.exp/`](src/main/java/bm.minecraft.more.exp/) | 主類別、指令、事件與玩家放置方塊追蹤。 |
| [`src/main/resources/`](src/main/resources/) | Paper 資訊、功能設定與語系檔。 |
| `placed-blocks.yml` | 伺服器執行後建立的方塊追蹤資料。 |

---

## 版本與多語系

- **插件版本**：`26.3_0.0.1`
- **目標 API**：Paper `26.3`
- **內建語系**：`zh_TW`、`zh_CN`、`ja_JP`、`en_US`

---

## 資料與隱私說明

本插件只在伺服器本機儲存玩家放置方塊追蹤資料，不會上傳資料、不含分析或廣告追蹤。

---

## 授權

本專案以 [MIT License](LICENSE) 授權。

---

## 問題與建議

歡迎透過 [GitHub Issues](https://github.com/BoringMan314/bm-minecraft-more-exp/issues) 回報錯誤或提出改善建議。

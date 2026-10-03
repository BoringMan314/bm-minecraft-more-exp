# [B.M] Minecraft 更多經驗

[![Paper](https://img.shields.io/badge/Paper-26.3-2D2D2D)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![GitHub](https://img.shields.io/badge/GitHub-bm--minecraft--more--exp-181717?logo=github)](https://github.com/BoringMan314/bm-minecraft-more-exp)
[![GitHub all releases](https://img.shields.io/github/downloads/BoringMan314/bm-minecraft-more-exp/total)](https://github.com/BoringMan314/bm-minecraft-more-exp/releases)
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
- [本機開發與測試](#本機開發與測試)
- [技術概要](#技術概要)
- [專案結構](#專案結構)
- [版本與多語系](#版本與多語系)
- [資料與隱私說明](#資料與隱私說明)
- [維護者：更新 GitHub 與發行版本](#維護者更新-github-與發行版本)
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

### 從 GitHub Releases 安裝

若 [GitHub Releases](https://github.com/BoringMan314/bm-minecraft-more-exp/releases) 已提供 JAR，請選擇所需語系下載；尚無發行檔時，可依下方步驟自行建置。

1. 停止伺服器，將所選 JAR 放入 `plugins/` 資料夾。
2. 啟動 **Paper 26.3** 伺服器，確認控制台顯示插件已啟用。
3. 在 `plugins/bm-minecraft-more-exp/` 調整設定；指令與設定項目請見下方說明。

> 同一插件只安裝一份語系 JAR；更新時請移除舊版 JAR。

### 從原始碼建置

1. 點選本頁綠色 **Code** → **Download ZIP** 解壓，或執行 `git clone https://github.com/BoringMan314/bm-minecraft-more-exp.git`。
2. 依 [本機開發與測試](#本機開發與測試) 準備 JDK 與 Maven，執行建置。
3. 從本機 `dist/` 選取 `bm-minecraft-more-exp_26.3_0.0.1-<語系>.jar`，依上方步驟安裝。

---

## 指令與權限

| 指令 | 可使用者 | 說明 |
|------|------|------|
| `/bm-minecraft-more-exp` | 所有人 | 顯示指令說明。 |
| `/bm-minecraft-more-exp 0\|1` | OP／控制台 | 關閉或開啟挖礦經驗。 |
| `/bm-minecraft-more-exp info\|status\|reload` | OP／控制台 | 顯示資訊、狀態或重載設定與語系。 |
| `/bm-minecraft-more-exp set <數量>` | OP／控制台 | 設定每個自然方塊給予的經驗。 |

| 權限 | 預設 | 說明 |
|------|------|------|
| `bm-minecraft-more-exp.receive` | `true` | 在啟用權限要求時允許獲得經驗。 |
| `bm-minecraft-more-exp.admin` | `op` | 允許使用管理指令。 |

---

## 設定檔

| 設定 | 預設值 | 說明 |
|------|------:|------|
| `enabled` | `true` | 是否啟用挖礦經驗。 |
| `experience-per-block` | `1` | 每個方塊給予的經驗。 |
| `allowed-game-modes` | `SURVIVAL` | 可獲得經驗的遊戲模式。 |
| `require-receive-permission` | `false` | 是否要求 `.receive` 權限。 |
| `track-player-placed-blocks` | `true` | 是否追蹤玩家放置方塊。 |
| `untracked-blocks-count-as-natural` | `true` | 未記錄方塊是否視為自然生成。 |

完整設定請見 [`config.yml`](src/main/resources/config.yml)，玩家文字位於 [`lang/`](src/main/resources/lang/)。

---

## 本機開發與測試

**Windows / PowerShell：**

1. 準備 **JDK 25**：放在 `.tools/<JDK 資料夾>/`，或安裝至可由 Windows `JavaSoft\JDK\25` 登錄項目辨識的位置。
2. 準備 **Maven**：放在 `.tools/apache-maven-3.9.11/`，或讓 `mvn.cmd` 可從 `PATH` 執行。首次建置需連線下載依賴。
3. 在專案根目錄執行：

```powershell
.\build.bat --no-pause
```

建置完成後，`dist/` 會產生 `zh_TW`、`zh_CN`、`ja_JP`、`en_US` 四份語系 JAR。直接執行 `build.bat` 會在結束時暫停；`--no-pause` 適合終端與自動化使用。

修改 [`src/main/java/`](src/main/java/) 或 [`src/main/resources/`](src/main/resources/) 後，重新建置並更換測試伺服器的 JAR，重新啟動伺服器，驗證 [功能](#功能) 及 [指令與權限](#指令與權限) 中的操作。`dist/`、`target/` 與 `.tools/` 由 [`.gitignore`](.gitignore) 排除，不會隨原始碼上傳。

---

## 技術概要

- **核心實作**：監聽方塊放置與破壞事件，搭配本機放置紀錄判斷是否給予挖掘經驗。
- **指令與權限**：由 [`plugin.yml`](src/main/resources/plugin.yml) 宣告，實際管理限制由指令處理程式與設定共同決定。
- **建置與語系**：使用 [`pom.xml`](pom.xml) 定義依賴，由 [`build.bat`](build.bat) 依語系逐次執行 Maven 建置。

---

## 專案結構

| 路徑 | 說明 |
|------|------|
| [`pom.xml`](pom.xml) | 插件版本、Java 版本、Paper API 依賴與 Maven 建置設定 |
| [`build.bat`](build.bat) | Windows 四語系 JAR 建置腳本 |
| [`src/main/java/bm.minecraft.more.exp/`](src/main/java/bm.minecraft.more.exp/) | 插件主類別與功能實作 |
| [`src/main/resources/plugin.yml`](src/main/resources/plugin.yml) | 插件資訊、指令與權限宣告 |
| [`src/main/resources/config.yml`](src/main/resources/config.yml) | 功能與管理設定 |
| [`src/main/resources/active-language.yml`](src/main/resources/active-language.yml) | 建置時套用的預設語系 |
| [`src/main/resources/lang/`](src/main/resources/lang/) | 四種語系的訊息 |
| [`.gitignore`](.gitignore) | 本機工具、暫存與建置產物的排除規則 |

---

## 版本與多語系

- **插件版本**：目前為 `26.3_0.0.1`，建置設定見 [`pom.xml`](pom.xml)。
- **目標 API**：Paper `26.3`；實際依賴版本見 `pom.xml` 的 `paper.version`。
- **預設語系**：`zh_TW`，由 Maven 的 `default.language` 設定。
- **內建語系**：`zh_TW`、`zh_CN`、`ja_JP`、`en_US`（路徑為 `src/main/resources/lang/<語系>.yml`）。
- **語系選擇**：建置腳本將不同預設語系分別打包為 JAR；執行時依 `active-language.yml` 載入對應語系。

---

## 資料與隱私說明

本插件只在伺服器本機儲存玩家放置方塊追蹤資料，不會上傳資料、不含分析或廣告追蹤。

---

## 維護者：更新 GitHub 與發行版本

### 更新至 GitHub

在專案根目錄執行：

```powershell
git add README.md
git commit -m "V26.3_0.0.1"
git push origin main
```

### 準備發行檔

1. 確認 [`pom.xml`](pom.xml)、[`build.bat`](build.bat) 與 [`plugin.yml`](src/main/resources/plugin.yml) 中的版本設定一致。
2. 執行 `build.bat --no-pause`，並在測試伺服器驗證功能及語系顯示。
3. 在 [GitHub Releases](https://github.com/BoringMan314/bm-minecraft-more-exp/releases) 建立對應版本，附上本機 `dist/` 中的四份語系 JAR 與更新說明。

---

## 授權

本專案以 [MIT License](LICENSE) 授權。

---

## 問題與建議

歡迎透過 [GitHub Issues](https://github.com/BoringMan314/bm-minecraft-more-exp/issues) 回報錯誤或提出改善建議。回報時請一併提供 Paper 版本、Java 版本、插件版本、**語系**及重現步驟；若有錯誤，請附上相關設定與錯誤日誌。

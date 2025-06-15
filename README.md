# 目標：オフラインでも mvn clean install や IntelliJ 上のビルド・起動ができるようにする
## 🔧 1. オフライン環境への準備（オンライン環境でやる）
### 🧱 必要なもの：
```
プロジェクト一式（pom.xml, src/, application.yml など）

.m2/repository/ 以下の Mavenローカルリポジトリ

オプション：settings.xml
```

### 📁 手順 ①：プロジェクトの依存を全てダウンロード
```
オンライン環境で以下を実行：

mvn dependency:go-offline
これにより、必要な依存（pom.xmlで使っている全JAR）が .m2/repository/ に事前ダウンロードされます。
```

### 📁 手順 ②：以下のものを USB などでコピーする
```
<プロジェクトフォルダ>/
├── pom.xml
├── src/
├── application.yml
├── .mvn/            ← 自動生成される場合あり
└── .m2/repository/  ← Maven のローカルキャッシュ全体
または .m2/repository/ を ZIP にして持ち込んでもOKです。
```

## ⚙️ 2. オフライン環境の設定
### 📁 手順 ①：.m2/repository/ を所定の場所に設置
```
Windows → C:\Users\<ユーザー名>\.m2\repository
```

### 📁 手順 ②：settings.xml をオフライン設定に変更（任意）
```
~/.m2/settings.xml に以下を追加すると Maven がネットに出ようとしなくなります：
```

```
xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
https://maven.apache.org/xsd/settings-1.0.0.xsd">
<offline>true</offline>
</settings>
```

```
ない場合は新規作成してOKです。
```

## 🧠 IntelliJ IDEA の設定（オフラインモード）
### ✅ 方法 ①：グローバルに Maven をオフラインにする
```
File → Settings → Build, Execution, Deployment → Build Tools → Maven

「Work offline」 にチェック
```

### ✅ 方法 ②：プロジェクトごとに切り替え
```
Maven タブ（右側） → 歯車マーク → Always Work Offline にチェック
```

### ✅ IntelliJ でプロジェクトを起動・ビルド
```
オフライン環境でも以下は可能：

ビルド（Build → Build Project）

Spring Boot アプリ起動（DemoApplication.java から Run）
```

### ✅ 注意点
```
項目	注意
.m2/repository は完全な状態でコピー	mvn dependency:go-offline 実行後であること
IntelliJ の Maven 設定を「Work offline」にする	IntelliJ が勝手に依存解決しようとしないように
```

Spring Boot DevTools なども含めておく	ランタイム依存も事前に含める

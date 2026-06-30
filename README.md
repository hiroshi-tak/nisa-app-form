
# NISA App

## 概要

初心者向けの新NISA積立支援アプリです。

人気ファンド比較、積立シミュレーション、
モンテカルロシミュレーションによるリスク分析、
Gemini APIを利用したAI解説機能を提供します。

## 画面イメージ

### 人気ファンド比較

![funds](docs/images/funds.png)

### 積立シミュレーション

![simulation](docs/images/simulation.png)

## 環境構築
```bash
git clone <repository>
cd nisa-app

cp .env.example .env
# GEMINI_API_KEYを設定

docker compose up --build
```

## DBリセット
```bash
docker compose down -v
docker compose up --build 
``` 

## 使用技術

- Frontend: Next.js 16 / TypeScript
- Backend: Spring Boot 3
- Database: PostgreSQL 16
- Authentication: JWT
- AI: Gemini API
- Container: Docker Compose

## システム構成図
```mermaid
graph TD
    Browser --> Next.js
    Next.js -->|REST API| SpringBoot
    SpringBoot -->|Spring Data JPA| PostgreSQL
    SpringBoot -->|Gemini API| Gemini
```

## 認証方式

JWT認証（Cookieベース）

Access Token（有効期限15分）
- HttpOnly Cookie（token）で管理
- API認証に使用

Refresh Token（有効期限7日）
- HttpOnly Cookie（refresh_token）で管理
- Access Token再発行に使用

トークン更新
- フロントはAxios/fetchで401/403を検知
- /api/auth/refresh により自動更新
- 成功時はAccess Tokenを再発行し再リクエスト

## テストユーザー
開発環境では以下のテストユーザーが登録されています。

| ユーザー名 | パスワード |
|-----------|-----------|
| test | 12345678 |

## 開発環境

- トップ画面:http://localhost:3000

## ER図
```mermaid
erDiagram USER ||--o{ POST : has USER { long id string username string password }
```

## シーケンス図
```mermaid
sequenceDiagram
    autonumber

    participant C as Client (Next.js)
    participant B as Browser
    participant A as Spring Boot API
    participant F as JwtAuthenticationFilter

    %% =========================
    %% LOGIN
    %% =========================
    C->>A: POST /api/auth/login (username/password)
    A->>A: 認証処理
    A->>A: Access Token生成
    A->>A: Refresh Token生成
    A-->>B: Set-Cookie (access token)
    A-->>B: Set-Cookie (refresh token)
    B-->>C: login success

    %% =========================
    %% API ACCESS
    %% =========================
    C->>A: GET /api/xxx (credentials: include)
    B->>A: Cookie自動送信 (access + refresh)

    A->>F: リクエスト到達

    alt Access Token 有効
        F->>F: JWT検証成功
        F->>A: Controllerへ処理継続
        A-->>C: 200 OK
    else Access Token 期限切れ
        F->>F: JWT検証失敗
        F->>F: refresh_token検証
        F->>F: 新Access Token生成 & Cookie更新
        F->>A: 認証成功として継続
        A-->>C: 200 OK（リトライ不要）
    else refresh_token も無効
        F->>A: 401 Unauthorized
        A-->>C: ログアウト扱い
    end
```

## API仕様
### API Endpoints

- POST /api/simulation
- POST /api/simulation/montecarlo
- POST /api/simulation/montecarlo/explain
- POST /api/funds/analysis
- POST /api/auth/register
- POST /api/auth/refresh
- POST /api/auth/logout
- POST /api/auth/login
- GET /api/auth/me
- GET /api/funds
- GET /api/funds/score

### Swagger UI
```text 
http://localhost:8080/swagger-ui/index.html
```


## 入力制限について

### ユーザーAPI

| 項目 | 制約 |
|--------|--------|
| ユーザー名 | 登録済みのユーザー名は使用不可 |


### シミュレーションAPI

| 項目 | 制約 |
|--------|--------|
| 毎月積立金 | 0以上 |
| 年利 | -100〜100 |
| 運用年数 | 0〜100 |
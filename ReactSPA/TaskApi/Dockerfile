# ビルドステージ
FROM gradle:8.11.1-jdk17-alpine AS build
WORKDIR /app

# Gradle のキャッシュを活用するために、依存関係を先にコピー
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew dependencies --no-daemon

# ソースコードをコピーしてビルド
COPY src src
RUN ./gradlew bootJar --no-daemon

# 実行ステージ
FROM eclipse-temurin:17.0.13_11-jdk-alpine AS runtime
WORKDIR /app

# ビルド済み JAR ファイルをコピー
COPY --from=build /app/build/libs/SimpleTodo-0.0.1-SNAPSHOT.jar app.jar

# ポートを公開
EXPOSE 8080

# アプリケーションを実行
ENTRYPOINT ["java", "-jar", "app.jar"]
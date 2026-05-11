# 使用 Oracle 提供的 OpenJDK 17 作為執行環境
FROM eclipse-temurin:17-jre-jammy

# 設定工作目錄
WORKDIR /app

# 僅複製指定的專案 JAR 檔
# 這裡的路徑是相對於 Dockerfile 所在的根目錄
COPY target/project-CBS.jar app.jar

# 設定時區
ENV TZ=Asia/Taipei

# 設定 JVM 啟動參數 (例如限制記憶體使用量，避免容器被 OOM Kill)
# ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# 暴露應用程式埠號
EXPOSE 8081 8443

# 啟動指令，使用 shell 形式以支援環境變數
# ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

ENTRYPOINT ["java", "-jar", "app.jar"]
# Dùng Amazon Corretto 17 giống bản bạn đang dùng
FROM amazoncorretto:17

# Tạo thư mục làm việc
WORKDIR /app

# Copy file JAR vào container
COPY demo-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng 8080 (hoặc port khác nếu app bạn cấu hình)
EXPOSE 8080

# Lệnh chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

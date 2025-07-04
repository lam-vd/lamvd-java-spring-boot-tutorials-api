# lamvd-java-spring-boot-tutorials-api

## Giới thiệu

Đây là dự án Spring Boot API mẫu cho quản lý sản phẩm, sử dụng MySQL làm database.

## Công nghệ sử dụng

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## Cấu hình database

Thêm vào `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lamvd_java_tutorials
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Chạy dự án

1. Clone repo về máy.
2. Cài đặt MySQL và tạo database `lamvd_java_tutorials`.
3. Chạy lệnh:
   ```sh
   ./mvnw spring-boot:run
   ```
   hoặc dùng VS Code, mở file `LamvdJavaTutorialsApplication.java` và bấm Run.

## API mẫu

- Lấy tất cả sản phẩm:  
  `GET /api/v1/products`

- Lấy sản phẩm theo id:  
  `GET /api/v1/products/{id}`

- Thêm sản phẩm mới:  
  `POST /api/v1/products/insert`  
  Body (JSON):
  ```json
  {
  	"productName": "iPhone 14 Pro",
  	"year": 2022,
  	"price": 999.99,
  	"url": "https://www.apple.com/iphone-14-pro"
  }
  ```

## Ghi chú

- Đảm bảo đã cài Java, Maven, MySQL.
- Để tránh trùng sản phẩm, API sẽ kiểm tra tên sản phẩm trước khi thêm

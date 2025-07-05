package com.lamvdjavaturorials.Lamvd.java.tutorials.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService implements IStorageService {
  private final Path storageFolder = Paths.get("uploads");

  public ImageStorageService() {
    // Initialize the storage folder if necessary
    try {
      java.nio.file.Files.createDirectories(storageFolder);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage folder", e);
    }
  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.storageFolder, 1).filter(path -> !path.equals(this.storageFolder))
          .map(this.storageFolder::relativize);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load files", e);
    }
  }

  @Override
  public byte[] loadAsResource(String filename) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public byte[] readFileContent(String filename) {
    try {
      Path filePath = storageFolder.resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists() || resource.isReadable()) {
        // byte[] sẽ sử dụng StreamUtils để đọc dữ liệu từ InputStream của resource
        // copyToByteArray sẽ sao chép toàn bộ dữ liệu từ InputStream vào mảng byte
        // và trả về mảng byte đó
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return bytes;
      } else {
        throw new RuntimeException("File not found or not readable: " + filename);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file: " + filename, e);
    }
  }

  private boolean isImageFile(MultipartFile file) {
    // getExtension sẽ lấy phần mở rộng của tên file
    String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
    // contains sẽ kiểm tra xem fileExtension có nằm trong danh sách các định dạng
    // ảnh hay không
    // trim() sẽ loại bỏ khoảng trắng ở đầu và cuối chuỗi
    // toLowerCase() sẽ chuyển đổi chuỗi thành chữ thường để so sánh không phân biệt
    // chữ hoa và chữ thường
    return Arrays.asList(null, "jpg", "jpeg", "png", "gif").contains(fileExtension.trim().toLowerCase());
  }

  @Override
  public String storageFile(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new RuntimeException("Failed to store empty file.");
      }
      // isImageFile sẽ kiểm tra xem file có phải là ảnh hay không
      if (!isImageFile(file)) {
        throw new RuntimeException("File is not an image.");
      }
      // getSize sẽ trả về kích thước của file
      // 5 * 1024 * 1024 là kích thước tối đa của file (5MB)
      // Nếu kích thước của file lớn hơn 5MB thì sẽ ném ra một RuntimeException
      if (file.getSize() > 5 * 1024 * 1024) { // 5MB
        throw new RuntimeException("File size exceeds the limit of 5MB.");
      }
      String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
      String generateFileName = UUID.randomUUID().toString().replace("-", "");
      // Tạo tên file mới bằng UUID và thêm phần mở rộng của file
      // replace("-", "") sẽ loại bỏ dấu gạch ngang trong UUID
      generateFileName += "." + fileExtension;
      // Tạo đường dẫn đến file mới trong thư mục lưu trữ
      // normalize() sẽ chuẩn hóa đường dẫn để loại bỏ các phần không cần thiết
      // toAbsolutePath() sẽ chuyển đổi đường dẫn tương đối thành đường dẫn tuyệt đối
      Path destinationFile = this.storageFolder.resolve(Paths.get(generateFileName)).normalize();
      if (!destinationFile.getParent().toAbsolutePath().equals(this.storageFolder.toAbsolutePath())) {
        throw new RuntimeException("Cannot store file outside current directory.");
      }
      // Sử dụng InputStream để đọc dữ liệu từ file và ghi vào file đích
      // getInputStream() sẽ trả về InputStream của file
      // Files.copy sẽ sao chép dữ liệu từ InputStream vào file đích
      // StandardCopyOption.REPLACE_EXISTING sẽ ghi đè lên file đích nếu nó đã tồn tại
      // Nếu có lỗi xảy ra trong quá trình sao chép thì sẽ ném ra một RuntimeException
      // với thông báo lỗi
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
      return generateFileName;
    } catch (Exception e) {
      throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
    }
  }
}

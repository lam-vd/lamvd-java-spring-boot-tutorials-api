package com.lamvdjavaturorials.Lamvd.java.tutorials.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.lamvdjavaturorials.Lamvd.java.tutorials.models.ResponseObject;
import com.lamvdjavaturorials.Lamvd.java.tutorials.services.IStorageService;

@Controller
@RequestMapping("/api/v1/file-upload")
public class FileUploadController {

  @Autowired
  private IStorageService storageService;

  @PostMapping("")
  public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      // sử dụng service để lưu trữ file
      String generatedFileName = storageService.storageFile(file);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseObject("ok", "File uploaded successfully", generatedFileName));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
          .body(new ResponseObject("failed", "File upload failed: " + e.getMessage(), ""));
    }
  }

  @GetMapping("/files/{fileName:.*}")
  public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
    try {
      byte[] bytes = storageService.readFileContent(fileName);
      return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
    } catch (Exception e) {
      return ResponseEntity.noContent().build();
    }
  }

  // how to load all files
  @GetMapping("/files")
  public ResponseEntity<ResponseObject> getUploadedFiles() {
    try {
      List<String> urls = storageService.loadAll()
          .map(path -> {
            String urlPath = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class, "readDetailFile", path.getFileName().toString()).build()
                .toUri().toString();
            return urlPath;
          }).collect(Collectors.toList());
      return ResponseEntity.ok(new ResponseObject("ok", "list file successfully", urls));
    } catch (Exception e) {
      return ResponseEntity.ok(new ResponseObject("failed", "list file failed", new String[] {}));
    }
  }
}

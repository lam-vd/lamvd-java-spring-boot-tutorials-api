package com.lamvdjavaturorials.Lamvd.java.tutorials.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  public String storageFile(MultipartFile file);

  public Stream<Path> loadAll();

  public byte[] loadAsResource(String filename);

  public byte[] readFileContent(String filename);

  public void deleteAll();
}

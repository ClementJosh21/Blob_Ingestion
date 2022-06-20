package com.example.ingestion;

import com.example.ingestion.entity.FileEntity;
import com.example.ingestion.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IngestionApplication {

  @Autowired private FileRepository fileRepository;

  public static void main(String[] args) {
    SpringApplication.run(IngestionApplication.class, args);
  }

  @PostConstruct
  public void init() throws IOException {
    int i = 0;
    List<FileEntity> fileEntities = new ArrayList<>();
    File file =
        new File("/home/clement/Downloads/Blob_TABLE/PUBLIC/BLOB_TABLE/blobs/Blob-Table-00001");
    for (File listFile : file.listFiles()) {
      String[] split = listFile.getName().split("\\.");
      fileEntities.add(
          FileEntity.builder()
              .fileName(listFile.getName())
              .fileExtension(split[split.length - 1])
              .file(Files.readAllBytes(listFile.toPath()))
              .build());
      i++;
      if (fileEntities.size() == 10) {
        System.out.println(i + "/" + file.listFiles().length + " entries saved.");
        fileRepository.saveAll(fileEntities);
        fileEntities.clear();
      }
    }
    System.out.println(fileEntities.size());
    if (fileEntities.size() > 0) fileRepository.saveAll(fileEntities);
  }
}

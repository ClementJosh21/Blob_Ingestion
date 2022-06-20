package com.example.ingestion.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Table(name = "blob_table")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String fileName;

  private String fileExtension;

  @Type(type = "org.hibernate.type.ImageType")
  @Lob
  private byte[] file;
}

package com.photogallery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Photo")
public class Photo {

//    @Transient
//    public static final String SEQUENCE_NAME = "photo_sequence";

    @Id
    private long id;
    private long albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
    private LocalDateTime creDate;
    private LocalDateTime modDate;
}

package com.photogallery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Album")
public class Album {

//    @Transient
//    public static final String SEQUENCE_NAME = "album_sequence";

    @Id
    private long id;
    private long userId;
    private String title;
    private LocalDateTime creDate;
    private LocalDateTime modDate;

}

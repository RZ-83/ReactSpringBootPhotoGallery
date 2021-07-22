package com.photogallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {

    private long id;
    private long albumId;
    private AlbumDto albumDto;
    private String title;
    private String url;
    private String thumbnailUrl;
    private LocalDateTime creDate;
    private LocalDateTime modDate;

}

package com.photogallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {

    private long id;
    private long userId;
	private String title;
    private LocalDateTime creDate;
    private LocalDateTime modDate;

}

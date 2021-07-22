package com.photogallery.repository;

import com.photogallery.dto.AlbumDto;
import com.photogallery.entity.Album;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AlbumRepository extends ReactiveMongoRepository<Album,Long> {

    Flux<AlbumDto> findByUserId(long userId);

    Flux<AlbumDto> findByTitleContains(String title);

}

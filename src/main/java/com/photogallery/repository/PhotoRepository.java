package com.photogallery.repository;

import com.photogallery.dto.PhotoDto;
import com.photogallery.entity.Photo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PhotoRepository extends ReactiveMongoRepository<Photo,Long> {

    Flux<PhotoDto> findByAlbumId(long albumId);

}

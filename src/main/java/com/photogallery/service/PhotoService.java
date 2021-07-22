package com.photogallery.service;

import com.photogallery.dto.AlbumDto;
import com.photogallery.dto.PhotoDto;
import com.photogallery.repository.PhotoRepository;
import com.photogallery.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private SequenceGeneratorService sequenceService;

    @Autowired
    private Environment env;

    public Flux<PhotoDto> getPhotos(){

        return photoRepository.findAll().map(AppUtils::photoEntityToDto)
                .sort(Comparator.comparing(PhotoDto::getId));
    }

    public Mono<PhotoDto> getPhoto(long id){
        return photoRepository.findById(id).map(AppUtils::photoEntityToDto);
    }

    public Mono<PhotoDto> insertPhoto(Mono<PhotoDto> photoDtoMono){
        return photoDtoMono
                .map(photo -> {
                    //photo.setId(sequenceService.getSequenceNbr(Photo.SEQUENCE_NAME));
                    return AppUtils.updatePhotoCreDate(photo, LocalDateTime.now());
                })
                .map(AppUtils::photoDtoToEntity)
                .flatMap(photoRepository::insert)
                .map(AppUtils::photoEntityToDto);
    }

    public Mono<PhotoDto> updatePhoto(Mono<PhotoDto> photoDtoMono, long id){
        return  photoRepository.findById(id)
                .flatMap(a->photoDtoMono.map(AppUtils::photoDtoToEntity)
                                        .doOnNext(e-> { e.setId(id); e.setModDate(LocalDateTime.now()); }))
                .flatMap(photoRepository::save)
                .map(AppUtils::photoEntityToDto);
    }

    public Mono<Void> deleteAllPhotos(){
        return photoRepository.deleteAll().log();
    }

    public Mono<Void> deletePhoto(long id){
        return photoRepository.deleteById(id);
    }

    public Flux<PhotoDto> importAllPhotos() {

        Flux<PhotoDto> lstPhotos = AppUtils.importData(PhotoDto.class ,"https://jsonplaceholder.typicode.com/photos", webClientBuilder)
                .map(p -> {return AppUtils.updatePhotoCreDate(p,LocalDateTime.now());})
                .map(AppUtils::photoDtoToEntity)
                .flatMap(photoRepository::insert)
                .map(AppUtils::photoEntityToDto);

        /*
        Flux<PhotoDto> lstPhotos = (Flux<PhotoDto>) webClientBuilder.build()
                .get()
//                .uri(env.getProperty("photos.Url"))
                .uri("https://jsonplaceholder.typicode.com/photos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PhotoDto.class)
                .map(p -> {return AppUtils.updatePhotoModDate(p,LocalDateTime.now());})
                .map(AppUtils::photoDtoToEntity)
                .flatMap(photoRepository::insert)
                .map(AppUtils::photoEntityToDto);
*/
        return lstPhotos;
    }


    public Flux<PhotoDto> getPhotosByAlbumId(long albumId, AlbumDto albumDto) {
        System.out.println("getPhotosByAlbumId ALBUM ID["+albumId+"] + TITLE["+albumDto.getTitle()+"] + MODDATE["+albumDto.getModDate()+"]");
        Flux<PhotoDto> lstPhotos= photoRepository.findByAlbumId(albumId)
                .doOnNext(photoDto -> photoDto.setAlbumDto(albumDto));
        return lstPhotos;
    }

    public String sayHello(String user) {
        return "Hello " + user;
    }
}

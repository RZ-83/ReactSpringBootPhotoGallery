package com.photogallery.service;

import com.photogallery.dto.AlbumDto;
import com.photogallery.repository.AlbumRepository;
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
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    public Flux<AlbumDto> getAlbums(){

        return albumRepository.findAll().map(AppUtils::albumEntityToDto)
                .sort(Comparator.comparing(AlbumDto::getId))
                ;
    }

    public Mono<AlbumDto> getAlbum(long id){

        return albumRepository.findById(id).map(AppUtils::albumEntityToDto);
    }

    public Flux<AlbumDto> getAlbumsByUserId(long userId) {

        return albumRepository.findByUserId(userId);

    }

    public Flux<AlbumDto> getAlbumsByTitle(String title) {

        return albumRepository.findByTitleContains(title);

    }

    public Mono<AlbumDto> insertAlbum(Mono<AlbumDto> albumDtoMono){
        return albumDtoMono
                .map(a -> {
                    return AppUtils.updateAlbumCreDate(a, LocalDateTime.now());
                })
                .map(AppUtils::albumDtoToEntity)
                .flatMap(albumRepository::insert)
                .map(AppUtils::albumEntityToDto);
    }

    public Mono<AlbumDto> updateAlbum(Mono<AlbumDto> albumDtoMono, long id){
        return  albumRepository.findById(id)
                .flatMap(a->albumDtoMono.map(AppUtils::albumDtoToEntity)
                                        .doOnNext(e-> {e.setId(id); e.setModDate(LocalDateTime.now());}))
                .flatMap(albumRepository::save)
                .map(AppUtils::albumEntityToDto);
    }

    public Mono<Void> deleteAllAlbums(){
        return albumRepository.deleteAll();
    }

    public Mono<Void> deleteAlbum(long id){
        return albumRepository.deleteById(id);
    }

    public Flux<AlbumDto> importAllAlbums() {

        LocalDateTime currTime = LocalDateTime.now();

        Flux<AlbumDto> lstAlbums = AppUtils.importData(AlbumDto.class ,"https://jsonplaceholder.typicode.com/albums", webClientBuilder)
                .map(a -> {
                    a.setCreDate(currTime);
                    //a.setModDate(currTime);
                    return a;
                })
                .map(AppUtils::albumDtoToEntity)
                .flatMap(albumRepository::insert)
                .map(AppUtils::albumEntityToDto);

        return lstAlbums;
    }

}

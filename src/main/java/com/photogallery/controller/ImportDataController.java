package com.photogallery.controller;

import com.photogallery.dto.AlbumDto;
import com.photogallery.dto.PhotoDto;
import com.photogallery.dto.UserDto;
import com.photogallery.service.AlbumService;
import com.photogallery.service.PhotoService;
import com.photogallery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value="/data", produces = "application/json", method= {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class ImportDataController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserService userService;

    @DeleteMapping("/cleanGallery")
    public Mono<Void> cleanGallery(){

        return Mono.defer(() -> photoService.deleteAllPhotos())
                .then(albumService.deleteAllAlbums())
                .then(userService.deleteAllUsers());

    }

    @PostMapping("/importGallery")
    public Flux importGallery(){

        Flux<UserDto> lstUsers = userService.importAllUsers().subscribeOn(Schedulers.elastic());
        Flux<PhotoDto> lstPhotos = photoService.importAllPhotos().subscribeOn(Schedulers.elastic());
        Flux<AlbumDto> lstAlbums = albumService.importAllAlbums().subscribeOn(Schedulers.elastic());

        return Flux.merge(lstUsers,lstAlbums,lstPhotos);
    }

}

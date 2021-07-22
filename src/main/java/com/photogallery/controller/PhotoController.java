package com.photogallery.controller;

import com.photogallery.dto.PhotoDto;
import com.photogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/photos", produces = "application/json", method= {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/user")
    public String helloUser(){
        return photoService.sayHello("USER");
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return photoService.sayHello("ADMIN");
    }

    @GetMapping
    public Flux<PhotoDto> getPhotos(){
        return photoService.getPhotos();
    }

    @GetMapping("/{id}")
    public Mono<PhotoDto> getPhoto(@PathVariable long id){
        return photoService.getPhoto(id);
    }

    @PostMapping("/admin/insertphoto")
    public Mono<PhotoDto> savePhoto(@RequestBody Mono<PhotoDto> photoDtoMono) {
        return photoService.insertPhoto(photoDtoMono);
    }

    @PutMapping("/admin/update/{id}")
    public Mono<PhotoDto> updatePhoto(@RequestBody Mono<PhotoDto> photoDtoMono, @PathVariable long id) {
        return photoService.updatePhoto(photoDtoMono, id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public Mono<Void> deletePhoto(@PathVariable long id){
        return photoService.deletePhoto(id);
    }

    @DeleteMapping("/admin/delete")
    public Mono<Void> deleteAllPhotos(){
        return photoService.deleteAllPhotos();
    }

    @PostMapping("/admin/importPhotos")
    public Flux<PhotoDto> importAllPhotos(){
        return photoService.importAllPhotos();
    }

    @GetMapping("/getalbumphotos/{id}")
    public Flux<PhotoDto> getPhotosByAlbumId(@PathVariable long id) {
        return photoService.getPhotosByAlbumId(id,null);
    }


}

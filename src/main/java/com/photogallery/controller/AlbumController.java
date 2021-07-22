package com.photogallery.controller;

import com.photogallery.dto.AlbumDto;
import com.photogallery.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/albums", produces = "application/json", method= {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public Flux<AlbumDto> getAlbums(){
        return albumService.getAlbums();
    }

    @GetMapping("/{id}")
    public Mono<AlbumDto> getAlbum(@PathVariable long id){
        return albumService.getAlbum(id);
    }

    @PostMapping("/admin/insertalbum")
    public Mono<AlbumDto> saveAlbum(@RequestBody Mono<AlbumDto> albumDtoMono) {
        return albumService.insertAlbum(albumDtoMono);
    }

    @PutMapping("/admin/update/{id}")
    public Mono<AlbumDto> updateAlbum(@RequestBody Mono<AlbumDto> albumDtoMono, @PathVariable long id) {
        return albumService.updateAlbum(albumDtoMono, id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public Mono<Void> deleteAlbum(@PathVariable long id){
        return albumService.deleteAlbum(id);
    }

    @DeleteMapping("/admin/delete")
    public Mono<Void> deleteAllAlbums(){
        return albumService.deleteAllAlbums();
    }

    @PostMapping("/admin/importAlbums")
    public Flux<AlbumDto> importAllAlbums(){
        return albumService.importAllAlbums();
    }

    @GetMapping("/getalbumsbyuserid/{userId}")
    public Flux<AlbumDto> getAlbumsByUserId(@PathVariable long userId) {
        return albumService.getAlbumsByUserId(userId);
    }

    @GetMapping("/getalbumsbyTitle/{title}")
    public Flux<AlbumDto> getAlbumsByTitle(@PathVariable String title) {
        return albumService.getAlbumsByTitle(title);
    }

}

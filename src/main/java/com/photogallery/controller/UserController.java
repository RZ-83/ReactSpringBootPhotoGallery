package com.photogallery.controller;

import com.photogallery.dto.AlbumDto;
import com.photogallery.dto.PhotoDto;
import com.photogallery.dto.UserDto;
import com.photogallery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value="/users", produces = "application/json", method= {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getPhoto(@PathVariable long id){
        return userService.getUser(id);
    }

    @PostMapping("/admin/insert")
    public Mono<UserDto> savePhoto(@RequestBody Mono<UserDto> userDtoMono) {
        return userService.insertUser(userDtoMono);
    }

    @PutMapping("/admin/update/{id}")
    public Mono<UserDto> updatePhoto(@RequestBody Mono<UserDto> userDtoMono, @PathVariable long id) {
        return userService.updateUser(userDtoMono, id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public Mono<Void> deletePhoto(@PathVariable long id){
        return userService.deleteUser(id);
    }

    @DeleteMapping("/admin/delete")
    public Mono<Void> deleteAllUsers(){
        return userService.deleteAllUsers();
    }

    @PostMapping("/admin/importUsers")
    public Flux<UserDto> importAllUsers(){
        return userService.importAllUsers();
    }

    @GetMapping("/getalbumsbyuserid/{userId}")
    public Flux<PhotoDto> getUserGallery(@PathVariable long userId){
        return userService.getUserGallery(userId);
    }

}

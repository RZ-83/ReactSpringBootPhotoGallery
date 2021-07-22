package com.photogallery.service;

import com.photogallery.dto.AlbumDto;
import com.photogallery.dto.PhotoDto;
import com.photogallery.dto.UserDto;
import com.photogallery.repository.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Environment env;

    public Flux<UserDto> getUsers() {
        return userRepository.findAll().map(AppUtils::userEntityToDto)
                .sort(Comparator.comparing(UserDto::getId));
    }

    public Mono<UserDto> getUser(long id) {
        return userRepository.findById(id).map(AppUtils::userEntityToDto);
    }

    public Mono<UserDto> insertUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(user -> {
                    return AppUtils.updateUserCreDate(user, LocalDateTime.now());
                })
                .map(AppUtils::userDtoToEntity)
                .flatMap(userRepository::insert)
                .map(AppUtils::userEntityToDto);
    }

    public Mono<UserDto> updateUser(Mono<UserDto> userDtoMono, long id) {
        return userRepository.findById(id)
                .flatMap(a -> userDtoMono.map(AppUtils::userDtoToEntity)
                                        .doOnNext(e -> {e.setId(id); e.setModDate(LocalDateTime.now());}))
                .flatMap(userRepository::save)
                .map(AppUtils::userEntityToDto);
    }

    public Mono<Void> deleteAllUsers(){
        return userRepository.deleteAll();
    }

    public Mono<Void> deleteUser(long id) {
        return userRepository.deleteById(id);
    }

    public Flux<UserDto> importAllUsers() {

        Flux<UserDto> lstUsers = AppUtils.importData(UserDto.class ,"https://jsonplaceholder.typicode.com/users", webClientBuilder)
                .map(p -> {return AppUtils.updateUserCreDate(p,LocalDateTime.now());})
                .map(AppUtils::userDtoToEntity)
                .flatMap(userRepository::insert)
                .map(AppUtils::userEntityToDto);;

        return lstUsers;
    }

    public Flux<PhotoDto> getUserGallery(long userId) {

        Flux<PhotoDto> lstAlbums = albumService.getAlbumsByUserId(userId)
                .flatMap(albumDto -> {
                    return photoService.getPhotosByAlbumId(albumDto.getId(), albumDto);
                })
                .sort(Comparator.comparing(PhotoDto::getModDate, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing((photoDto -> { return photoDto.getAlbumDto().getModDate();} ), Comparator.nullsLast(Comparator.reverseOrder())))
                ;

        return lstAlbums;
    }

}

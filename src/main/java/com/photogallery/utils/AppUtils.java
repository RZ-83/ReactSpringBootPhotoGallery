package com.photogallery.utils;

import com.photogallery.dto.*;
import com.photogallery.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public class AppUtils {

    public static AlbumDto albumEntityToDto(Album album){
        AlbumDto albumDto = new AlbumDto();
        BeanUtils.copyProperties(album,albumDto);
        return albumDto;
    }

    public static Album albumDtoToEntity(AlbumDto albumDto){
        Album album = new Album();
        BeanUtils.copyProperties(albumDto,album);
        return album;
    }

    public static PhotoDto photoEntityToDto(Photo photo){
        PhotoDto photoDto = new PhotoDto();
        BeanUtils.copyProperties(photo,photoDto);
        AlbumDto albumDto = new AlbumDto();
        albumDto.setId(photo.getAlbumId());
        return photoDto;
    }

    public static Photo photoDtoToEntity(PhotoDto photoDto){
        Photo photo = new Photo();
        BeanUtils.copyProperties(photoDto,photo);
        return photo;
    }

    public static UserDto userEntityToDto(User user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);

        if(user!=null){
            Address address = user.getAddress();
            Company company = user.getCompany();

            if(address!=null){

                AddressDto addressDto = new AddressDto();
                BeanUtils.copyProperties(address,addressDto);

                Geo geo = address.getGeo();

                if(geo!=null){
                    GeoDto geoDto = new GeoDto();
                    BeanUtils.copyProperties(geo,geoDto);
                    addressDto.setGeo(geoDto);
                }

                userDto.setAddress(addressDto);
            }

            if(company!=null)
            {
                CompanyDto companyDto = new CompanyDto();
                BeanUtils.copyProperties(company,companyDto);
                userDto.setCompany(companyDto);
            }
        }

        return userDto;
    }

    public static User userDtoToEntity(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto,user);

        if(userDto!=null){
            AddressDto addressDto = userDto.getAddress();
            CompanyDto companyDto = userDto.getCompany();

            if(addressDto!=null){

                Address address = new Address();
                BeanUtils.copyProperties(addressDto,address);

                GeoDto geoDto= addressDto.getGeo();
                if(geoDto!=null){
                    Geo geo = new Geo();
                    BeanUtils.copyProperties(geoDto,geo);
                    address.setGeo(geo);
                }

                user.setAddress(address);
            }

            if(companyDto!=null)
            {
                Company company = new Company();
                BeanUtils.copyProperties(companyDto,company);
                user.setCompany(company);
            }

        }

        System.out.println("<<<<<<<userdto>>>>>> " + userDto);
        return user;
    }

    public static AlbumDto updateTime(AlbumDto albumDto, LocalDateTime paramDateTime){
        albumDto.setCreDate(paramDateTime);
        albumDto.setModDate(paramDateTime);
        return albumDto;
    }

    public static AlbumDto updateAlbumCreDate(AlbumDto albumDto, LocalDateTime paramDateTime){
        albumDto.setCreDate(paramDateTime);
        return albumDto;
    }

    public static AlbumDto updateAlbumModDate(AlbumDto albumDto, LocalDateTime paramDateTime){
        albumDto.setModDate(paramDateTime);
        return albumDto;
    }

    public static PhotoDto updatePhotoCreDate(PhotoDto photoDto, LocalDateTime paramDateTime){
        photoDto.setCreDate(paramDateTime);

        return photoDto;
    }

    public static PhotoDto updatePhotoModDate(PhotoDto photoDto, LocalDateTime paramDateTime){
        photoDto.setModDate(paramDateTime);
        return photoDto;
    }

    public static UserDto updateUserCreDate(UserDto userDto, LocalDateTime paramDateTime){
        userDto.setCreDate(paramDateTime);
        return userDto;
    }

    public static UserDto updateUserModDate(UserDto userDto, LocalDateTime paramDateTime){
        userDto.setModDate(paramDateTime);
        return userDto;
    }

    public static <T> Flux<T> importData(Class<T> dto, String uri, WebClient.Builder webClientBuilder) {
        Flux<T> lstDtos = webClientBuilder.build()
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(dto)
                .log();
        return lstDtos;
    }

}

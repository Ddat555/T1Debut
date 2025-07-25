package com.example.authService.services;

import com.example.authService.models.UserEntity;
import com.example.authService.models.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class ConverterEntityToDTO {

    public UserDTO userConverter(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUserRole(userEntity.getUserRole());
        return userDTO;
    }
}

package com.example.authService.services;

import com.example.authService.enums.UserRole;
import com.example.authService.exceptions.InvalidAuthentificationPrincipalException;
import com.example.authService.exceptions.PasswordNotMatchesException;
import com.example.authService.exceptions.UserNotFoundException;
import com.example.authService.models.AuthToken;
import com.example.authService.models.UserEntity;
import com.example.authService.models.dto.UserCreateDTO;
import com.example.authService.models.dto.UserDTO;
import com.example.authService.models.dto.UserLoginDTO;
import com.example.authService.repositories.AuthTokenRepository;
import com.example.authService.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public UUID loginUser(UserLoginDTO userLoginDTO){
        var userOpt = userRepository.findByLogin(userLoginDTO.getLogin());
        if(userOpt.isEmpty())
            throw new UserNotFoundException("User with this login not found");
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), userOpt.get().getPassword()))
            throw new PasswordNotMatchesException("Password not matches");
        var authTokenOpt = authTokenRepository.findByUserEntity(userOpt.get());
        authTokenOpt.ifPresent(authToken -> {
            authTokenRepository.deleteById(authToken.getToken());
            entityManager.flush();
        });
        AuthToken authToken = new AuthToken();
        authToken.setCreatedAt(Instant.now());
        authToken.setExpiresAt(Instant.now().plus(15, ChronoUnit.SECONDS));
        authToken.setUserEntity(userOpt.get());
        authToken = authTokenRepository.save(authToken);
        return authToken.getToken();
    }

    public List<UserDTO> getAllUser(Pageable pageable){
        var userList = userRepository.findAll(pageable);
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user -> userDTOList.add(converterEntityToDTO.userConverter(user)));
        return userDTOList;
    }

    public UserDTO getMy(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var object = authentication.getPrincipal();
        if(object instanceof UserEntity){
            var user = userRepository.findByLogin(((UserEntity) object).getLogin());
            if(user.isEmpty())
                throw new NullPointerException();
            return converterEntityToDTO.userConverter(user.get());
        }
        else
            throw new InvalidAuthentificationPrincipalException("Invalid authentification principal");

    }

    public UserDTO registryUser(UserCreateDTO userCreateDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userCreateDTO.getEmail());
        userEntity.setUserRole(UserRole.GUEST);
        userEntity.setLogin(userCreateDTO.getLogin());
        userEntity.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        userEntity = userRepository.save(userEntity);
        return converterEntityToDTO.userConverter(userEntity);
    }

    public UserDTO updateUser(UserCreateDTO userDTO){
        if(userDTO.getId() == null)
            throw new IllegalArgumentException();
        var user = userRepository.findById(userDTO.getId()).orElseThrow(NullPointerException::new);
        user.setUserRole(userDTO.getUserRole());
        if(userDTO.getLogin() != null)
            user.setLogin(userDTO.getLogin());
        if(userDTO.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setEmail(userDTO.getEmail());
        user = userRepository.save(user);
        return converterEntityToDTO.userConverter(user);
    }

    @Transactional
    public void logout(){
        var user = getMy();
        authTokenRepository.deleteFromUserId(user.getId());
    }
}

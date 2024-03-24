package net.dzakirin.templatebe.service;

import net.dzakirin.templatebe.dto.UserDto;
import net.dzakirin.templatebe.exception.ResourceNotFoundException;
import net.dzakirin.templatebe.mapper.UserMapper;
import net.dzakirin.templatebe.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final String USER_NOT_FOUND = "User id not found : ";

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public UserDto createUser(UserDto userDto) {
        var userEntity = userMapper.toUserEntity(userDto);
        userRepo.save(userEntity);

        return userMapper.toUserDto(userEntity);
    }

    public UserDto findById(UUID userId) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        return userMapper.toUserDto(userEntity);
    }

    public List<UserDto> findAll() {
        var userEntities = userRepo.findAll();

        return userEntities.stream()
                .map(userMapper::toUserDto) // Use the manual mapper here
                .toList();
    }

    public List<UserDto> findByAddressPostCode(String postcode) {
        var users = userRepo.findByAddressPostCode(postcode); // You need to implement this method in your repository
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }



    public UserDto updateUser(UUID userId, UserDto userDto) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userRepo.save(userEntity);

        return userMapper.toUserDto(userEntity);
    }

    public void deleteUser(UUID userId) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        userRepo.delete(userEntity);
    }
}

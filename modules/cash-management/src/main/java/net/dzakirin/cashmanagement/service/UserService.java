package net.dzakirin.cashmanagement.service;

import net.dzakirin.cashmanagement.dto.response.UserDto;
import net.dzakirin.cashmanagement.exception.ResourceNotFoundException;
import net.dzakirin.cashmanagement.mapper.UserMapper;
import net.dzakirin.cashmanagement.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static net.dzakirin.cashmanagement.constant.ErrorCodes.USER_NOT_FOUND;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto createUser(UserDto userDto) {
        var userEntity = UserMapper.toUserEntity(userDto);
        userRepo.save(userEntity);

        return UserMapper.toUserDto(userEntity);
    }

    public UserDto findById(UUID userId) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage(String.valueOf(userId))));

        return UserMapper.toUserDto(userEntity);
    }

    public List<UserDto> findAll() {
        var userEntities = userRepo.findAll();

        return userEntities.stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public List<UserDto> findByAddressPostCode(String postcode) {
        var users = userRepo.findByAddressPostCode(postcode);
        return users.stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto updateUser(UUID userId, UserDto userDto) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage(String.valueOf(userId))));

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userRepo.save(userEntity);

        return UserMapper.toUserDto(userEntity);
    }

    public void deleteUser(UUID userId) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage(String.valueOf(userId))));

        userRepo.delete(userEntity);
    }
}

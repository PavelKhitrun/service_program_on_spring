package by.pavelkht.service_program_on_spring.service;

import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.database.repository.*;
import by.pavelkht.service_program_on_spring.dto.*;
import by.pavelkht.service_program_on_spring.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CreateUserMapper createUserMapper;
    private final ImageService imageService;

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(user -> userMapper.mapFrom(user));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapFrom).collect(Collectors.toList());
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        log.info("user insert to database...");
        return userMapper
                .mapFrom(userRepository.saveAndFlush(createUserMapper.mapFrom(createUserDto)));
    }

    public Page<UserDto> findAll(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(userRepository.byFilters(filter), pageable).map(userMapper::mapFrom);
    }

    public Optional<UserDto> update(Long id, EditUserDto editUserDto) {
        if (!editUserDto.getImage().isEmpty()) {
            uploadImage(editUserDto.getImage());
        }

        return userRepository
                .findById(id)
                .map(user -> {
                    user.setName(editUserDto.getOrganization());
                    user.setPhone(editUserDto.getPhone());
                    user.setEmail(editUserDto.getEmail());
                    user.setPassword(editUserDto.getPassword());
                    user.setImage(editUserDto.getImage().getOriginalFilename());
                    userRepository.saveAndFlush(user);
                    log.info("user updated");
                    return userMapper.mapFrom(user);
                });
    }

    public boolean deleteUser(Long id) {
        return userRepository
                .findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    public Optional<byte[]> findAvatar(Long userId) {
        return userRepository.findById(userId)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByLogin(username).map(user -> userMapper.mapFrom(user));
    }
}

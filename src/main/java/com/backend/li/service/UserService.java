package com.backend.li.service;

import com.backend.li.model.UserEntity;
import com.backend.li.repository.UserRepository;
import com.backend.li.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return new UserDTO(user.getId(), user.getNume(),user.getPin(),user.getBalance());
        } else {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        Optional<UserEntity> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();
            existingUser.setNume(updatedUser.getNume());
            existingUser.setNr_card(updatedUser.getNr_card());
            existingUser.setPin(updatedUser.getPin());

            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }



    public void addNewUser(UserEntity user) {
        userRepository.save(user);
    }
}

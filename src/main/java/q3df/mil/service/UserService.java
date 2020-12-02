package q3df.mil.service;


import q3df.mil.dto.UserDto;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.entities.users_roles.User;

import java.util.List;


public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto saveUser(UserRegistrationDto userRegistrationDto);

    UserDto updateUser(User user);

    void deleteUser(User user);
}

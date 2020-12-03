package q3df.mil.service;


import q3df.mil.dto.UserDto;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.dto.UserForPreviewOfAllUsersDto;

import java.util.List;


public interface UserService {

    List<UserForPreviewOfAllUsersDto> findAll();

    UserDto findById(Long id);

    UserDto saveUser(UserRegistrationDto userRegistrationDto);

    UserDto updateUser(UserDto user);

    void deleteUser(Long id);
}

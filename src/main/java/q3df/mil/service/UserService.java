package q3df.mil.service;


import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserForPreviewOfAllUsersDto;

import java.util.List;


public interface UserService {

    List<UserForPreviewOfAllUsersDto> findAll();

    UserDto findById(Long id);

    UserDto saveUser(UserRegistrationDto userRegistrationDto);

    UserDto updateUser(UserDto user);

    void deleteUser(Long id);
}

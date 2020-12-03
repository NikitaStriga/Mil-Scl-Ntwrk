package q3df.mil.service.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.UserDto;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.dto.UserForPreviewOfAllUsersDto;
import q3df.mil.entities.users_roles.User;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.UserForPreviewOfAllUsersMapper;
import q3df.mil.mapper.UserMapper;
import q3df.mil.mapper.UserRegistrationMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRegistrationMapper userRegistrationMapper;
    private final UserForPreviewOfAllUsersMapper userForPreviewOfAllUsersMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserRegistrationMapper userRegistrationMapper, UserForPreviewOfAllUsersMapper userForPreviewOfAllUsersMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userForPreviewOfAllUsersMapper = userForPreviewOfAllUsersMapper;
    }



    @Override
    @Transactional
    public List<UserForPreviewOfAllUsersDto> findAll() {
        return userRepository.findAll().stream().map(userForPreviewOfAllUsersMapper::toDto).collect(Collectors.toList());
    }



    @Override
    @Transactional
    public UserDto findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return userMapper.toDto(
                byId.orElseThrow(
                        ()-> new UserNotFoundException("User with id " + id + " not found!")));

    }



    @Override
    @Transactional
    public UserDto saveUser(UserRegistrationDto userRegistrationDto) {
        List<User> usersByEmailAndLogin = userRepository.findUsersByEmailOrLogin(userRegistrationDto.getEmail(), userRegistrationDto.getLogin());
        if(!usersByEmailAndLogin.isEmpty()){
            for (User user : usersByEmailAndLogin) {
                if(user.getLogin().equalsIgnoreCase(userRegistrationDto.getLogin())) throw new LoginExistException("Login " + userRegistrationDto.getLogin() + " is already exists!");
                if(user.getEmail().equalsIgnoreCase(userRegistrationDto.getEmail())) throw new EmailExistException("Email " + userRegistrationDto.getEmail() + " is already exists!");
            }
        }

        return userMapper.toDto(userRepository.save(userRegistrationMapper.fromDto(userRegistrationDto)));
    }



    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.getOne(userDto.getId());
        BeanUtils.copyProperties(userDto, user);
        return userMapper.toDto(user);
    }



    @Override
    @Transactional
    public void deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("User with id " + id + " doesn't exist!");
        }



    }
}

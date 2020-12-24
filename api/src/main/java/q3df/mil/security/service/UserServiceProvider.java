package q3df.mil.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.entities.role.Role;
import q3df.mil.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceProvider implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * @param login user login
     * @return security User
     * @throws UsernameNotFoundException if user with input login not founded
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            Optional<q3df.mil.entities.user.User> searchResult = userRepository.findByLogin(login);
            if (searchResult.isPresent()) {
                q3df.mil.entities.user.User user = searchResult.get();
                return new org.springframework.security.core.userdetails.User(
                        user.getLogin(),
                        user.getPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                        AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles().stream().map(Role::getRole).map(SystemRoles::name).collect(Collectors.joining(",")))
                );
            } else {
                throw new UsernameNotFoundException("Cant found user with login " + login);
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("Cant found user with login " + login);
        }

    }
}
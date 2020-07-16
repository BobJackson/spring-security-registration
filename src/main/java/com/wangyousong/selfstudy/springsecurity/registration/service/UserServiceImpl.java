package com.wangyousong.selfstudy.springsecurity.registration.service;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.PasswordResetTokenRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.RoleRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.UserRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.VerificationTokenRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.PasswordResetToken;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.VerificationToken;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;
import com.wangyousong.selfstudy.springsecurity.registration.web.error.UserAlreadyExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 20:59
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordTokenRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository,
                           PasswordResetTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        user.setEmail(accountDto.getEmail());

        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String existingToken) {
        Optional<VerificationToken> token = tokenRepository.findByToken(existingToken);
        return token.map(VerificationToken::getUser).orElse(null);
    }

    @Override
    public VerificationToken updateVerificationTokenForUser(User user, String token) {
        Optional<VerificationToken> vToken = tokenRepository.findByToken(token);
        if (vToken.isEmpty()) {
            throw new IllegalArgumentException("Invalid token ->" + token);
        }
        vToken.get().updateToken(UUID.randomUUID().toString());
        return tokenRepository.save(vToken.get());
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElse(null);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}

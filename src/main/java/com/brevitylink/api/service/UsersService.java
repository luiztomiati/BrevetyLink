package com.brevitylink.api.service;

import com.brevitylink.api.dto.*;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final TokenService tokenService;




    public UsersService(UserRepository usuarioRepository, PasswordEncoder encoder, EmailService emailService, TokenService tokenService) {
        this.repository = usuarioRepository;
        this.encoder = encoder;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return repository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException("Username não encontrado"));
    }

    public UserResponse createUser(UserRequest user){

        Boolean email =  repository.existsByEmailIgnoreCase(user.email());
        Boolean username =  repository.existsByNickNameIgnoreCase(user.nickname());
        if(!email && !username) {
            Users newUser = new Users(user, encoder);
            repository.save(newUser);
            return new UserResponse(newUser.getName(), newUser.getNickName(), newUser.getEmail());
        }
        throw new RuntimeException("Email ou username já existe");
    }

    public void updateUser(UUID id, UserEdit userEdit) {
        Optional<Users> oldUser = repository.findById(id);
        if(repository.existsByEmailAndIdNot(userEdit.userEmail(), id)) {
            throw new RuntimeException("Email já está em uso");
        }
        if(oldUser.isPresent()) {
            Users user = oldUser.get();
            user.updateUsers(user,userEdit);
            repository.save(user);
        }
    }

    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    public void resetPassword(UUID id, ResetPassword user) {
        Users users = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(user.oldPassword(), users.getPassword())) {
           throw new RuntimeException("Password invalido");
       }
        users.setPassword(encoder.encode(user.oldPassword()));
        repository.save(users);
    }
    public void forgotPassword(ForgotPassword email) {
        var emailUser = email.email().trim().toLowerCase();
        var user = repository.findByEmailIgnoreCase(emailUser);

        user.ifPresent(users -> {
            var token = tokenService.generateToken(users);
            var body = emailService.buildForgotPasswordEmail(users,token);
            emailService.sendEmail(emailUser,"Solicitação de redefinição de senha",body);
        });
    }

    public void resetPasswordToken(String token, String password) {
        String userIdentifier = tokenService.checkToken(token);
        if (userIdentifier != null) {
            var user = repository.findByEmailIgnoreCase(userIdentifier)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            user.setPassword(encoder.encode(password));
            repository.save(user);
        } else {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}

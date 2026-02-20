package com.francisco.blog.service;

import com.francisco.blog.dto.response.DeleteUserResponse;
import com.francisco.blog.dto.response.ShowUserResponse;
import com.francisco.blog.entitys.EditUser;
import com.francisco.blog.entitys.ExcludeUser;
import com.francisco.blog.entitys.User;
import com.francisco.blog.entitys.UserRole;
import com.francisco.blog.exception.PermissionDeniedException;
import com.francisco.blog.exception.ResourceNotFoundException;
import com.francisco.blog.repository.EditUserRepository;
import com.francisco.blog.repository.ExcludeUserRepository;
import com.francisco.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.UUID;



@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EditUserRepository editUserRepository;
    private final ExcludeUserRepository excludeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<ShowUserResponse> showAll(Long id,Pageable pageable, boolean showDesactive){

       User userLogged = userRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("Usuário Logado não encontrado")
       );
        Page<User> users = userLogged.getUserRole().contains(UserRole.ROLE_ADMIN) && showDesactive ? userRepository.findAll(pageable) :
                userRepository.findAllByIsActiveTrue(pageable);

        return users.map(user -> new ShowUserResponse(
               user.getUsername(),
               user.getPhotoPerfil(),
                user.getIsActive()

       ));
    }

    public User showUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado")
        );
        return user;
    }



    public User saveUser(User user){
        user.setIsActive(true);
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User editUserById(Long editorId, Long id, User user){
        User userEntity = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não Foi Possivel Enontrar o Usuario")
        );
        User userEditor = userRepository.findById(editorId).orElseThrow(
                () -> new ResourceNotFoundException("Não Foi Possivel Encontrar o Usuario a editar")
        );
        EditUser editUser = new EditUser();
        editUser.setEditedUser(userEntity);
        editUser.setEditorUser(userEditor);
        boolean altered = false;

        if (editorId.equals(id) || userEditor.getUserRole().contains(UserRole.ROLE_ADMIN)){


        if (user.getUsername() != null && !user.getUsername().equals(userEntity.getUsername())){
            editUser.setOldUsername(userEntity.getUsername());
            userEntity.setUsername(user.getUsername());
            altered = true;
        }
        if (user.getEmail() != null && !user.getEmail().equals(userEntity.getEmail())){
            editUser.setOldEmail(userEntity.getEmail());
            userEntity.setEmail(user.getEmail());
            altered = true;
        }
        if (user.getPassword() != null && !user.getPassword().equals(userEntity.getPassword())){
            throw new PermissionDeniedException("Não é Permitido Atualizar Senhas Por essa Rota");
        }
        if (user.getUserRole() != null && !user.getUserRole().equals(userEntity.getUserRole())){
            if (userEditor.getUserRole().contains(UserRole.ROLE_ADMIN)) {

                editUser.setOldRole(new LinkedHashSet<>(userEntity.getUserRole()));
                userEntity.setUserRole(user.getUserRole());
                altered = true;
            }else {
                throw new PermissionDeniedException("Somente Administradores podem alterar Permissão");
            }
        }
        if (user.getPhotoPerfil() != null && !user.getPhotoPerfil().equals(userEntity.getPhotoPerfil())){
            editUser.setOldPhotoPerfil(userEntity.getPhotoPerfil());
            userEntity.setPhotoPerfil(user.getPhotoPerfil());
            altered = true;
        }
        if (user.getAbout() != null && !user.getAbout().equals(userEntity.getAbout())){
            editUser.setOldAbout(userEntity.getAbout());
            userEntity.setAbout(user.getAbout());
            altered = true;
        }
        }else {
            throw new PermissionDeniedException("Não é permitido Alterar Usuario Alheio");
        }
        if (altered == true){
            editUserRepository.saveAndFlush(editUser);
            userRepository.saveAndFlush(userEntity);
        }
        return userEntity;

    }

    @Transactional
    public User EditPasswordById(Long editorId, Long editedId, String password){
            User userEntity = userRepository.findById(editedId).orElseThrow(
                    () -> new ResourceNotFoundException("Não Foi Possivel Enontrar o Usuario")
            );
            User userEditor = editorId.equals(editedId) ? userEntity :
                    userRepository.findById(editorId).orElseThrow(
                            () -> new ResourceNotFoundException("Não Foi Possivel Enontrar o Usuario editor")
                    );
            if (editorId.equals(editedId) || userEditor.getUserRole().contains(UserRole.ROLE_ADMIN)){
                userEntity.setPassword(passwordEncoder.encode(password));

            }else {
                throw new PermissionDeniedException("Você não tem permissão para mudar essa senha");
            }
            return userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    public DeleteUserResponse SoftDeleteUserById(Long excludorId , Long excludedId, String reason, Integer time){
        User excludedUser = userRepository.findById(excludedId).orElseThrow(
                () -> new RuntimeException("Usuario a excluir não Encontrado")
        );
        if (excludedUser.getUserRole().contains(UserRole.ROLE_ADMIN)){
            throw new PermissionDeniedException("Ação proibida: Não é possível SftDelete em um administrador.");
        }

        int finalDays = (time != null) ? time : 30;
        ZonedDateTime expirationTime =  ZonedDateTime.now().plusDays(finalDays);

        ExcludeUser excludeUser = new ExcludeUser();
        excludeUser.setExcludedUser(excludedUser);
        excludeUser.setExcludeReason(reason);
        excludeUser.setExcludeForTime(expirationTime);
        if (excludorId.equals(excludedId)){
            excludeUser.setExcluderUser(excludedUser);

        }else {
            User excludorUser = userRepository.findById(excludorId).orElseThrow(
                    () -> new ResourceNotFoundException("Usuario excluidor não Encontrado")
            );
            excludeUser.setExcluderUser(excludorUser);


        }
        excludedUser.setIsActive(false);
        userRepository.saveAndFlush(excludedUser);
        ExcludeUser excludedLog = excludeUserRepository.saveAndFlush(excludeUser);
        String timeResponse = String.valueOf(finalDays);
        return new DeleteUserResponse(excludedUser.getUsername(), excludedUser.getEmail(), excludedUser.getIsActive(), excludedLog.getExcludeReason(), timeResponse);
    }

    @Transactional
    public void DeletePermUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado")
        );
        if (user.getUserRole().contains(UserRole.ROLE_ADMIN)){
            throw new PermissionDeniedException("Ação proibida: Não é possível excluir permanentemente um administrador.");
        }
        user.setUsername("UserDeleted");
        user.setEmail("User_"+ user.getId()+"@deleted.com");
        user.setPassword("Deleted_"+UUID.randomUUID());
        user.setPhotoPerfil(null);
        user.setAbout(null);
        user.setIsActive(false);
        user.getUserRole().clear();

        userRepository.saveAndFlush(user);
    }


}



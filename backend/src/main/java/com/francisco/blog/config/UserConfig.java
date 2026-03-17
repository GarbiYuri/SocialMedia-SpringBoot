package com.francisco.blog.config;


import com.francisco.blog.entitys.ExcludeUser;
import com.francisco.blog.entitys.User;
import com.francisco.blog.exception.PermissionDeniedException;
import com.francisco.blog.exception.ResourceNotFoundException;
import com.francisco.blog.repository.ExcludeUserRepository;
import com.francisco.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserConfig implements UserDetailsService {

    private final ExcludeUserRepository excludeUserRepository;
    private final UserRepository userRepository;

    public UserConfig(ExcludeUserRepository excludeUserRepository, UserRepository userRepository) {
        this.excludeUserRepository = excludeUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findUserByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Login Incorreto. Email e/ou Senha Inválidos")
        );
        if (!user.isEnabled()){
            ExcludeUser log = excludeUserRepository
                    .findTopByExcludedUser_IdOrderByIdDesc(user.getId())
                    .orElseThrow(() -> new PermissionDeniedException("Conta desativada sem registro de exclusão. Contate o suporte."));
                        if (log.getExcluderUser().getId().equals(user.getId())){
                            if ( log.getExcludeForTime().isAfter(ZonedDateTime.now())){
                                user.setIsActive(true);
                                userRepository.saveAndFlush(user);
                            }else {
                                throw new PermissionDeniedException( "Não é Mais Possível acessar esta conta. Tempo Expirado em" + log.getExcludeForTime().getDayOfMonth() + "/" +
                                        log.getExcludeForTime().getMonthValue() + "/" + log.getExcludeForTime().getYear() );
                            }
                        }else {
                            if ( log.getExcludeForTime().isAfter(ZonedDateTime.now())) {
                                String mensagem = String.format("Conta Suspensa Por um Administrador até: %02d/%02d/%d. Razão: %s",
                                        log.getExcludeForTime().getDayOfMonth(),
                                        log.getExcludeForTime().getMonthValue(),
                                        log.getExcludeForTime().getYear(),
                                        log.getExcludeReason());
                                throw new PermissionDeniedException(mensagem);
                            }else {
                                user.setIsActive(true);
                                userRepository.saveAndFlush(user);
                            }
                        }

                    }
            return user;
        }

    }


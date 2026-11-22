package com.jpdev.agendadortarefas.infrastructure.security;


import com.jpdev.agendadortarefas.business.dtos.UsuarioDTO;
import com.jpdev.agendadortarefas.infrastructure.cliente.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    // Repositório para acessar dados de usuário no banco de dados
    @Autowired
    private UsuarioClient usuarioClient;

    public UserDetails carregaDadosUsuario(String email , String token){
        UsuarioDTO usuarioDTO = usuarioClient.buscaUsuarioPorEmail(email, token);
        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build();
    }
}

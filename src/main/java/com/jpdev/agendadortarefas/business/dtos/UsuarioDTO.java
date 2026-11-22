package com.jpdev.agendadortarefas.business.dtos;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    private String email;
    private String senha;
}

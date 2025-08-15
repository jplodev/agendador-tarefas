package com.jpdev.agendadortarefa.infrastructure.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpdev.agendadortarefa.infrastructure.enums.StatusNotificacaoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document("tarefa")
public class TarefasEntity {

    @Id
    private String id;
    private String nomeTarefa;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private StatusNotificacaoEnum statusNotificacao;


}

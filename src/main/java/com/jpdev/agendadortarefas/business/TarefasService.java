package com.jpdev.agendadortarefas.business;

import com.jpdev.agendadortarefas.business.dto.TarefasDTO;
import com.jpdev.agendadortarefas.business.mapper.TarefasConverter;
import com.jpdev.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.jpdev.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.jpdev.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.jpdev.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dto);
        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }
}

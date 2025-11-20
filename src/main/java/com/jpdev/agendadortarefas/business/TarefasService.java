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
    private final TarefasConverter converter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token,TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNOtificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefasEntity = converter.paraTerefaEntity(dto);
        return converter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }
}

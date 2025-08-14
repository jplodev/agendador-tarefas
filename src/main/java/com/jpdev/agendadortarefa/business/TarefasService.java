package com.jpdev.agendadortarefa.business;

import com.jpdev.agendadortarefa.business.dto.TarefasDTO;
import com.jpdev.agendadortarefa.business.mapper.TarefaConverter;
import com.jpdev.agendadortarefa.infrastructure.entity.TarefasEntity;
import com.jpdev.agendadortarefa.infrastructure.enums.StatusNotificacaoEnum;
import com.jpdev.agendadortarefa.infrastructure.repository.TarefasRepository;
import com.jpdev.agendadortarefa.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacao(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity tarefasEntity = tarefaConverter.paraTarefasEntity(dto);
        return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefasEntity));
    }
}

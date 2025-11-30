package com.jpdev.agendadortarefas.business;

import com.jpdev.agendadortarefas.business.dto.TarefasDTO;
import com.jpdev.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.jpdev.agendadortarefas.business.mapper.TarefasConverter;
import com.jpdev.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.jpdev.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.jpdev.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.jpdev.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.jpdev.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter converter;
    private final TarefaUpdateConverter tarefaUpdateConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token,TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefasEntity = converter.paraTerefaEntity(dto);
        return converter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return converter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(
                dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTO> buscaTarefaPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        return converter.paraListaTarefasDTO(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id){
        try{
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ai delatar, id não encontrado " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("tarefa não encontrada " + id) );
            entity.setStatusNotificacaoEnum(status);
            return converter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da notificação " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("tarefa não encontrada " + id) );
            tarefaUpdateConverter.updatetarefas(dto, entity);
            return converter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

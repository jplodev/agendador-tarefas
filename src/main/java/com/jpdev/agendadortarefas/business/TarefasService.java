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
    private final TarefasConverter tarefasConverter;
    public final TarefaUpdateConverter tarefaUpdateConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefa(dto);
        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefaPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefaraPorId(String id){
       try{
           tarefasRepository.deleteById(id);
       } catch (ResourceNotFoundException e) {
           throw new ResourceNotFoundException("Erro ao deletar a tarefa por id, id não encontrado " + id , e.getCause());
       }
    }

    public TarefasDTO alteraStatus (StatusNotificacaoEnum status , String id){
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não enontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao aletar estatus da tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
       try{
           TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                   () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
           tarefaUpdateConverter.updateTarefas(dto, entity);
           return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
       } catch (ResourceNotFoundException e) {
           throw new ResourceNotFoundException("Tarefa não encontrada ", e.getCause());
       }

    }
}

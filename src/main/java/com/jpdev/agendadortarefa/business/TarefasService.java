package com.jpdev.agendadortarefa.business;

import com.jpdev.agendadortarefa.business.dto.TarefasDTO;
import com.jpdev.agendadortarefa.business.mapper.TarefaConverter;
import com.jpdev.agendadortarefa.business.mapper.TarefaUpdateConverter;
import com.jpdev.agendadortarefa.infrastructure.entity.TarefasEntity;
import com.jpdev.agendadortarefa.infrastructure.enums.StatusNotificacaoEnum;
import com.jpdev.agendadortarefa.infrastructure.exceptions.ResourceNotFoundException;
import com.jpdev.agendadortarefa.infrastructure.repository.TarefasRepository;
import com.jpdev.agendadortarefa.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final TarefaUpdateConverter tarefaUpdateConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacao(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity tarefasEntity = tarefaConverter.paraTarefasEntity(dto);
        return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return tarefaConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefasDTO(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaId(String id){
        try{
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar a tarefa,  Id não encontrado " + id , e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        try{
            TarefasEntity tarefa = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefa.setStatusNotificacao(status);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefa));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status notificação ", e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try{
            TarefasEntity tarefa = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTrafeas(dto, tarefa);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefa));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar tarefa ", e.getCause());
        }
    }
}

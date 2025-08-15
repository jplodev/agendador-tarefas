package com.jpdev.agendadortarefa.controller;

import com.jpdev.agendadortarefa.business.TarefasService;
import com.jpdev.agendadortarefa.business.dto.TarefasDTO;
import com.jpdev.agendadortarefa.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravaTarefa(@RequestBody TarefasDTO dto,
                                                  @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("eventos")
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){
                return  ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTerefasPorEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){
        tarefasService.deletaTarefaId(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping()
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(@RequestParam("status")StatusNotificacaoEnum status,
                                                              @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> atualizaTarefas(@RequestBody TarefasDTO dto, @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.updateTarefas(dto, id));
    }


}

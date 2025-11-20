package com.jpdev.agendadortarefas.controller;

import com.jpdev.agendadortarefas.business.TarefasService;
import com.jpdev.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService service;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravaTarefas(@RequestBody TarefasDTO dto,
                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.CREATED).body((service.gravarTarefa(token, dto)));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscasListasTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal){
    return ResponseEntity.ok(service.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(service.buscaTarefaPorEmail(token));
    }
}

package com.jpdev.agendadortarefas.controller;

import com.jpdev.agendadortarefas.business.TarefasService;
import com.jpdev.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

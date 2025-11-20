package com.jpdev.agendadortarefas.business.mapper;

import com.jpdev.agendadortarefas.business.dto.TarefasDTO;
import com.jpdev.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTerefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);
}

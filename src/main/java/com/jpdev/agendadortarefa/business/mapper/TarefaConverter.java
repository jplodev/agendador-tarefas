package com.jpdev.agendadortarefa.business.mapper;

import com.jpdev.agendadortarefa.business.dto.TarefasDTO;
import com.jpdev.agendadortarefa.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO > dto);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity > entity);

}

package com.jpdev.agendadortarefa.business.mapper;

import com.jpdev.agendadortarefa.business.dto.TarefasDTO;
import com.jpdev.agendadortarefa.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateTrafeas(TarefasDTO dto, @MappingTarget TarefasEntity entity);
}

package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.entity.ItemCardapio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemCardapioMapper {

    ItemCardapioAtualizarDTO itemCardapioToItemCardapioDTO(ItemCardapio itemCardapio);
    ItemCardapio itemCardapioAtualizarDTOToItemCardapio(ItemCardapioAtualizarDTO itemCardapioAtualizarDTO);
    ItemCardapio itemCardapioCriarDTOToItemCardapio(ItemCardapioCriarDTO itemCardapioCriarDTO);

}

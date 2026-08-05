package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.entity.ItemCardapio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemCardapioMapper {

    ItemCardapioAtualizarDTO itemCardapioToItemCardapioAtualizarDTO(ItemCardapio itemCardapio);
    ItemCardapioBuscarDTO itemCardapioToItemCardapioBuscarDTO(ItemCardapio itemCardapio);
    ItemCardapioCriarDTO itemCardapioToItemCardapioCriarDTO(ItemCardapio itemCardapio);

    ItemCardapio itemCardapioAtualizarDTOToItemCardapio(ItemCardapioAtualizarDTO itemCardapioAtualizarDTO);
    ItemCardapio itemCardapioCriarDTOToItemCardapio(ItemCardapioCriarDTO itemCardapioCriarDTO);
    ItemCardapio itemCardapiBuscarDTOToItemCardapio(ItemCardapio itemCardapio);

}

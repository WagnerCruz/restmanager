package com.raidstack.restmanager.controllers;


import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioCriarDTO;
import com.raidstack.restmanager.services.TipoUsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tipousuario")
public class TipoUsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TipoUsuarioController.class);
    private TipoUsuarioService tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TipoUsuarioBuscarDTO>> pesquisarTodosTiposUsuario() {
        LOGGER.info("Retorna uma lista de todos os tipos de usuário cadastrados");
        List<TipoUsuarioBuscarDTO> tiposUsuarioBuscarDTO = tipoUsuarioService.buscarTodosTipoUsuario(10, 1);
        LOGGER.info("Foram encontrados {} tipos de usuário", tiposUsuarioBuscarDTO.size());
        return ResponseEntity.ok(tiposUsuarioBuscarDTO);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<TipoUsuarioBuscarDTO>> pesquisarTiposUsuarioPorNome(@RequestParam String nome) {
        LOGGER.info("Pesquisando tipos de usuário pelo nome: {}", nome);
        List<TipoUsuarioBuscarDTO> tiposUsuarioBuscarDTO = tipoUsuarioService.buscarTipoUsuarioPorNome(nome);
        LOGGER.info("Foram encontrados {} tipos de usuário com o nome informado", tiposUsuarioBuscarDTO.size());
        return ResponseEntity.ok(tiposUsuarioBuscarDTO);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarTipoUsuario(@Valid @RequestBody TipoUsuarioCriarDTO tipoUsuarioDTO) {
        LOGGER.info("Cadastrando novo tipo de usuário: {}", tipoUsuarioDTO.nome_tipo());
        tipoUsuarioService.cadastrarTipoUsuario(tipoUsuarioDTO);
        LOGGER.info("Tipo de usuário cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarTipoUsuario(@Valid @RequestBody TipoUsuarioAtualizarDTO tipoUsuarioAtualizarDTO) {
        LOGGER.info("Atualizando tipo de usuário: {}", tipoUsuarioAtualizarDTO.nome_tipo());
        tipoUsuarioService.atualizarTipoUsuario(tipoUsuarioAtualizarDTO);
        LOGGER.info("Tipo de usuário atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTipoUsuario(@Valid @RequestBody TipoUsuarioAtualizarDTO tipoUsuarioAtualizarDTO) {
        LOGGER.info("Deletando tipo de usuário: {}", tipoUsuarioAtualizarDTO.nome_tipo());
        tipoUsuarioService.deletarTipoUsuario(tipoUsuarioAtualizarDTO);
        LOGGER.info("Tipo de usuário deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

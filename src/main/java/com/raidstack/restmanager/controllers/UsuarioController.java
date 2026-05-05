package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.services.UsuarioService;
import com.raidstack.restmanager.vo.UsuarioVO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioVO>> getUsuarios() {
        LOGGER.info("Retorna uma lista dos usuários cadastrados na base");
        List<UsuarioVO> usuarios = usuarioService.findAll();
        LOGGER.info("Retorno com sucesso de {} usuarios", usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> getValidaUsuario(@RequestBody UsuarioSenhaDTO usuarioDTO) {
        LOGGER.info("Validando login de usuario {}", usuarioDTO.login());
        usuarioService.validarLoginUsuario(usuarioDTO);
        LOGGER.info("Login validado com sucesso {}", usuarioDTO.login());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/nome")
    public ResponseEntity<List<UsuarioVO>> getUsuarioByNome(@RequestBody UsuarioBuscarDTO usuarioDTO) {
        LOGGER.info("Retorna os dados do usuário de acordo com o nome. {}", usuarioDTO.nome());
        List<UsuarioVO> usuariosVO = usuarioService.findByNome(usuarioDTO.nome());
        LOGGER.info("Usuário encontrado com sucesso. {}", usuarioDTO.nome());
        return ResponseEntity.ok(usuariosVO);
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioCriarDTO usuarioDTO) {
        LOGGER.info("Cria um novo usuário com o login: {}", usuarioDTO.login());
        usuarioService.criarUsuario(usuarioDTO);
        LOGGER.info("Usuário criado com sucesso. Login: {}", usuarioDTO.login());
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarUsuario(@Valid @RequestBody UsuarioAtualizarDTO usuarioDTO) {
        LOGGER.info("Atualiza o usuário do id: {}", usuarioDTO.id());
        usuarioService.atualizarUsuario(usuarioDTO);
        LOGGER.info("Usuário atualizado com sucesso Id: {}", usuarioDTO.id());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> atualizarSenhaUsuario(@RequestBody @Valid UsuarioSenhaDTO usuarioDTO) {
        LOGGER.info("Atualiza senha do usuário do id: {}", usuarioDTO.id());
        usuarioService.atualizarSenhaUsuario(usuarioDTO);
        LOGGER.info("Senha atualizado com sucesso Id: {}", usuarioDTO.id());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestBody UsuarioAtualizarDTO usuarioDTO) {
        LOGGER.info("Deleta usuário com ID {}", usuarioDTO.id());
        usuarioService.deletarUsuario(usuarioDTO);
        LOGGER.info("Successfully deleted usuario with id {}", usuarioDTO.id());
        return ResponseEntity.ok().build();
    }
}

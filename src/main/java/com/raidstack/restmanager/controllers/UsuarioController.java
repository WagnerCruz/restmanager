package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.UsuarioDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        LOGGER.info("Retorna uma lista dos usuários cadastrados na base");
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        LOGGER.info("Retorno com sucesso de {} usuarios", usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

     @GetMapping("/usuarios/login")
    public ResponseEntity<UsuarioDTO> getUsuarioByLogin(String login) {
         LOGGER.info("Retorna os dados do usuário de acordo com o login. {}", login);
         UsuarioDTO usuarioDTO = usuarioService.findByLogin(login);
         if (usuarioDTO != null) {
             LOGGER.info("Usuário encontrado com sucesso. {}", login);
             return ResponseEntity.ok(usuarioDTO);
         } else {
             LOGGER.warn("Usuário de login {} não encontrado", login);
         }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/usuarios/criar")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.info("Cria um novo usuário com o login: {}", usuarioDTO.login());
        Integer flagCriado = usuarioService.criarUsuario(usuarioDTO);
        if (flagCriado > 0) {
            LOGGER.info("Usuário criado com sucesso. Login: {}", usuarioDTO.login());
            return ResponseEntity.status(201).build();
        } else {
            LOGGER.error("Erro ao criar usuário com login: {}", usuarioDTO.login());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/usuarios/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.info("Atualiza o usuário do id: {}", usuarioDTO.id());
        Integer flagAtualizado = usuarioService.atualizarUsuario(usuarioDTO);
        if (flagAtualizado > 0) {
            LOGGER.info("Usuário atualizado com sucesso Id: {}", usuarioDTO.id());
            return ResponseEntity.ok().build();
        } else {
            LOGGER.error("Erro ao atualizar usuário com id: {}", usuarioDTO.id());
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/usuarios/deletar")
    public ResponseEntity<Void> deletarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.info("Request received to delete usuario with id {}", usuarioDTO.id());
        String result = usuarioService.deletarUsuario(usuarioDTO);
        if (result.equals("Usuario deletado com sucesso")) {
            LOGGER.info("Successfully deleted usuario with id {}", usuarioDTO.id());
            return ResponseEntity.ok().build();
        } else {
            LOGGER.error("Failed to delete usuario with id {}", usuarioDTO.id());
            return ResponseEntity.status(500).build();
        }
    }
}

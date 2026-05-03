package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.services.UsuarioService;
import com.raidstack.restmanager.vo.UsuarioVO;
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
         Boolean usuarioValido = usuarioService.validarLoginUsuario(usuarioDTO);
         if (usuarioValido) {
             LOGGER.info("Login validado com sucesso {}", usuarioDTO.login());
             return ResponseEntity.noContent().build();
         } else {
             LOGGER.warn("Erro: Login ou Senha incorretos {}", usuarioDTO.login());
         }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/nome")
    public ResponseEntity<UsuarioVO> getUsuarioByNome(@RequestBody UsuarioBuscarDTO usuarioDTO) {
        LOGGER.info("Retorna os dados do usuário de acordo com o nome. {}", usuarioDTO.nome());
        UsuarioVO usuarioVO = usuarioService.findByNome(usuarioDTO.nome());
        if (usuarioVO != null) {
            LOGGER.info("Usuário encontrado com sucesso. {}", usuarioDTO.nome());
            return ResponseEntity.ok(usuarioVO);
        } else {
            LOGGER.warn("Usuário de login {} não encontrado", usuarioDTO.nome());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioCriarDTO usuarioDTO) {
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

    @PutMapping
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioAtualizarDTO usuarioDTO) {
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

    @PutMapping("/senha")
    public ResponseEntity<Void> atualizarSenhaUsuario(@RequestBody UsuarioSenhaDTO usuarioDTO) {
        LOGGER.info("Atualiza senha do usuário do id: {}", usuarioDTO.id());
        Integer flagAtualizado = usuarioService.atualizarSenhaUsuario(usuarioDTO);
        if (flagAtualizado > 0) {
            LOGGER.info("Senha atualizado com sucesso Id: {}", usuarioDTO.id());
            return ResponseEntity.ok().build();
        } else {
            LOGGER.error("Erro ao atualizar senha usuário com id: {}", usuarioDTO.id());
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestBody UsuarioAtualizarDTO usuarioDTO) {
        LOGGER.info("Deleta usuário com ID {}", usuarioDTO.id());
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

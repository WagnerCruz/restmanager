package com.raidstack.restmanager.vo;

import java.time.LocalDateTime;

public record UsuarioVO(
        Integer id,
        String nome,
        String cpf,
        String email,
        String login,
        String endereco,
        int numero,
        String flagProprietario,
        LocalDateTime dataAtualizacao
) {
}

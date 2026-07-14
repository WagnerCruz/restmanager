package com.raidstack.restmanager.helper;

import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.entity.Restaurante;
import com.raidstack.restmanager.entity.Usuario;

import java.sql.Time;

public abstract class MocksHelper {

    public static ItemCardapio mockItemCardapio() {
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setId(1L);
        itemCardapio.setNome_item("Pasta Carbonara");
        itemCardapio.setDescricao("Massa com ovos e bacon");
        itemCardapio.setValor_item(35.50);
        itemCardapio.setDisponibilidade("Sim");
        itemCardapio.setImagem_prato("carbonara.jpg");

        return itemCardapio;
    }

    public static Restaurante mockRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Bom Sabor");
        restaurante.setTipo_cozinha("Italiana");
        restaurante.setHorario_inicio_functo(Time.valueOf("10:00:00"));
        restaurante.setHorario_fim_functo(Time.valueOf("22:00:00"));
        restaurante.setId_usuario(5);

        return restaurante;
    }

    public static Usuario mockUsuarioCliente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setCpf("12345678900");
        usuario.setEmail("joao@email.com");
        usuario.setLogin("joao");
        usuario.setSenha("senha123");
        usuario.setEndereco("Rua A");
        usuario.setNumero(123);
        usuario.setFlagProprietario(false);

        return usuario;
    }

    public static Usuario mockUsuarioProprietario() {
        Usuario usuarioProprietario = new Usuario();
        usuarioProprietario.setNome("Maria");
        usuarioProprietario.setCpf("98765432100");
        usuarioProprietario.setEmail("maria@email.com");
        usuarioProprietario.setLogin("maria");
        usuarioProprietario.setSenha("senha456");
        usuarioProprietario.setEndereco("Rua B");
        usuarioProprietario.setNumero(456);
        usuarioProprietario.setFlagProprietario(true);

        return usuarioProprietario;
    }

}

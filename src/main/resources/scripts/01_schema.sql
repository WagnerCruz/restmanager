CREATE TABLE IF NOT EXISTS usuarios (
   id BIGSERIAL PRIMARY KEY,
   nome VARCHAR(255) NOT NULL,
   cpf VARCHAR(14) NOT NULL UNIQUE,
   email VARCHAR(255) NOT NULL UNIQUE,
   login VARCHAR(100) NOT NULL UNIQUE,
   senha VARCHAR(255) NOT NULL,
   endereco VARCHAR(255),
   numero INTEGER,
   flag_proprietario BOOLEAN DEFAULT FALSE,
   data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS RESTAURANTES(
                             id INT generated always as identity primary key,
                             nome varchar(255) not null,
                             endereco varchar(255) not null,
                             tipo_cozinha varchar(255) not null,
                             horario_inicio_functo time not null default '08:00',
                             horario_fim_functo time not null default '18:00',
                             id_usuario INT not null,
                             constraint  fk_id_usuario
                                 FOREIGN KEY (id_usuario)
                                     REFERENCES usuarios(id)
                                     ON DELETE set null);

CREATE TABLE IF NOT EXISTS ITEM_CARDAPIO(
                              id int generated always as identity primary key,
                              nome_item varchar(255) not null,
                              descricao varchar(255) not null,
                              valor_item numeric(15,2) not null,
                              disponibilidade varchar(100) not null,
                              imagem_prato text);



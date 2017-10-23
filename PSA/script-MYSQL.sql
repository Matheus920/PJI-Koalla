create database pji;

use pji;

create table categoria(
id int not null primary key auto_increment,
nome varchar(255) default null);

create table criterio(
id int not null primary key auto_increment,
nome varchar(255) default null);

create table usuario(
id int not null primary key auto_increment,
nome varchar(255) default null,
email varchar(255) default null,
instituicao varchar(255) default null,
senha varchar(255) default null,
data_nascimento timestamp default current_timestamp,
imagem varchar(255) default null,
palestrante boolean default false,
privilegio int default 1);

create table comite(
id int not null primary key auto_increment,
prontuario varchar(255) default null,
data_nascimento date ,
funcao varchar(255) default null,
email varchar(255) default null,
imagem varchar(255) default null);

create table professor(
id int not null primary key auto_increment,
nome varchar(255) default null,
prontuario varchar(255) default null,
data_nascimento timestamp default current_timestamp,
area varchar(255) default null,
avaliador boolean default false);


create table evento(
id int not null primary key auto_increment,
titulo varchar(255) default null,
resumo varchar(2048) default null,
descricao varchar(1024) default null,
data_inicio timestamp default now(),
duracao int(4) default 0,
`local` varchar(255) default null,
capacidade varchar(255) default null,
`status` boolean default false,
quantidade_palestrantes int(3) default 0
);

create table eventos_avaliadores(
id int not null primary key auto_increment,
id_professor int not null,
id_evento int not null,

constraint fk_eventos_avaliadores_professor foreign key (id_professor)
references professor(id),

constraint fk_eventos_avaliadores_evento foreign key (id_evento)
references evento(id));

create table eventos_categorias(
id int not null primary key auto_increment,
id_categoria int not null,
id_evento int not null,

constraint fk_eventos_categorias_evento foreign key (id_evento)
references evento(id),

constraint fk_eventos_avaliadores_categoria foreign key (id_categoria)
references categoria(id));

create table eventos_criterios(
id int not null primary key auto_increment,
id_criterio int not null,
id_evento int not null,
peso int default 0,

constraint fk_eventos_criterios_criterio foreign key (id_criterio)
references criterio(id),

constraint fk_eventos_criterios_evento foreign key (id_evento)
references evento(id));


create table especializacao(
id int not null primary key auto_increment,
id_professor int not null,
id_categoria int not null,

constraint fk_especializacao_professor foreign key (id_professor) 
references professor(id),

constraint fk_especializacao_categoria foreign key (id_categoria)
references categoria(id));

create table artigo(
id int not null primary key auto_increment,
titulo varchar(255) default null,
caminho varchar(255) default null,
id_evento int not null,
palestrante boolean default false,
constraint fk_artigo_evento foreign key (id_evento)
references evento(id));

create table avaliacao(
id int not null primary key auto_increment,
resultado boolean default false,
mensagem varchar(1024) default false,
id_avaliador int not null,

constraint fk_avaliacao_avaliador foreign key(id_avaliador)
references professor(id));

insert into usuario(email, senha, privilegio) values ('comite', 'comite', 4),
('admin', 'admin', 0),
('avaliador', 'avaliador', 2),
('usuario', 'usuario', 1);

select privilegio from usuario where email = 'comite' and senha = 'comite';
select * from usuario;
-- drop database pji;

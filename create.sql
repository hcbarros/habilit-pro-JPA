create table Empresa (id  bigserial not null, cidade varchar(255) not null, cnpj varchar(255), estado varchar(255) not null, nome varchar(255) not null, nomeFilial varchar(255), regional varchar(255) not null, segmento varchar(255) not null, tipo varchar(255) not null, primary key (id));
create table Modulo (
    id  bigserial not null,
    fimModulo timestamp,
    inicioAvaliacao timestamp,
    inicioModulo timestamp,
    nome varchar(255) not null,
    prazo_limite int4 not null,
    status varchar(255),
    tarefaValidacao varchar(255),
    trilha_id_modulo int8 not null,
    primary key (id));

create table Modulo_habilidades (Modulo_id int8 not null, habilidades varchar(255));
create table ModuloTrabalhador (id  bigserial not null, anotacao varchar(255), avaliacao varchar(255), funcao varchar(255), setor varchar(255), empresa_mod_trab_id int8 not null, modulo_mod_trab_id int8 not null, modulo_trabalhador_id int8, primary key (id));
create table Trabalhador (id  bigserial not null, cpf varchar(255), nome varchar(255), dataAlteracao date, funcao varchar(255), setor varchar(255), empresa_id_trabalhador int8 not null, primary key (id));

create table Trilha (
    id  bigserial not null,
    anotacoes varchar(255),
    apelido varchar(255) not null,
    nome varchar(255) not null,
    ocupacao varchar(255) not null,
    satisfacao varchar(255),
    empresa_id_trilha int8 not null,
    primary key (id));

create table Usuario (
    id  bigserial not null,
    cpf varchar(255),
    nome varchar(255),
    email varchar(255),
    senha varchar(255) not null,
    primary key (id));

create table Usuario_perfis (
    Usuario_id int8 not null,
    perfis int4);


alter table Empresa add constraint UK_s80sy0kcw2wdybiah9jy7y5jo unique (cnpj);
alter table Trabalhador add constraint UK_9oliyb6g3jnhvixacx70ghbs unique (cpf);
alter table Usuario add constraint UK_898atepo5gx8dqj60c07k766b unique (cpf);
alter table Modulo add constraint FKteghl66x4ggmq9nhld1k94mgl foreign key (trilha_id_modulo) references Trilha;
alter table Modulo_habilidades add constraint FKkx0abyc5fh0g68jn8d2tb8cfr foreign key (Modulo_id) references Modulo;
alter table ModuloTrabalhador add constraint FK1dxorec7ds1yqsdyin0upd89w foreign key (empresa_mod_trab_id) references Empresa;
alter table ModuloTrabalhador add constraint FK7cya8w76u2465qwxmgjpvm6iv foreign key (modulo_mod_trab_id) references Modulo;
alter table ModuloTrabalhador add constraint FKkymgkqu2hg1i3lra4ae33f98y foreign key (modulo_trabalhador_id) references Trabalhador;
alter table Trabalhador add constraint FKmc3o1nwcntndqj0pdg38p6k16 foreign key (empresa_id_trabalhador) references Empresa;

alter table Trilha add constraint FK3s1ps4x4th5ofqp6t0rrwlqw8 foreign key (empresa_id_trilha) references Empresa;

alter table Usuario_perfis add constraint FKbl3tsfnts24te4um6sam8y0in foreign key (Usuario_id) references Usuario;


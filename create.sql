create table Empresa (id  bigserial not null, cidade varchar(255) not null, cnpj varchar(255), estado varchar(255) not null, nome varchar(255) not null, nomeFilial varchar(255), regional varchar(255) not null, segmento varchar(255) not null, tipo varchar(255) not null, primary key (id));
create table Modulo (id  bigserial not null, fimModulo timestamp, inicioAvaliacao timestamp, inicioModulo timestamp, nome varchar(255) not null, prazo_limite int4 not null, status varchar(255), tarefaValidacao varchar(255), trilha_id_modulo int8 not null, primary key (id));
create table Modulo_habilidades (Modulo_id int8 not null, habilidades varchar(255));

create table ModuloTrabalhador (
    id  bigserial not null,
    anotacao varchar(255),
    avaliacao varchar(255),
    cpf varchar(255),
    funcao varchar(255) not null,
    setor varchar(255) not null,
    modulo_mod_trab_id int8 not null,
    modulo_trabalhador_id int8,
    primary key (id));

create table Trabalhador (id  bigserial not null, cpf varchar(255), nome varchar(255) not null, dataAlteracao date, funcao varchar(255), setor varchar(255), empresa_id_trabalhador int8 not null, primary key (id));

create table Trilha (
    id  bigserial not null,
    anotacoes varchar(255),
    apelido varchar(255),
    nome varchar(255),
    ocupacao varchar(255) not null,
    satisfacao varchar(255),
    empresa_id_trilha int8 not null, primary key (id));


create table Usuario (id  bigserial not null, cpf varchar(255), nome varchar(255) not null, email varchar(255), senha varchar(255) not null, primary key (id));
create table Usuario_perfis (Usuario_id int8 not null, perfis int4);
alter table Empresa add constraint UK_s80sy0kcw2wdybiah9jy7y5jo unique (cnpj);
alter table Trabalhador add constraint UK_9oliyb6g3jnhvixacx70ghbs unique (cpf);
alter table Usuario add constraint UK_898atepo5gx8dqj60c07k766b unique (cpf);
alter table Modulo add constraint FKteghl66x4ggmq9nhld1k94mgl foreign key (trilha_id_modulo) references Trilha;
alter table Modulo_habilidades add constraint FKkx0abyc5fh0g68jn8d2tb8cfr foreign key (Modulo_id) references Modulo;
alter table ModuloTrabalhador add constraint FK7cya8w76u2465qwxmgjpvm6iv foreign key (modulo_mod_trab_id) references Modulo;
alter table ModuloTrabalhador add constraint FKkymgkqu2hg1i3lra4ae33f98y foreign key (modulo_trabalhador_id) references Trabalhador;
alter table Trabalhador add constraint FKmc3o1nwcntndqj0pdg38p6k16 foreign key (empresa_id_trabalhador) references Empresa;
alter table Trilha add constraint FK3s1ps4x4th5ofqp6t0rrwlqw8 foreign key (empresa_id_trilha) references Empresa;
alter table Usuario_perfis add constraint FKbl3tsfnts24te4um6sam8y0in foreign key (Usuario_id) references Usuario;
insert into Empresa (nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional) values ('Tabajara LTDA','80.641.905/0001-80','FILIAL','Tabajara','FUMO','PE','Recife','OUTRO');
insert into Empresa (nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional) values ('Construções piratas','48.491.224/0001-70','FILIAL','Pirata','CONSTRUCAO','SC','Tubarão','SUL');
insert into Empresa (nome, cnpj, tipo, segmento, estado, cidade, regional) values ('Fake Enterprise','05.130.415/0001-01','MATRIZ','TIC','SC','Florianópolis','OESTE');
insert into Trilha (empresa_id_trilha, ocupacao) values (1, 'Desenvolvimento de software');
insert into Trilha (empresa_id_trilha, ocupacao) values (2, 'Trabalho em equipe');
insert into Trilha (empresa_id_trilha, ocupacao) values (3, 'Produtividade no trabalho');
insert into Modulo (trilha_id_modulo, prazo_limite, nome) values (1, 10, 'Orientação a objetos');
insert into Modulo (trilha_id_modulo, prazo_limite, nome) values (2, 10, 'Relacionamento interpessoal');
insert into Modulo (trilha_id_modulo, prazo_limite, nome) values (3, 10, 'Foco na atividade');
insert into Trabalhador (cpf, nome, dataAlteracao, funcao, setor, empresa_id_trabalhador) values ('554.250.480-92', 'Alex', '2020-10-10', 'Programador I', 'T.I.', 1);
insert into Trabalhador (cpf, nome, dataAlteracao, funcao, setor, empresa_id_trabalhador) values ('865.177.570-90', 'Adriana', '2021-05-15', 'Analista de RH', 'RH', 2);
insert into Trabalhador (cpf, nome, dataAlteracao, funcao, setor, empresa_id_trabalhador) values ('468.521.560-52', 'Rodrigo', '2022-01-22', 'Motorista', 'Manutenção', 3);
insert into Usuario (nome, cpf, email, senha) values ('José', '058.677.000-38', 'jose@gmail.com', 'jose1234');
insert into Usuario (nome, cpf, email, senha) values ('Maria', '612.768.110-39', 'maria@gmail.com', 'maria123');
insert into Usuario (nome, cpf, email, senha) values ('Manoel', '729.295.050-79', 'manoel@gmail.com', 'manoel123');
insert into Usuario_perfis (Usuario_id, perfis) values (1, 1);
insert into Usuario_perfis (Usuario_id, perfis) values (2, 2);
insert into Usuario_perfis (Usuario_id, perfis) values (3, 1);
insert into Usuario_perfis (Usuario_id, perfis) values (3, 0);
insert into Modulo_habilidades (Modulo_id, habilidades) values (1, 'RACIOCÍNIO LÓGICO');
insert into Modulo_habilidades (Modulo_id, habilidades) values (2, 'INTELIGÊNCIA EMOCIONAL');
insert into Modulo_habilidades (Modulo_id, habilidades) values (3, 'CONCENTRAÇÂO');

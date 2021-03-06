
CREATE OR REPLACE FUNCTION contar(ocup varchar, emp_id bigint) RETURNS integer AS $$ BEGIN return(select count(*) from Trilha where lower(ocupacao) = lower(ocup) and empresa_id_trilha = emp_id); END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION define_nomes() RETURNS trigger LANGUAGE PLPGSQL AS $$ BEGIN update Trilha set nome = replace(ocupacao,' ','_') || replace((select nome from Empresa where id = empresa_id_trilha),' ','_') || contar(ocupacao, empresa_id_trilha) || extract(year from now()), apelido = replace(ocupacao,' ','_') || contar(ocupacao, empresa_id_trilha) where nome is null; RETURN NEW; END; $$

DROP TRIGGER IF EXISTS nomes_trigger ON Trilha;
CREATE TRIGGER nomes_trigger AFTER INSERT OR UPDATE ON Trilha FOR EACH ROW EXECUTE PROCEDURE define_nomes();


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

insert into ModuloTrabalhador (cpf, funcao, setor, avaliacao, modulo_mod_trab_id, modulo_trabalhador_id) values ('554.250.480-92', 'Programador I', 'T.I.', 'NOTA_3', 1, 1);
insert into ModuloTrabalhador (cpf, funcao, setor, avaliacao, modulo_mod_trab_id, modulo_trabalhador_id) values ('865.177.570-90', 'Analista de RH', 'RH', 'NOTA_4', 2, 2);
insert into ModuloTrabalhador (cpf, funcao, setor, avaliacao, modulo_mod_trab_id, modulo_trabalhador_id) values ('468.521.560-52', 'Motorista', 'Manutenção', 'NOTA_4', 3, 3);

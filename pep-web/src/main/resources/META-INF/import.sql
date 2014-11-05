--cria usuarios
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, nome, telefone, qt_acessos_errados, chk_ativo) VALUES (1, '05009090935', 'guisiagudos@gmail.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', 'Douglas Maciel Guisi', '4192669662', 0, true);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, nome, telefone, qt_acessos_errados, chk_ativo) VALUES (2, '12345678901', 'teste@gmail.com', 'sclHl/b+xwu+/b86PIYl+Q==', 'Usu�rio de Testes', '4199998888', 0, true);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, nome, telefone, qt_acessos_errados, chk_ativo) VALUES (3, '98765432101', 'semperfil@email.com', 'sclHl/b+xwu+/b86PIYl+Q==', 'Usu�rio Sem Perfil', '4199998888', 0, true);
ALTER SEQUENCE pep_owner.usuario_sequence RESTART WITH 4;

--cria perfis
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios) VALUES (1, 'Administrador', 'Perfil com autoriza��o para todas as funcionalidades do sistema.', false, false);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios) VALUES (2, 'Recepcionista', 'Perfil com autoriza��o para funcionalidades necess�rias para atendimento na recep��o do estabelecimento m�dico.', false, false);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios) VALUES (3, 'Paciente', 'Perfil com autoriza��o para funcionalidades dispon�veis para pacientes.', false, true);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios) VALUES (4, 'Profissional M�dico', 'Perfil com autoriza��o para funcionalidades dispon�veis para os profissionais m�dicos.', true, true);
ALTER SEQUENCE pep_owner.perfil_sequence RESTART WITH 5;

--cria autorizacoes
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (1, 'EDITAR_PERFIS', 'Autoriza��o para visualizar, criar, editar e excluir perfis de usu�rio.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (2, 'EDITAR_USUARIOS', 'Autoriza��o para visualizar, criar, editar e excluir usu�rios.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (3, 'VISUALIZAR_USUARIOS', 'Autoriza��o para visualizar usu�rios.');
ALTER SEQUENCE pep_owner.autorizacao_sequence RESTART WITH 4;

--vincula perfis aos usuarios
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (1, 1);
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (2, 2);

--vincula autorizacoes de edicao ao perfil Administrador
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 1);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 2);
--vincula autorizacao VISUALIZAR_USUARIOS ao perfil Recepcionista
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (2, 3);
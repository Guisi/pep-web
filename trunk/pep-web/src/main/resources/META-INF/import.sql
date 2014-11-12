--cria usuarios
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, optlock) VALUES (1, '05009490935', 'guisiagudos@gmail.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Douglas Maciel Guisi', 'Douglas', '4192669662', 0, true, 0);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, optlock) VALUES (2, '12345678901', 'teste@gmail.com', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Usu�rio de Testes', 'Teste', '4199998888', 0, true, 0);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, optlock) VALUES (3, '98765432101', 'semperfil@email.com', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Usu�rio Sem Perfil', null, '4199998888', 0, true, 0);
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

--especialidades
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (1, 'Acupuntura');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (2, 'Alergia e Imunologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (3, 'Anestesiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (4, 'Angiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (5, 'Cancerologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (6, 'Cardiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (7, 'Cirurgia Cardiovascular');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (8, 'Cirurgia da M�o');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (9, 'Cirurgia de Cabe�a e Pesco�o');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (10, 'Cirurgia do Aparelho Digestivo');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (11, 'Cirurgia Geral');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (12, 'Cirurgia Pedi�trica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (13, 'Cirurgia Pl�stica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (14, 'Cirurgia Tor�cica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (15, 'Cirurgia Vascular');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (16, 'Cl�nica M�dica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (17, 'Coloproctologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (18, 'Dermatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (19, 'Endocrinologia e Metabologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (20, 'Endoscopia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (21, 'Gastroenterologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (22, 'Gen�tica M�dica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (23, 'Geriatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (24, 'Ginecologia e Obstetr�cia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (25, 'Hematologia e Hemoterapia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (26, 'Homeopatia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (27, 'Infectologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (28, 'Mastologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (29, 'Medicina de Fam�lia e Comunidade');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (30, 'Medicina do Trabalho');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (31, 'Medicina de Tr�fego');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (32, 'Medicina Esportiva');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (33, 'Medicina F�sica e Reabilita��o');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (34, 'Medicina Intensiva');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (35, 'Medicina Legal e Per�cia M�dica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (36, 'Medicina Nuclear');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (37, 'Medicina Preventiva e Social');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (38, 'Nefrologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (39, 'Neurocirurgia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (40, 'Neurologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (41, 'Nutrologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (42, 'Oftalmologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (43, 'Ortopedia e Traumatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (44, 'Otorrinolaringologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (45, 'Patologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (46, 'Patologia Cl�nica/Medicina Laboratorial');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (47, 'Pediatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (48, 'Pneumologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (49, 'Psiquiatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (50, 'Radiologia e Diagn�stico por Imagem');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (51, 'Radioterapia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (52, 'Reumatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, nome_completo) VALUES (53, 'Urologia');
ALTER SEQUENCE pep_owner.especialidade_sequence RESTART WITH 54;
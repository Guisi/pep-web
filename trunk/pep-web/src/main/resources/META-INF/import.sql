--cria usuarios
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, data_nascimento, sexo, optlock, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (1, '76217092786', 'administrador@pepweb.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Administrador do PEP', 'Administrador', '4188889999', 0, true, '1980-01-01', 'M', 0, '80230000', 'Avenida Silva Jardim', '1194', 'Casa', 'Rebouças', 'Curitiba', 'PR');
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, data_nascimento, sexo, optlock, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (2, '72228163481', 'recepcionista@pepweb.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Recepcionista do PEP', 'Recepcionista', '4199998888', 0, true, '1990-01-01', 'F', 0, '80230000', 'Avenida Silva Jardim', '1194', 'Casa', 'Rebouças', 'Curitiba', 'PR');
ALTER SEQUENCE pep_owner.usuario_sequence RESTART WITH 3;

--cria perfis
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios, chk_perfil_predefinido) VALUES (1, 'Administrador', 'Perfil com autorização para todas as funcionalidades do sistema.', false, false, false);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios, chk_perfil_predefinido) VALUES (2, 'Recepcionista', 'Perfil com autorização para funcionalidades necessárias para atendimento na recepção do estabelecimento médico.', false, false, false);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios, chk_perfil_predefinido) VALUES (3, 'Paciente', 'Perfil com autorização para funcionalidades disponíveis para pacientes.', false, true, true);
INSERT INTO pep_owner.tb_perfil(id_perfil, nome, descricao, chk_possui_especialidades, chk_possui_convenios, chk_perfil_predefinido) VALUES (4, 'Profissional Médico', 'Perfil com autorização para funcionalidades disponíveis para os profissionais médicos.', true, true, false);
ALTER SEQUENCE pep_owner.perfil_sequence RESTART WITH 5;

--cria autorizacoes
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (1, 'EDITAR_PERFIS', 'Autorização para visualizar, criar, editar e excluir perfis de usuário.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (2, 'EDITAR_USUARIOS', 'Autorização para visualizar, criar, editar e excluir usuários.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (3, 'VISUALIZAR_USUARIOS', 'Autorização para visualizar usuários.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (4, 'EDITAR_INFORMACOES_PESSOAIS', 'Autorização para editar informações pessoais.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (5, 'EDITAR_ESPECIALIDADES', 'Autorização para funcionalidade de manutenção de especialidades.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (6, 'EDITAR_CONVENIOS', 'Autorização para funcionalidade de manutenção de convênios.');
ALTER SEQUENCE pep_owner.autorizacao_sequence RESTART WITH 7;

--vincula perfis aos usuarios
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (1, 1);
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (2, 2);

--vincula autorizacoes de edicao ao perfil Administrador
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 1);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 2);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 5);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 6);

--vincula autorizacao EDITAR_INFORMACOES_PESSOAIS a todos os perfis
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 4);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (2, 4);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (3, 4);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (4, 4);

--especialidades
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (1, 'Acupuntura');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (2, 'Alergia e Imunologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (3, 'Anestesiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (4, 'Angiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (5, 'Cancerologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (6, 'Cardiologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (7, 'Cirurgia Cardiovascular');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (8, 'Cirurgia da Mão');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (9, 'Cirurgia de Cabeça e Pescoço');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (10, 'Cirurgia do Aparelho Digestivo');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (11, 'Cirurgia Geral');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (12, 'Cirurgia Pediátrica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (13, 'Cirurgia Plástica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (14, 'Cirurgia Torácica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (15, 'Cirurgia Vascular');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (16, 'Clínica Médica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (17, 'Coloproctologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (18, 'Dermatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (19, 'Endocrinologia e Metabologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (20, 'Endoscopia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (21, 'Gastroenterologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (22, 'Genética Médica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (23, 'Geriatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (24, 'Ginecologia e Obstetrícia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (25, 'Hematologia e Hemoterapia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (26, 'Homeopatia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (27, 'Infectologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (28, 'Mastologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (29, 'Medicina de Família e Comunidade');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (30, 'Medicina do Trabalho');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (31, 'Medicina de Tráfego');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (32, 'Medicina Esportiva');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (33, 'Medicina Física e Reabilitação');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (34, 'Medicina Intensiva');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (35, 'Medicina Legal e Perícia Médica');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (36, 'Medicina Nuclear');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (37, 'Medicina Preventiva e Social');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (38, 'Nefrologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (39, 'Neurocirurgia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (40, 'Neurologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (41, 'Nutrologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (42, 'Oftalmologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (43, 'Ortopedia e Traumatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (44, 'Otorrinolaringologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (45, 'Patologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (46, 'Patologia Clínica/Medicina Laboratorial');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (47, 'Pediatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (48, 'Pneumologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (49, 'Psiquiatria');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (50, 'Radiologia e Diagnóstico por Imagem');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (51, 'Radioterapia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (52, 'Reumatologia');
INSERT INTO pep_owner.tb_especialidade(id_especialidade, descricao) VALUES (53, 'Urologia');
ALTER SEQUENCE pep_owner.especialidade_sequence RESTART WITH 54;

--convenios
INSERT INTO pep_owner.tb_convenio(id_convenio, descricao) VALUES (1, 'Unimed');
INSERT INTO pep_owner.tb_convenio(id_convenio, descricao) VALUES (2, 'Paraná Clínicas');
INSERT INTO pep_owner.tb_convenio(id_convenio, descricao) VALUES (3, 'Amil');
ALTER SEQUENCE pep_owner.convenio_sequence RESTART WITH 4;
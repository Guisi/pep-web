--cria usuarios
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, data_nascimento, sexo, optlock, cep, logradouro, numero, complemento, bairro, cidade, uf, nr_prontuario) VALUES (1, '76217092786', 'administrador@pepweb.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Administrador do PEP', 'Administrador', '4188889999', 0, true, '1980-01-01', 'M', 0, '80230000', 'Avenida Silva Jardim', '1194', 'Casa', 'Rebouças', 'Curitiba', 'PR', 232425);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, data_nascimento, sexo, optlock, cep, logradouro, numero, complemento, bairro, cidade, uf, nr_prontuario) VALUES (2, '72228163481', 'recepcionista@pepweb.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Recepcionista do PEP', 'Recepcionista', '4199998888', 0, true, '1990-01-01', 'F', 0, '80230000', 'Avenida Silva Jardim', '1194', 'Casa', 'Rebouças', 'Curitiba', 'PR', 232426);
INSERT INTO pep_owner.tb_usuario(id_usuario, cpf, email, senha, chk_senha_provisoria, nome_completo, nome_fantasia, celular, qt_acessos_errados, chk_ativo, data_nascimento, sexo, optlock, cep, logradouro, numero, complemento, bairro, cidade, uf, nr_prontuario) VALUES (3, '40424527103', 'paciente@pepweb.com.br', 'sclHl/b+xwu+/b86PIYl+Q==', false, 'Paciente do PEP', 'Paciente', '4198765432', 0, true, '1990-01-01', 'M', 0, '80230000', 'Avenida Silva Jardim', '1194', 'Casa', 'Rebouças', 'Curitiba', 'PR', 232427);
ALTER SEQUENCE pep_owner.usuario_sequence RESTART WITH 4;

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
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (7, 'EDITAR_PACIENTES', 'Autorização para visualizar, criar, editar e excluir pacientes.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (8, 'VISUALIZAR_PACIENTES', 'Autorização para visualizar pacientes.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (9, 'EDITAR_MEDICAMENTOS', 'Autorização para editar medicamentos.');
ALTER SEQUENCE pep_owner.autorizacao_sequence RESTART WITH 10;

--vincula perfis aos usuarios
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (1, 1);
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (2, 2);
INSERT INTO pep_owner.tb_usuario_perfil(id_usuario, id_perfil) VALUES (3, 3);

--vincula autorizacoes de edicao ao perfil Administrador
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 1);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 2);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 5);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 6);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 7);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 9);

--vincula autorizacoes de edicao de paciente ao perfil Recepcionista
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (2, 7);

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

-- medicamentos (somente alguns, a lista total esta no arquivo import_medicamentos.sql)
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (1, 'ABATACEPTE', '125 MG/ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (2, 'ABATACEPTE', '250 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (3, 'ABCIXIMABE', '2 MG/ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (4, 'ACARBOSE', '100 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (5, 'ACEBROFILINA', '10 MG/ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (6, 'ACEBROFILINA', '25 MG/5ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (7, 'ACEBROFILINA', '5 MG/ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (8, 'ACECLOFENACO', '100 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (9, 'ACEPONATO DE METILPREDNISOLONA', '1 MG/G', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (10, 'ACETATO DE ABIRATERONA', '250 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (11, 'ACETATO DE BETAMETASONA', '3 MG+3 MG/ML', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (12, 'ACETATO DE BUSSERRELINA', '3,3 MG/MICROBAST', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (13, 'ACETATO DE CASPOFUNGINA', '50 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (14, 'ACETATO DE CETRORRELIX', '0,25 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (15, 'ACETATO DE CIPROTERONA', '100 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (16, 'ACETATO DE CIPROTERONA', '50 MG', true);
INSERT INTO pep_owner.tb_medicamento(id_medicamento, principio_ativo, apresentacao, chk_ativo) VALUES (17, 'ACETATO DE CIPROTERONA - ETINILESTRADIOL', '(0,035 + 2) MG', true);
ALTER SEQUENCE pep_owner.medicamento_sequence RESTART WITH 18;

--atendimento
INSERT INTO pep_owner.tb_atendimento(id_atendimento, data, id_paciente) VALUES (1, now(), 3);
ALTER SEQUENCE pep_owner.atendimento_sequence RESTART WITH 2;

--tratamentos
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento, id_medicamento) VALUES (1, '', 'a cada 8 horas', 1, 1);
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento) VALUES (2, 'Paracetamol', 'a cada 4 horas', 1);
ALTER SEQUENCE pep_owner.medicamento_atendimento_sequence RESTART WITH 2;
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
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (10, 'ALTERAR_EMAIL_USUARIO', 'Autorização para modificar e-mail do usuário.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (11, 'EDITAR_QUEIXAS_PRINCIPAIS', 'Autorização para editar queixas principais.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (12, 'MANUTENIR_ATENDIMENTOS', 'Autorização para ver, criar e editar atendimentos do paciente.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (13, 'VISUALIZAR_ATENDIMENTOS', 'Autorização para ver atendimentos do paciente.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (14, 'EDITAR_DOENCAS', 'Autorização para editar doenças.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (15, 'EDITAR_PROCEDIMENTOS', 'Autorização para editar procedimentos.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (16, 'EDITAR_HABITOS', 'Autorização para editar hábitos.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (17, 'EDITAR_ALERGIAS', 'Autorização para editar alergias.');
INSERT INTO pep_owner.tb_autorizacao(id_autorizacao, nome, descricao) VALUES (18, 'EDITAR_VACINAS', 'Autorização para editar vacinas.');
ALTER SEQUENCE pep_owner.autorizacao_sequence RESTART WITH 19;

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
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 10);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 11);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 12);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 14);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 15);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 16);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 17);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (1, 18);

--vincula autorizacoes de edicao de paciente e atendimentos ao perfil Recepcionista
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (2, 7);
INSERT INTO pep_owner.tb_perfil_autorizacao(id_perfil, id_autorizacao) VALUES (2, 12);

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

-- queixas principais
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (1, 'Alteração laboratorial', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (2, 'Dor de garganta', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (3, 'Dor nas costas', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (4, 'Fadiga', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (5, 'Astenia', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (6, 'Hipertensão arterial', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (7, 'Dor (especificar)', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (8, 'Cefaléia', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (9, 'Cessação de tabagismo', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (10, 'Dispnéia', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (11, 'Dor de cabeça', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (12, 'Dor lombar', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (13, 'Exame de prevenção', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (14, 'Asfixia', true);
INSERT INTO pep_owner.tb_queixa_principal(id_queixa_principal, descricao, chk_ativo) VALUES (15, 'Abandono', true);
ALTER SEQUENCE pep_owner.queixa_principal_sequence RESTART WITH 16;

--doencas
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (1, 'A00.0', 'Colera dev Vibrio cholerae 01 biot cholerae', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (2, 'A00.1', 'Colera dev Vibrio cholerae 01 biot El Tor', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (3, 'A00.9', 'Colera NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (4, 'A01.0', 'Febre tifoide', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (5, 'A01.1', 'Febre paratifoide A', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (6, 'A01.2', 'Febre paratifoide B', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (7, 'A01.3', 'Febre paratifoide C', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (8, 'A01.4', 'Febre paratifoide NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (9, 'A02.0', 'Enterite p/salmonela', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (10, 'A02.1', 'Septicemia p/salmonela', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (11, 'A02.2', 'Infecc localizadas p/salmonela', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (12, 'A02.8', 'Outr infecc espec p/salmonela', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (13, 'A02.9', 'Infecc NE p/salmonela', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (14, 'A03.0', 'Shiguelose dev Shigella dysenteriae', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (15, 'A03.1', 'Shiguelose dev Shigella flexneri', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (16, 'A03.2', 'Shiguelose dev Shigella boydii', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (17, 'A03.3', 'Shiguelose dev Shigella sonnei', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (18, 'A03.8', 'Outr shigueloses', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (19, 'A03.9', 'Shiguelose NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (20, 'A04.0', 'Infecc p/Escherichia coli enteropatogenica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (21, 'A04.1', 'Infecc p/Escherichia coli enterotoxigenica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (22, 'A04.2', 'Infecc p/Escherichia coli enteroinvasiva', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (23, 'A04.3', 'Infecc p/Escherichia coli enterohemorragica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (24, 'A04.4', 'Outr infecc intestinais p/Escherichia coli', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (25, 'A04.5', 'Enterite p/Campylobacter', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (26, 'A04.6', 'Enterite dev Yersinia enterocolitica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (27, 'A04.7', 'Enterocolite dev Clostridium difficile', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (28, 'A04.8', 'Outr infecc bacter intestinais espec', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (29, 'A04.9', 'Infecc intestinal bacter NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (30, 'A05.0', 'Intox alimentar estafilococica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (31, 'A05.1', 'Botulismo', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (32, 'A05.2', 'Intox alimentar dev Clostridium perfringens', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (33, 'A05.3', 'Intox alimentar dev Vibrio parahemolyticus', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (34, 'A05.4', 'Intox alimentar dev Bacillus cereus', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (35, 'A05.8', 'Outr intox alimentares bacter espec', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (36, 'A05.9', 'Intox alimentar bacter NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (37, 'A06.0', 'Disenteria amebiana aguda', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (38, 'A06.1', 'Amebiase intestinal cronica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (39, 'A06.2', 'Colite amebiana nao-disenterica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (40, 'A06.3', 'Ameboma intestinal', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (41, 'A06.4', 'Abscesso amebiano do figado', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (42, 'A06.5', 'Abscesso amebiano do pulmao', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (43, 'A06.6', 'Abscesso amebiano do cerebro', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (44, 'A06.7', 'Amebiase cutanea', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (45, 'A06.8', 'Infecc amebiana de outr localiz', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (46, 'A06.9', 'Amebiase NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (47, 'A07.0', 'Balantidiase', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (48, 'A07.1', 'Giardiase', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (49, 'A07.2', 'Criptosporidiose', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (50, 'A07.3', 'Isosporiase', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (51, 'A07.8', 'Outr doenc intestinais espec p/protozoarios', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (52, 'A07.9', 'Doenc intestinal NE p/protozoarios', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (53, 'A08.0', 'Enterite p/rotavirus', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (54, 'A08.1', 'Gastroenteropatia aguda p/agente de Norwalk', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (55, 'A08.2', 'Enterite p/adenovirus', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (56, 'A08.3', 'Outr enterites virais', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (57, 'A08.4', 'Infecc intestinal dev virus NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (58, 'A08.5', 'Outr infecc intestinais espec', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (59, 'A15.0', 'Tuberc pulm c/conf p/ex micr expec c/s/cult', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (60, 'A15.1', 'Tuberc pulmonar c/conf somente p/cultura', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (61, 'A15.2', 'Tuberc pulmonar c/conf histolog', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (62, 'A15.3', 'Tuberc pulmonar c/conf p/meio NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (63, 'A15.4', 'Tuberc gangl intrator c/conf bacter e histol', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (64, 'A15.5', 'Tuberc laring traq bronq c/conf bact e hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (65, 'A15.6', 'Pleuris tuberc c/conf bacteriol e histolog', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (66, 'A15.7', 'Tuberc prim vias respir c/conf bact e histol', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (67, 'A15.8', 'Outr form tuberc via resp c/conf bact hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (68, 'A15.9', 'Tuberc NE vias respir c/conf bacter histol', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (69, 'A16.0', 'Tuberc pulmonar c/exames bact e hist negat', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (70, 'A16.1', 'Tuberc pulmonar s/exame bacter ou histolog', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (71, 'A16.2', 'Tuberc pulmonar s/menc conf bact ou hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (72, 'A16.3', 'Tuberc gangl intrat s/menc conf bact hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (73, 'A16.4', 'Tuberc laring traq bronq s/menc bact hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (74, 'A16.5', 'Pleurisia tuberc s/menc conf bact ou histol', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (75, 'A16.7', 'Tuberc respir prim s/menc conf bact ou hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (76, 'A16.8', 'Outr tuber via resp s/menc conf bact hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (77, 'A16.9', 'Tuberc respirat NE s/menc conf bact ou hist', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (78, 'A17.0', 'Meningite tuberc', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (79, 'A17.1', 'Tuberculoma meningeo', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (80, 'A17.8', 'Outr tuberc do sist nervoso', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (81, 'A17.9', 'Tuberc NE do sist nervoso', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (82, 'A18.0', 'Tuberc ossea e das articulacoes', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (83, 'A18.1', 'Tuberc do aparelho geniturinario', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (84, 'A18.2', 'Linfadenopatia tuberc periferica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (85, 'A18.3', 'Tuberc intestino peritonio gangl mesenter', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (86, 'A18.4', 'Tuberc de pele e tec celular subcutaneo', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (87, 'A18.5', 'Tuberc do olho', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (88, 'A18.6', 'Tuberc do ouvido', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (89, 'A18.7', 'Tuberc das supra-renais', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (90, 'A18.8', 'Tuberc de outr orgaos espec', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (91, 'A19.0', 'Tuberc miliar aguda de localiz unica espec', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (92, 'A19.1', 'Tuberc miliar aguda de mult localiz', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (93, 'A19.2', 'Tuberc miliar aguda NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (94, 'A19.8', 'Outr tuberc miliares', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (95, 'A19.9', 'Tuberc miliar NE', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (96, 'A20.0', 'Peste bubonica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (97, 'A20.1', 'Peste celulocutanea', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (98, 'A20.2', 'Peste pneumonica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (99, 'A20.3', 'Peste meningea', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (100, 'A20.7', 'Peste septicemica', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (101, 'A20.8', 'Outr form de peste', true);
INSERT INTO pep_owner.tb_doenca(id_doenca, codigocid, descricao, chk_ativo) VALUES (102, 'A20.9', 'Peste form NE', true);
ALTER SEQUENCE pep_owner.doenca_sequence RESTART WITH 103;

--procedimentos
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (1, 'Mamoplastia', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (2, 'Amigdalectomia', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (3, 'Colecistectomia', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (4, 'Herniorrafia Inguinal', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (5, 'Apendicectomia', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (6, 'Histerectomia total laparoscópica', 'CIRURGICO', true);
INSERT INTO pep_owner.tb_procedimento(id_procedimento, descricao, tipo_procedimento, chk_ativo) VALUES (7, 'Tireoidectomia total', 'CIRURGICO', true);
ALTER SEQUENCE pep_owner.procedimento_sequence RESTART WITH 8;

--habitos
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (1, 'Atividade física', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (2, 'Sedentarismo', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (3, 'Etilismo', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (4, 'Ex-tabagista', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (5, 'Hábitos alimentares inadequados', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (6, 'Tabagista', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (7, 'Beliscador', true);
INSERT INTO pep_owner.tb_habito(id_habito, descricao, chk_ativo) VALUES (8, 'Uso de drogas', true);
ALTER SEQUENCE pep_owner.habito_sequence RESTART WITH 9;

--alergias
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (1, 'Frutos do mar', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (2, 'Aditivos alimentares', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (3, 'Ácaros', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (4, 'Lactose', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (5, 'Iodo', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (6, 'Corantes', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (7, 'Glúten', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (8, 'Amendoim', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (9, 'Insetos', true);
INSERT INTO pep_owner.tb_alergia(id_alergia, descricao, chk_ativo) VALUES (10, 'Látex', true);
ALTER SEQUENCE pep_owner.alergia_sequence RESTART WITH 11;

--alergias
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (1, 'Anti-tetânica', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (2, 'Pneumocócica Polivalente (Pneumo 23)', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (3, 'Meningite C', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (4, 'Rotavírus', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (5, 'Raiva', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (6, 'Varicela', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (7, 'Tríplice Viral (Sar.+ Cax.+ Rub.) mmr/scr', true);
INSERT INTO pep_owner.tb_vacina(id_vacina, descricao, chk_ativo) VALUES (8, 'Tetraxim (Dpat + Salk)', true);
ALTER SEQUENCE pep_owner.vacina_sequence RESTART WITH 9;

--##########################
--#### atendimento #########
--##########################
--postgres
--INSERT INTO pep_owner.tb_atendimento(id_atendimento, data, id_paciente, historia_doenca_atual, isda) VALUES (1, '2015-01-10 15:35', 3, 'História atendimento 1', 'ISDA atendimento 1');
--INSERT INTO pep_owner.tb_atendimento(id_atendimento, data, id_paciente, historia_doenca_atual, isda) VALUES (2, '2015-05-12 10:30', 3, 'História atendimento 2', 'ISDA atendimento 2');
--h2
INSERT INTO pep_owner.tb_atendimento(id_atendimento, data, id_paciente, historia_doenca_atual, isda) VALUES (1, PARSEDATETIME('10 Jan 2015 15:35 GMT',  'dd MMM yyyy HH:mm z', 'en', 'GMT'), 3, 'História atendimento 1', 'ISDA atendimento 1');
INSERT INTO pep_owner.tb_atendimento(id_atendimento, data, id_paciente, historia_doenca_atual, isda, lembretes) VALUES (2, PARSEDATETIME('12 May 2015 10:30 GMT',  'dd MMM yyyy HH:mm z', 'en', 'GMT'), 3, 'História atendimento 2', 'ISDA atendimento 2', 'Só lembrando que não é pra esquecer de lembrar, ok?');
ALTER SEQUENCE pep_owner.atendimento_sequence RESTART WITH 3;

--queixas principais
INSERT INTO pep_owner.tb_queixa_principal_atendimento(id_queixa_principal_atendimento, descricao, observacao, id_atendimento, id_queixa_principal) VALUES (1, '', 'tá doendo muito', 1, 2);
INSERT INTO pep_owner.tb_queixa_principal_atendimento(id_queixa_principal_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Dor de cotovelo', 'Pode estar relacionado à desilusão amorosa...', 1);
INSERT INTO pep_owner.tb_queixa_principal_atendimento(id_queixa_principal_atendimento, descricao, observacao, id_atendimento, id_queixa_principal) VALUES (3, '', 'tadinho', 2, 15);
INSERT INTO pep_owner.tb_queixa_principal_atendimento(id_queixa_principal_atendimento, descricao, observacao, id_atendimento) VALUES (4, 'Dilma rouba demais', 'Dica, mude-se para outro país', 2);
ALTER SEQUENCE pep_owner.queixa_principal_atendimento_sequence RESTART WITH 5;

--tratamentos
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento, id_medicamento, chk_em_uso) VALUES (1, '', 'a cada 8 horas', 1, 1, true);
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento, chk_em_uso) VALUES (2, 'Paracetamol', 'a cada 4 horas', 1, true);
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento, id_medicamento, chk_em_uso) VALUES (3, '', 'tomar de meia em meia hora', 2, 9, true);
INSERT INTO pep_owner.tb_medicamento_atendimento(id_medicamento_atendimento, descricao, apresentacao, id_atendimento, chk_em_uso) VALUES (4, 'Antigripal', 'a cada 12 horas', 2, true);
ALTER SEQUENCE pep_owner.medicamento_atendimento_sequence RESTART WITH 5;

--antecedentes clinicos
INSERT INTO pep_owner.tb_antecedente_clinico_atendimento(id_antecedente_clinico_atendimento, descricao, observacao, id_atendimento, id_doenca) VALUES (1, '', 'Foi foda!', 1, 1);
INSERT INTO pep_owner.tb_antecedente_clinico_atendimento(id_antecedente_clinico_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Gripe aviária', 'Quase morri!', 1);
INSERT INTO pep_owner.tb_antecedente_clinico_atendimento(id_antecedente_clinico_atendimento, descricao, observacao, id_atendimento, id_doenca) VALUES (3, '', 'Foi foda!', 2, 31);
INSERT INTO pep_owner.tb_antecedente_clinico_atendimento(id_antecedente_clinico_atendimento, descricao, observacao, id_atendimento) VALUES (4, 'Mal estar', 'Desculpa pra faltar no trabalho.', 2);
ALTER SEQUENCE pep_owner.antecedente_clinico_atendimento_sequence RESTART WITH 5;

--antecedentes cirurgicos
INSERT INTO pep_owner.tb_antecedente_cirurgico_atendimento(id_antecedente_cirurgico_atendimento, descricao, observacao, id_atendimento, id_procedimento) VALUES (1, null, 'Deu boa!', 1, 1);
INSERT INTO pep_owner.tb_antecedente_cirurgico_atendimento(id_antecedente_cirurgico_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Facectomia', 'Nem sei o que é isso!', 1);
INSERT INTO pep_owner.tb_antecedente_cirurgico_atendimento(id_antecedente_cirurgico_atendimento, descricao, observacao, id_atendimento) VALUES (3, 'Retinopexia', 'Muito menos isso...', 2);
INSERT INTO pep_owner.tb_antecedente_cirurgico_atendimento(id_antecedente_cirurgico_atendimento, descricao, observacao, id_atendimento, id_procedimento) VALUES (4, null, 'Nem doeu!', 2, 2);
ALTER SEQUENCE pep_owner.antecedente_cirurgico_atendimento_sequence RESTART WITH 5;

--habitos
INSERT INTO pep_owner.tb_habito_atendimento(id_habito_atendimento, descricao, observacao, id_atendimento, id_habito) VALUES (1, '', 'de vez em quando', 1, 1);
INSERT INTO pep_owner.tb_habito_atendimento(id_habito_atendimento, descricao, observacao, id_atendimento, id_habito) VALUES (2, '', '', 1, 2);
INSERT INTO pep_owner.tb_habito_atendimento(id_habito_atendimento, descricao, observacao, id_atendimento, id_habito) VALUES (3, '', 'bla bla bla', 2, 3);
INSERT INTO pep_owner.tb_habito_atendimento(id_habito_atendimento, descricao, observacao, id_atendimento, id_habito) VALUES (4, '', '', 2, 4);
ALTER SEQUENCE pep_owner.habito_atendimento_sequence RESTART WITH 5;

--alergias
INSERT INTO pep_owner.tb_alergia_atendimento(id_alergia_atendimento, descricao, observacao, id_atendimento, id_alergia) VALUES (1, '', 'observação', 1, 1);
INSERT INTO pep_owner.tb_alergia_atendimento(id_alergia_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Nozes', '', 1);
INSERT INTO pep_owner.tb_alergia_atendimento(id_alergia_atendimento, descricao, observacao, id_atendimento, id_alergia) VALUES (3, '', 'outra alergia', 2, 3);
INSERT INTO pep_owner.tb_alergia_atendimento(id_alergia_atendimento, descricao, observacao, id_atendimento) VALUES (4, 'Leite com pêra', '', 2);
ALTER SEQUENCE pep_owner.alergia_atendimento_sequence RESTART WITH 5;

--vacinas
INSERT INTO pep_owner.tb_vacina_atendimento(id_vacina_atendimento, descricao, observacao, id_atendimento, id_vacina) VALUES (1, '', 'teste', 1, 1);
INSERT INTO pep_owner.tb_vacina_atendimento(id_vacina_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Injeção', '', 1);
INSERT INTO pep_owner.tb_vacina_atendimento(id_vacina_atendimento, descricao, observacao, id_atendimento, id_vacina) VALUES (3, '', 'outra vacina', 2, 3);
INSERT INTO pep_owner.tb_vacina_atendimento(id_vacina_atendimento, descricao, observacao, id_atendimento) VALUES (4, 'Vacina de mentira', '', 2);
ALTER SEQUENCE pep_owner.vacina_atendimento_sequence RESTART WITH 5;

--antecedentes familiares
INSERT INTO pep_owner.tb_antecedente_familiar_atendimento(id_antecedente_familiar_atendimento, descricao, observacao, id_atendimento, id_doenca) VALUES (1, '', 'teste', 1, 60);
INSERT INTO pep_owner.tb_antecedente_familiar_atendimento(id_antecedente_familiar_atendimento, descricao, observacao, id_atendimento) VALUES (2, 'Sarnice', '', 1);
INSERT INTO pep_owner.tb_antecedente_familiar_atendimento(id_antecedente_familiar_atendimento, descricao, observacao, id_atendimento, id_doenca) VALUES (3, '', 'outro antecedente familiar', 2, 80);
INSERT INTO pep_owner.tb_antecedente_familiar_atendimento(id_antecedente_familiar_atendimento, descricao, observacao, id_atendimento) VALUES (4, 'Outro antecedente familiar', 'só pra constar', 2);
ALTER SEQUENCE pep_owner.antecedente_familiar_atendimento_sequence RESTART WITH 5;

--exame fisico
INSERT INTO pep_owner.tb_exame_fisico_atendimento(id_exame_fisico_atendimento, aspecto_geral, id_atendimento) VALUES (1, 'teste aspecto 1', 1);
INSERT INTO pep_owner.tb_exame_fisico_atendimento(id_exame_fisico_atendimento, peso, altura, superficie_corporea, temperatura, imc, circunferencia_abdominal, pressao_sentado_pas, pressao_sentado_pad, pressao_deitado_pas, pressao_deitado_pad, aspecto_geral, mucosas, olhos_face, pescoco, sistema_cardiorespiratorio, pele_dermatologico, abdome_superior, abdome_inferior, membros, neurologico, observacoes, id_atendimento) VALUES (2, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100.1, 'teste aspecto 2', 'teste mucosas', 'teste olhos face', null, 'tá malexo', 'virado no sapo seco', 'tá bão', 'tá ruim', 'grandes', 'noiado', 'minha observaçãozinha', 2);
ALTER SEQUENCE pep_owner.exame_fisico_atendimento_sequence RESTART WITH 3;

--doenças diagnosticadas
INSERT INTO pep_owner.tb_doenca_diagnosticada_atendimento(id_doenca_diagnosticada_atendimento, descricao, observacao, status_doenca, id_atendimento, id_doenca) VALUES (1, '', 'Essa é fudida!', 'CONFIRMADO', 1, 10);
INSERT INTO pep_owner.tb_doenca_diagnosticada_atendimento(id_doenca_diagnosticada_atendimento, descricao, observacao, status_doenca, id_atendimento) VALUES (2, 'Churrio', 'Foi de deitar a macega!', 'CONFIRMADO', 1);
INSERT INTO pep_owner.tb_doenca_diagnosticada_atendimento(id_doenca_diagnosticada_atendimento, descricao, observacao, status_doenca, id_atendimento, id_doenca) VALUES (3, '', 'Foi de peidar lavareda!', 'CONFIRMADO', 2, 20);
INSERT INTO pep_owner.tb_doenca_diagnosticada_atendimento(id_doenca_diagnosticada_atendimento, descricao, observacao, status_doenca, id_atendimento) VALUES (4, 'Amnésia', 'Foi é? Que merda', 'CONFIRMADO', 2);
ALTER SEQUENCE pep_owner.doenca_diagnosticada_atendimento_sequence RESTART WITH 5;
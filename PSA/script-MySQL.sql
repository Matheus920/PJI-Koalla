create database pji;

use pji;

create table categoria (
    id int not null primary key auto_increment,
    nome varchar(255) default null
);

create table login (
    id int not null primary key auto_increment,
    email varchar(255) default null,
    senha varchar(255) default null,
    privilegio int(2) default 0
);


create table criterio (
    id int not null primary key auto_increment,
    nome varchar(255) default null,
    descricao varchar(1024) default null
);

create table usuario (
    id int not null primary key auto_increment,
    id_login int not null,
    nome varchar(255) default null,
    instituicao varchar(255) default null,
    data_nascimento timestamp default current_timestamp,
    imagem varchar(255) default null,
    palestrante boolean default false,
    constraint usuario_login foreign key (id_login)
        references login (id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table comite (
    id int not null primary key auto_increment,
    id_login int not null,
    prontuario varchar(255) default null,
    data_nascimento date,
    funcao varchar(255) default null,
    imagem varchar(255) default null,
    nome varchar(255) default null,
    constraint comite_login foreign key (id_login)
        references login (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table professor (
    id int not null primary key auto_increment,
    id_login int not null,
    nome varchar(255) default null,
    prontuario varchar(255) default null,
    data_nascimento timestamp default current_timestamp,
    area varchar(255) default null,
    avaliador boolean default false,
    constraint professor_login foreign key (id_login)
        references login (id) ON DELETE CASCADE ON UPDATE CASCADE
);



create table evento (
    id int not null primary key auto_increment,
    titulo varchar(255) default null,
    resumo varchar(2048) default null,
    descricao varchar(1024) default null,
    data_inicio timestamp default now(),
    duracao int(4) default 0,
    `local` varchar(255) default null,
    capacidade int(4) default 0,
    `status` boolean default false,
    quantidade_palestrantes int(3) default 0,
    id_comite int not null,
    
    constraint fk_evento_comite foreign key (id_comite)
    references comite(id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table eventos_avaliadores (
    id int not null primary key auto_increment,
    id_professor int not null,
    id_evento int not null,
    constraint fk_eventos_avaliadores_professor foreign key (id_professor)
        references professor (id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint fk_eventos_avaliadores_evento foreign key (id_evento)
        references evento (id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table eventos_categorias (
    id int not null primary key auto_increment,
    id_categoria int not null,
    id_evento int not null,
    constraint fk_eventos_categorias_evento foreign key (id_evento)
        references evento (id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint fk_eventos_avaliadores_categoria foreign key (id_categoria)
        references categoria (id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table eventos_criterios (
    id int not null primary key auto_increment,
    id_criterio int not null,
    id_evento int not null,
    peso int default 0,
    constraint fk_eventos_criterios_criterio foreign key (id_criterio)
        references criterio (id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint fk_eventos_criterios_evento foreign key (id_evento)
        references evento (id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table especializacao (
    id int not null primary key auto_increment,
    id_professor int not null,
    id_categoria int not null,
    constraint fk_especializacao_professor foreign key (id_professor)
        references professor (id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint fk_especializacao_categoria foreign key (id_categoria)
        references categoria (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table artigo (
    id int not null primary key auto_increment,
    titulo varchar(255) default null,
    caminho varchar(255) default null,
    id_evento int not null,
    palestrante boolean default false,
    id_usuario int not null,
    aprovado boolean default false,
    
    constraint fk_artigo_usuario foreign key (id_usuario)
    references usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    
    constraint fk_artigo_evento foreign key (id_evento)
        references evento (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table avaliacao (
    id int not null primary key auto_increment,
    resultado boolean default false,
    mensagem varchar(1024) default false,
    id_avaliador int not null,
    id_artigo int not null,
    constraint fk_avaliacao_avaliador foreign key (id_avaliador)
        references professor (id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint fk_avaliacao_artigo foreign key (id_artigo) references
    artigo(id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table convidados(
id int not null primary key auto_increment,
nome varchar(255) not null,
id_evento int not null,

constraint fk_convidados_evento foreign key (id_evento)
references evento(id) ON DELETE CASCADE ON UPDATE CASCADE);

create table eventos_usuarios(
id int not null primary key auto_increment,
id_usuario int not null,
id_evento int not null,


constraint fk_evento_usuario foreign key (id_usuario) references usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
constraint fk_evento_usuario_evento foreign key (id_evento) references evento(id) ON DELETE CASCADE ON UPDATE CASCADE);

create table palestras(
id int primary key not null auto_increment,
id_evento int not null,
id_usuario int not null,



constraint fk_evento_palestrante foreign key (id_usuario) references usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
constraint fk_evento_palestrante_evento foreign key (id_evento) references evento(id) ON DELETE CASCADE ON UPDATE CASCADE);

-- INSERTS

insert into categoria(nome) values ('Nutrição'),
('Farmácia'),
('Engenharia de Software'),
('Banco de Dados'),
('Ecologia'),
('Meteorologia'),
('Geologia'),
('Física Nuclear'),
('Hardware'),
('Arquitetura de Sistemas de Computação'),
('Desenvolvimento de Sistemas'),
('Zoologia'),
('Hidrologia'),
('Robotização'),
('Ergonomia'),
('Veículos de Transportes'),
('História da Arte'),
('Música'),
('Cinema'),
('Sociologia da Educação'),
('Arqueologia'),
('História do Brasil'),
('Jornalismo'),
('Arquitetura e Urbanismo'),
('Bioquímica');

select * from categoria;

insert into criterio(nome, descricao) values
('Título', 'O título reflete claramente e de modo suficiente o conteúdo do artigo?'),
('Resumo', 'O resumo contém introdução ao problema, objetivos, método e principais resultados?'),
('Base teórica', 'As bases teóricas estão claramente especificadas? Descreve o estado atual de conhecimento sobre o assunto? Oferece sustentação para o estudo?'),
('Ortografia e gramática', 'A obediência das normas ortográficas e de gramática.'), 
('Sequência Lógica', 'A obediência de uma sequência de lógica'), 
('Coerência Interna', 'O texto apresenta uma coesão.'), 
('Clareza na Argumentação', 'A ideia é bem apresentada?'), 
('Ausência de Ambiguidades', 'A ideia não pode se contradizer.'),
('Linguagem Científica', 'As termologias utilizadas são adequadas.'),
('Referências', 'As referências necessárias foram apresentadas? Mantém correspondência com as referências citadas no corpo do manuscrito?  Indicam familiaridade com o tema e área de conhecimento?'),
('Tabelas e figuras', 'São necessárias e pertinentes ao texto? Foram discutidas no texto?'),
('Análise dos resultados', 'O plano de análise adotado é apropriado e consistente com as teorias/premissas/hipóteses adotadas? As análises foram conduzidas de forma apropriada? Os resultados foram apresentados de forma apropriada?');

insert into login(email, senha, privilegio) values ('comite', 'comite', 4),
('admin', 'admin', 0),
('avaliador', 'avaliador', 2),
('usuario', 'usuario', 1),
('cleber@gmail.com', 'cleber', 4),
('mauricio@gmail.com', 'mauricio', 4),
('anafranda@gmail.com', 'anafranda', 4),
('shemilly@gmail.com', 'shemilly', 4),
('juliana@gmail.com', 'juliana', 4),
('luiza@gmail.com', 'luiza', 1),
('ricardo@gmail.com', 'ricardo', 1),
('john@gmail.com', 'john', 1),
('selena@gmail.com', 'selena', 1),
('waldecir@gmail.com', 'waldecir', 1),
('fugencio@gmail.com', 'fugencio', 2),
('leonor@gmail.com', 'leonor', 2),
('simao@gmail.com', 'simao', 2),
('debora@gmail.com', 'debora', 2),
('camila@gmail.com', 'camila', 2),
('rodrigo@gmail.com', 'rodrigo', 2),
('britinei@gmail.com', 'britinei', 2),
('claudemir@gmail.com', 'claudemir', 2),
('teodoro@gmail.com', 'teodoro', 2),
('hugo@gmail.com', 'hugo', 2);


insert into comite(prontuario, data_nascimento, funcao, imagem, nome, id_login) values ('5457854', now(), 'Diretor', 'teste', 'Cléber Soares', 5),
('7233521', now(), 'Presidente', 'teste', 'Maurício Moreira', 6),
('5544332', now(), 'Professora', 'teste', 'Anafranda Suelly', 7),
('3421321', now(), 'Conselheira', 'teste', 'Shemilly Kelton', 8),
('3234443', now(), 'Conselheira', 'teste', 'Juliana Xupulito', 9);

insert into usuario(id_login, instituicao, palestrante, nome, imagem, data_nascimento) values
(10, 'IFSP', true, 'Luiza Camaleão', 'teste', now()), 
(11, 'ETESP', true, 'Ricardo Camões', 'teste', now()),
(12, 'UNICAMP', true, 'John Schemetzes', 'teste', now()),
(13, 'IFSP', true, 'Selena Antônio', 'teste', now()),
(14, 'IFSP', true, 'Waldecir de Matos', 'teste', now());

insert into professor(nome, prontuario, data_nascimento, area, avaliador, id_login) values
('Fugêncio Batista', '2454540', now(), 'Matemática', true, 15),
('Leonor Almeida', '132541', now(), 'Sociologia', true, 16),
('Simão Bolivão', '9233021', now(), 'Matemática', true, 17),
('Débora Leite', '1023521', now(), 'Química', true, 18),
('Camila Safra', '9933521', now(), 'Biologia', true, 19),
('Rodrigo Takeda', '3293924', now(), 'História', false, 20),
('Britinéi Omeda', '1399332', now(), 'Sociologia', false, 21),
('Claudemir Júnior', '948394', now(), 'História', false, 22),
('Teodoro da Silva', '1159593', now(), 'Química', false, 23),
('Hugo Alves', '1023211', now(), 'Linguagem de Programação', false, 24);


insert into evento(titulo, resumo, descricao, data_inicio, duracao, `local`, capacidade, `status`, quantidade_palestrantes, id_comite) values
('Aviação Moderna', 'Aviação é a atividade científico-tecnológica, econômica e de transportes que tem por objetivo o estudo, o desenvolvimento e a exploração (utilização, com ou sem fins comerciais) dos aeródinos. É comum as pessoas incorretamente associarem "aviação" como sinônimo de aeronáutica, embora na realidade a aviação seja apenas um dos dois ramos da aeronáutica, sendo o outro ramo a aerostação.
Atualmente, a aviação pode ser considerada uma indústria global que pertence e é operada largamente pelas empresas do setor. Essa indústria é uma verdadeira rede que congrega atividades tais como engenharia de design, manufatura, vendas, operações de linhas aéreas, serviços aos clientes, manutenção, finanças, leasing, seguros, publicidade, propaganda, mídia etc. Também está presente uma forte componente legal, que inclui legislação e regulamentos nacionais que se interconectam a tratados internacionais, e tudo em um contínuo processo de revisão e atualização à medida que essa indústria continua crescendo e apresentando cada vez mais e maiores demandas.',  
'Uma calorosa discussão sobre os moldes da aviação moderna', '2017-11-23 03:21', '20', 'IVO', 20, true, 5, 2),

('Vida Animal', 'Animalia, Animal ou Metazoa[1] é um reino biológico composto por seres vivos pluricelulares, Eucariontes, heterotróficos, cujas células formam tecidos biológicos, com capacidade de responder ao ambiente (possuem tecido nervoso) que os envolve ou, por outras palavras, pelos animais.[2]
A maioria dos animais possui um plano corporal que se determina à medida que se tornam maduros e, exceto em animais que metamorfoseiam, esse plano corporal é estabelecido desde cedo em sua ontogenia quando embriões. O estudo científico dos animais é chamado zoologia, que tradicionalmente estudava, não só os seres vivos com as características descritas acima, mas também os protozoários. Como resultado de estudos filogenéticos, consideram-se os Protista como um grupo separado dos animais.[3]
Coloquialmente, o termo "animal" é frequentemente utilizado para referir-se a todos os animais diferentes dos humanos, e raramente para referir-se a animais não classificados como Metazoários. A palavra "animal" é derivada do latim anima, no sentido de fôlego vital, e entrou na língua portuguesa através da palavra animalis. Animalia é seu plural.[4] Os únicos animais que não são considerados metazoários compreendem os três filos de posição sistemática incerta, por vezes agrupados no sub-reino Agnotozoa (que significa "animais desconhecidos"), nos filos Rhombozoa, Orthonectida e Placozoa.[5]', 
'A importância dos animais no nosso cotidiano', '2017-12-02 11:20', '08', 'SALA 3', 20, true, 2, 2),

('Tecnologia no Mundo Moderno', 'Tecnologia (do grego τεχνη — "técnica, arte, ofício" e λογια — "estudo") é um termo que envolve o conhecimento técnico e científico e a aplicação deste conhecimento através de sua transformação no uso de ferramentas, processos e materiais criados e utilizados a partir de tal conhecimento.', 'A tecnologia ao nosso redor como ferramenta de estudo.', '2017-11-12 19:20', '400', 'SALA 4', 15, true, 6, 1),

('O Esporte é Saúde', 'Os exercícios físicos praticados desde a infância proporcionam ao individuo benefícios incríveis no corpo e na mente! O corpo se beneficia diariamente, pois a atividade física fortalece o tônus muscular, ajuda na flexibilidade e no fortalecimento dos ossos e articulações.', 'Os esportes são essenciais. Mas qual o melhor ou mais utilizado atualmente? Descubra tudo isso nesse reconfortante simpósio.', '2017-11-15 18:20', '120', 'SAGUÃO', 14, true, 7, 2),

('A Biomedicina é o futuro', 'Biomedicina é uma área que atua no campo de interface entre Biologia e Medicina, voltada para a pesquisa das doenças humanas, seus fatores ambientais e ecoepidemiológicos, com o objetivo de compreender as causas, efeitos, mecanismos e desenvolver e/ou aprimorar diagnósticos e tratamentos. O(A) Biomédico(a) identifica, classifica e estuda os agentes causadores de enfermidades e desenvolve ou aprimora meios de combatê-los. Trabalha em Hospitais, Universidades, Faculdades, Centros de pesquisas, Laboratórios Públicos e Privados, órgãos públicos de saúde entre outras gerenciando, ensinando, realizando pesquisas e testes em parceria com Bioquímicos, Biólogos, Médicos, Farmacêuticos e demais profissionais da saúde. Muitas disciplinas do curso de graduação abrangem as áreas de ciências biológicas e da saúde.', 
'A biomedicina pode ser uma importante arma para a nossa nação futura, não perca mais tempo sem ter um conhecimento ideal do assunto.', '2017-11-23 15:00', '180', 'SAGUÃO', 11, true, 7, 5),

('Linguagens de Programação', 'Uma linguagem de programação é um método padronizado para comunicar instruções para um computador.[1] É um conjunto de regras sintáticas e semânticas usadas para definir um programa de computador.[2][Nota 1] Permite que um programador especifique precisamente sobre quais dados um computador vai atuar, como estes dados serão armazenados ou transmitidos e quais ações devem ser tomadas sob várias circunstâncias. Linguagens de programação podem ser usadas para expressar algoritmos com precisão.
O conjunto de palavras (lexemas classificados em tokens), compostos de acordo com essas regras, constituem o código fonte de um software.[3] Esse código fonte é depois traduzido para código de máquina, que é executado pelo processador.[3]', 
'Um debate com os melhores sobre qual a melhor linguagem de programação para cada uma das situações do mercado..', '2017-11-04 15:00', '180', 'SAGUÃO', 10, false, 2, 2),

('Comunicação e Sociologia', 'Os resultados da pesquisa sociológica não são de interesse apenas de sociólogos(as). Cobrindo todas as áreas do convívio humano — desde as relações na família até a organização das grandes empresas, o papel da política na sociedade ou o comportamento religioso —, a sociologia pode vir a interessar, em diferentes graus de intensidade, a diversas outras áreas do saber. Entretanto, os maiores interessados na produção e sistematização do conhecimento sociológico atualmente são o Estado, normalmente o principal financiador da pesquisa desta disciplina científica, e a sociedade civil organizada (movimentos sociais por exemplo). Assim como toda ciência, a sociologia pretende explicar a totalidade do seu universo de pesquisa. Ainda que esta tarefa não seja objetivamente alcançável, é tarefa da sociologia transformar as malhas da rede com a qual ela capta a realidade social cada vez mais estreitas. Por essa razão, o conhecimento sociológico, através dos seus conceitos, teorias e métodos, pode constituir para as pessoas um excelente instrumento de compreensão das situações com que se defrontam na vida cotidiana, das suas múltiplas relações sociais e, consequentemente, de si mesmas como seres inevitavelmente sociais.', 
'A Sociologia atuando diretamente como uma área de comunicação pode ser uma efetiva ferramenta. Descubra como!', '2017-03-20 18:20', '200', 'SALA 50', 40, false, 3, 3);


insert into eventos_categorias(id_evento, id_categoria) values (1, 16),
(1, 14),
(2, 12),
(3, 3),
(3, 10),
(3, 11),
(4, 1),
(5, 25),
(6, 4),
(6, 3),
(7, 20);

insert into artigo(titulo, caminho, id_evento, palestrante, id_usuario, aprovado) values
('Artigo1', 'C:\\Users\\mathe\\Documents\\AC_MER.pdf', 6, false, 2, true),
('Artigo2', 'C:\\Users\\mathe\\Documents\\Atividade #10-1650084-MatheusMoreiraDaSilva.pdf', 6, false, 1, true);

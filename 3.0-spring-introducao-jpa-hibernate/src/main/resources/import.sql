INSERT INTO cozinha (id,nome) VALUES (1,'Tailandesa');
INSERT INTO cozinha (id,nome) VALUES (2,'Indiana');

INSERT INTO restaurante (nome,taxa_frete,cozinha_id) VALUES ('Bar e Restuante Gustavo', 10.65, 1);
INSERT INTO restaurante (nome,taxa_frete,cozinha_id) VALUES ('Petisqueira', 22.65, 2);

INSERT INTO forma_pagamento (descricao) VALUES ('DINHEIRO');

INSERT INTO permissao (nome,descricao) VALUES ('CONSULTAR_PRODUTOS', 'Permite consultar produtos.');

INSERT INTO estado (id,nome) VALUES (1, 'MG');

INSERT INTO cidade (nome,estado_id) VALUES ('Belo Horizonte', 1);
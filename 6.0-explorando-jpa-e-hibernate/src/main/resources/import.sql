INSERT INTO cozinha (id,nome) VALUES (1,'Tailandesa');
INSERT INTO cozinha (id,nome) VALUES (2,'Indiana');

INSERT INTO restaurante (nome,taxa_frete,cozinha_id) VALUES ('Bar e Restuante Gustavo', 10.65, 1);
INSERT INTO restaurante (nome,taxa_frete,cozinha_id) VALUES ('Petisqueira', 22.65, 2);
INSERT INTO restaurante (nome,taxa_frete,cozinha_id) VALUES ('Mineirense', 22.65, 2);

INSERT INTO forma_pagamento (id,descricao) VALUES (1, 'Cartão de crédito'), (2, 'Cartão de débito'), (3, 'Dinheiro');

INSERT INTO permissao (nome,descricao) VALUES ('CONSULTAR_PRODUTOS', 'Permite consultar produtos.');

INSERT INTO estado (id,nome) VALUES (1, 'MG');

INSERT INTO cidade (nome,estado_id) VALUES ('Belo Horizonte', 1);

INSERT INTO restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) VALUES (1,1), (1,2), (1,3), (2,2), (2,3), (3,3);
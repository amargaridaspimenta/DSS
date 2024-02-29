USE oficina;

INSERT INTO Especializacao VALUES ('Eletrico'), ('CombustaoGasoleo'), ('CombustaoGasolina');

INSERT INTO postoTrabalho VALUES (1), (2), (3), (4);

INSERT INTO Servico VALUES
                        ('Substituicao da bateria de arranque', 'Combustao', 10, 8.50),
                        ('Substituicao do conversor catalitico', 'Combustao', 20, 17.00),
                        ('Substituicao do filtro do oleo', 'Combustao', 25, 14.50),
                        ('Mundanca do oleo do motor', 'Combustao', 15, 9.00),
                        ('Substituicao do filtro do combustivel', 'Combustao', 19, 10.60),
                        ('Substituicao das velas de incandescencia', 'CombustaoGasoleo', 15, 17.90),
                        ('Substituicao do filtro de particulas', 'CombustaoGasoleo', 35, 20.60),
                        ('Substituicao das velas de ignicao', 'CombustaoGasolina', 20, 30.50),
                        ('Substituicao da valvula', 'CombustaoGasolina', 20, 56.30),
                        ('Substituicao da bateria', 'Eletrico', 50, 200.00),
                        ('Avaliacao de desempenho', 'Eletrico', 5, 20.00),
                        ('Limpeza', 'Geral', '25', 50.20),
                        ('Substituicao do filtro do ar', 'Geral', 15, 15.00),
                        ('Substituicao dos pneus', 'Geral', 10, 10.00),
                        ('Substituicao dos injetores', 'Geral', 9, 60.25),
                        ('Calibragem das rodas', 'Geral', 8, 5.00),
                        ('Substituicao dos calços dos travoes', 'Geral', 10, 60.00),
                        ('Alinhamento da direcao', 'Geral', 30, 6.00),
                        ('Mudanca do oleo dos travoes', 'Geral', 20, 5.10),
                        ('Checkup', 'Checkup', 20, 30.00);

INSERT INTO postoEspecializacao VALUES
                                    (1, 'Eletrico'),
                                    (2, 'CombustaoGasoleo'),
                                    (3, 'CombustaoGasolina'),
                                    (4, 'CombustaoGasoleo'),
                                    (4, 'CombustaoGasolina'),
                                    (4, 'Eletrico');


INSERT INTO Mecanico VALUES
                         ('1','Pedro',9,18, 1),
                         ('2','Margarida',9,18, 2),
                         ('3','Tomas',9,18, 3),
                         ('4','Ines',9,18, 4);


INSERT INTO mecanicoEspecializacao VALUES
                                       ('1', 'Eletrico'),
                                       ('1', 'CombustaoGasolina'),
                                       ('2', 'CombustaoGasoleo'),
                                       ('2', 'CombustaoGasolina'),
                                       ('2', 'Eletrico'),
                                       ('3', 'CombustaoGasolina'),
                                       ('3', 'Eletrico'),
                                       ('4', 'CombustaoGasoleo'),
                                       ('4', 'CombustaoGasolina'),
                                       ('4', 'Eletrico');

INSERT INTO Cliente VALUES
                        ('255596480','Luisa','Rua Rodrigues Sampaio','911234567','luisagmail'),
                        ('248591312','Jose','Rua Maria Costa','914678549','Josegmail'),
                        ('276058794','Mario','Rua Margarida Primenta','914673649','Mariogmail'),
                        ('276490376','Raquel','Rua tomas pinto','934676449','Raquelgmail'),
                        ('276930147','Dinis','Rua Afonso Costa','964694549','Dinisgmail');

INSERT INTO Veiculo VALUES
                        ('00QR45', 'Renault','Eletrico'),
                        ('RD22DD', 'RangeRover', 'Hibrido'),
                        ('39PE60', 'Ferrari', 'Combustao'),
                        ('20HG56', 'Opel', 'Combustao'),
                        ('AE24RL', 'Mercedes', 'Combustao');

INSERT INTO ClienteVeiculo VALUES
                               ('243567092', 'AE24RL'),
                               ('247591678', '00QR45'),
                               ('278453912', 'RD22DD'),
                               ('259439172', '39PE60'),
                               ('234789123', '20HG56');

INSERT INTO VeiculoEletrico VALUES
    ('00QR45', 'Niquel');

INSERT INTO VeiculoCombustao (Matricula, Motor) VALUES
                                                    ('AE24RL', 'Gasolina'),
                                                    ('20HG56', 'Gasoleo'),
                                                    ('39PE60', 'Gasoleo');

INSERT INTO VeiculoHibrido (Matricula, Motor, Bateria) VALUES
    ('RD22DD', 'Gasolina', 'Litio');

INSERT INTO PostoServico VALUES
                             (2, 'Substituicao da bateria de arranque'),
                             (3, 'Substituicao da bateria de arranque'),
                             (4, 'Substituicao da bateria de arranque'),
                             (2, 'Substituicao do conversor catalitico'),
                             (3, 'Substituição do conversor catalitico'),
                             (4, 'Substituicao do conversor catalitico'),
                             (2, 'Substituicao do filtro do oleo'),
                             (3, 'Substituicao do filtro do oleo'),
                             (4, 'Substituicao do filtro do oleo'),
                             (2, 'Mundança do oleo do motor'),
                             (3, 'Mundança do oleo do motor'),
                             (4, 'Mundança do oleo do motor'),
                             (2, 'Substituicao do filtro do combustivel'),
                             (3, 'Substituicao do filtro do combustivel'),
                             (4, 'Substituicao do filtro do combustivel'),
                             (2, 'Substituicao das velas de incandescencia'),
                             (4, 'Substituicao das velas de incandescencia'),
                             (2, 'Substituicao do filtro de particulas'),
                             (4, 'Substituicao do filtro de particulas'),
                             (3,'Substituicao das velas de ignicao'),
                             (4,'Substituicao das velas de ignicao'),
                             (3, 'Substituicao da valvula'),
                             (4, 'Substituicao da valvula'),
                             (1, 'Substituicao da bateria'),
                             (4, 'Substituicao da bateria'),
                             (1, 'Avaliacao de desempenho'),
                             (4, 'Avaliacao de desempenho'),
                             (1, 'Limpeza'),
                             (2, 'Limpeza'),
                             (3, 'Limpeza'),
                             (4, 'Limpeza'),
                             (1, 'Substituicao do filtro do ar'),
                             (2, 'Substituicao do filtro do ar'),
                             (3, 'Substituicao do filtro do ar'),
                             (4, 'Substituicao do filtro do ar'),
                             (1, 'Substituicao dos pneus'),
                             (2, 'Substituicao dos pneus'),
                             (3, 'Substituicao dos pneus'),
                             (4, 'Substituicao dos pneus'),
                             (1, 'Substituicao dos injetores'),
                             (2, 'Substituicao dos injetores'),
                             (3, 'Substituicao dos injetores'),
                             (4, 'Substituicao dos injetores'),
                             (1, 'Calibragem das rodas'),
                             (2, 'Calibragem das rodas'),
                             (3, 'Calibragem das rodas'),
                             (4, 'Calibragem das rodas'),
                             (1, 'Substituicao dos calços dos travoes'),
                             (2, 'Substituicao dos calços dos travoes'),
                             (3, 'Substituicao dos calços dos travoes'),
                             (4, 'Substituicao dos calços dos travoes'),
                             (1, 'Alinhamento da direcao'),
                             (2, 'Alinhamento da direcao'),
                             (3, 'Alinhamento da direcao'),
                             (4, 'Alinhamento da direcao'),
                             (1, 'Mudança do oleo dos travoes'),
                             (2, 'Mudança do oleo dos travoes'),
                             (3, 'Mudança do oleo dos travoes'),
                             (4, 'Mudança do oleo dos travoes'),
                             (1, 'Checkup'),
                             (2, 'Checkup'),
                             (3, 'Checkup'),
                             (4, 'Checkup');

INSERT INTO Pedido (Estado,Inicio,Fim,Cliente,Posto,Veiculo,Servico) VALUES
    ('PorRealizar', '2024-01-02 09:00:00', '2024-01-02 09:00:00', '243567092', 3, 'AE24RL', 'Substituição do conversor catalítico');

INSERT INTO Notificacao (lida,Mensagem,Cliente) VALUES
    (TRUE, 'Realizado com sucesso', '243567092');
    
    
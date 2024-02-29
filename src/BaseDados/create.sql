
CREATE DATABASE IF NOT EXISTS oficina;

USE oficina;

-- Criação da tabela postoTrabalho
Create table if not exists postoTrabalho(
                                            `idPosto` INT NOT NULL,
                                            PRIMARY KEY (`idPosto`)
);

-- criação da tabela serviço

CREATE TABLE IF NOT EXISTS servico(
                                      nome varchar(128) NOT NULL,
                                      Tipo VARCHAR(128) NOT NULL,
                                      Duracao INT NOT NULL,
                                      Preco DOUBLE NOT NULL,
                                      PRIMARY KEY (nome),
                                      CHECK (Tipo = 'Eletrico' OR Tipo = 'Combustao' OR Tipo = 'Geral' OR Tipo = 'Checkup' OR Tipo = 'CombustaoGasoleo' OR Tipo = 'CombustaoGasolina'),
                                      CHECK (Preco > 0)
);

-- Criação da tabela mecanico.
Create table if not exists mecanico(
                                       `idMecanico` INT NOT NULL,
                                       `nome` VARCHAR(200),
                                       `inicio` TIME NOT NULL,
                                       `fim` TIME NOT NULL,
                                       `idPosto` int NOT NULL,
                                       PRIMARY KEY (`idMecanico`),
                                       FOREIGN KEY (idPosto) REFERENCES postoTrabalho (idPosto)
);

-- Criação da tabela cliente.
Create table if not exists cliente(
                                      `NIF` VARCHAR(20) NOT NULL,
                                      `nome` VARCHAR(200) NOT NULL,
                                      `morada` VARCHAR(200) NOT NULL,
                                      `contacto` VARCHAR(20) NOT NULL,
                                      `email` VARCHAR(200) NOT NULL,
                                      PRIMARY KEY (`NIF`));

-- criação da tabela veiculo

CREATE TABLE IF NOT EXISTS veiculo(
                                      Matricula VARCHAR(128) NOT NULL,
                                      Marca VARCHAR(128) NOT NULL,
                                      Tipo VARCHAR(128) NOT NULL,
                                      PRIMARY KEY (Matricula),
                                      CHECK (Tipo = 'Eletrico' OR Tipo = 'Combustao' OR Tipo = 'Hibrido')
);

-- criação da tabela veiculo eletrico

CREATE TABLE IF NOT EXISTS veiculoEletrico(
                                              Matricula VARCHAR(128) NOT NULL,
                                              Bateria VARCHAR(128) NOT NULL,
                                              PRIMARY KEY (Matricula),
                                              FOREIGN KEY (Matricula) REFERENCES veiculo (Matricula),
                                              CHECK (Bateria = 'Litio' OR Bateria = 'Niquel')
);

-- criação da tabela veiculo a combustão

CREATE TABLE IF NOT EXISTS veiculoCombustao(
                                               Motor VARCHAR(128) NOT NULL,
                                               Matricula VARCHAR(128) NOT NULL,
                                               PRIMARY KEY (Matricula),
                                               FOREIGN KEY (Matricula) REFERENCES veiculo (Matricula),
                                               CHECK (Motor = 'Gasoleo' OR Motor = 'Gasolina')
);

-- criação da tabela veiculo hibrido

CREATE TABLE IF NOT EXISTS veiculoHibrido(
                                             Motor VARCHAR(128) NOT NULL,
                                             Matricula VARCHAR(128) NOT NULL,
                                             Bateria VARCHAR(128) NOT NULL,
                                             PRIMARY KEY (Matricula),
                                             FOREIGN KEY (Matricula) REFERENCES Veiculo (Matricula),
                                             CHECK (Motor = 'Gasoleo' OR Motor = 'Gasolina'),
                                             CHECK (Bateria = 'Litio' OR Bateria = 'Niquel')
);

-- criação da tabela especialização

CREATE TABLE IF NOT EXISTS especializacao(
                                             TipoEsp VARCHAR(128) NOT NULL,
                                             PRIMARY KEY (TipoEsp),
                                             CHECK (TipoEsp = 'Eletrico' OR TipoEsp = 'CombustaoGasoleo' OR TipoEsp = 'CombustaoGasolina')

);

CREATE TABLE IF NOT EXISTS clienteVeiculo(
                                             Cliente VARCHAR(128) NOT NULL,
                                             Veiculo VARCHAR(128) NOT NULL,
                                             PRIMARY KEY (Cliente,Veiculo),
                                             FOREIGN KEY (Cliente) REFERENCES cliente (Nif),
                                             FOREIGN KEY (Veiculo) REFERENCES veiculo (Matricula)
);

-- criação da relação mecanico-especialização

CREATE TABLE IF NOT EXISTS mecanicoEspecializacao(
                                                     idMecanico INT NOT NULL,
                                                     Especializacao VARCHAR(128) NOT NULL,
                                                     PRIMARY KEY (idMecanico,Especializacao),
                                                     FOREIGN KEY (idMecanico) REFERENCES mecanico (idMecanico),
                                                     FOREIGN KEY (Especializacao) REFERENCES Especializacao (TipoEsp)
);

-- criação da relação Posto-Especialização

CREATE TABLE IF NOT EXISTS postoEspecializacao(
                                                  idPosto INT NOT NULL,
                                                  Especializacao VARCHAR(128) NOT NULL,
                                                  PRIMARY KEY (idPosto,Especializacao),
                                                  FOREIGN KEY (idPosto) REFERENCES postoTrabalho(idPosto) ,
                                                  FOREIGN KEY (Especializacao) REFERENCES Especializacao (TipoEsp)
);

-- criação da relação Posto-Serviço

CREATE TABLE IF NOT EXISTS postoServico(
                                           idPosto INT NOT NULL,
                                           Servico varchar(128) NOT NULL,
                                           PRIMARY KEY (idPosto,Servico),
                                           FOREIGN KEY (idPosto) REFERENCES postoTrabalho (idPosto),
                                           FOREIGN KEY (Servico) REFERENCES servico (nome)
);

CREATE TABLE IF NOT EXISTS pedido(
                                     idPedido INT UNSIGNED AUTO_INCREMENT NOT NULL,
                                     estado VARCHAR(128) NOT NULL,
                                     inicio TIMESTAMP NOT NULL,
                                     fim TIMESTAMP NOT NULL,
                                     Cliente VARCHAR(128) NOT NULL,
                                     Posto INT NOT NULL,
                                     Veiculo VARCHAR(128) NOT NULL,
                                     Servico VARCHAR(128) NOT NULL,
                                     PRIMARY KEY (idPedido),
                                     FOREIGN KEY (Cliente) REFERENCES cliente (NIF),
                                     FOREIGN KEY (Posto) REFERENCES postotrabalho (idPosto),
                                     FOREIGN KEY (Veiculo) REFERENCES veiculo (matricula),
                                     FOREIGN KEY (Servico) REFERENCES servico (nome),
                                     CHECK (Estado = 'PorRealizar' OR Estado = 'ARealizar' OR Estado = 'Concluido' OR Estado = 'Incompleto')
);

CREATE TABLE IF NOT EXISTS Notificacao(
                                          idNot INT UNSIGNED AUTO_INCREMENT NOT NULL,
                                          lida BOOLEAN not null,
                                          Mensagem VARCHAR(128) NOT NULL,
                                          Cliente VARCHAR(128) NOT NULL,

                                          PRIMARY KEY (idNot),
                                          FOREIGN KEY (Cliente) REFERENCES cliente (NIF)
);
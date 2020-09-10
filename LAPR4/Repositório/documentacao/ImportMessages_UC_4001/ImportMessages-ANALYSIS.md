# UC 4001 - Import Messages #

## Pre-Condições ##
* Devem existir mensagens de máquinas no diretório especificado.

## Pós-Condições ##
* Devem estar carregadas em sistema todas as menssagens que estão na pasta do diretório.

## ANÁLISE ##
* O Identificador da máquina que consta na mensagem é o identificador de protocolo.
* As mensagens importadas serão as mesmas que o Simulador da Máquina irá gerar e colocar no diretório.
* Existem dois diretórios distintos: o diretório de entrada e o diretório para onde os ficheiros são motivos aquando da importação das mensagens (para posterior processamento).
* Numa primeira fase de importação será necessário saber quantos ficheiros existem para ler visto que cada thread irá se encarregar da leitura de um ficheiro.
* Na segunda fase onde todos os ficheiros adicionados ao diretório devem ser tratados, deve ser utilizada uma thread para cada um.

## REGRAS DE NEGÓCIO ##
* Este serviço deve corresponder a uma aplicação servidora usando threads em Java.
* Após importação, os ficheiros devem ser transferidos para um diretório de ficheiros processados.
* Ambos os diretórios devem ser definidos por configuração aquando da implantação do sistema.
* No mesmo diretório de entrada podem existir ficheiros com diferentes formatos e estruturas.
* Após serem importadas todas as mensagems que estão no diretório aquando da execução do programa, sempre que uma mensagem nova é adicionada ao diretório, esta também deve ser importada.
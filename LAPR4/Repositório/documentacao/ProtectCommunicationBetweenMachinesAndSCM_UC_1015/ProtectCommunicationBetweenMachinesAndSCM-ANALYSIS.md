# UC 1013 - Protect Communication Between SCM And Machines #

## Pre-Condições ##

* Devem existir máquinas no sistema para comunicar com o SCM.
* As comunicações entre as máquinas e o SCM devem estar funcionais, no entanto, desprotegidas.
* Devem ser criados os certificados para posteriormente ser possível a encriptação das ligações.

## Pós-Condições ##

* Todas as comunicações entre o simulador da máquina e o SCM ficarão seguras.

## ANÁLISE ##

* Os métodos de envio e receção de dados entre as máquinas e o SCM irão ser métodos da biblioteca SSL/TLS.
* O servidor irá ter uma chave privada, enquanto a chave pública irá ser incorporada no certificado SSL e partilhada pelos clientes.
* Através desta funcionalidade, as aplicações em JAVA e C têm totais garantias que apenas conseguem comunicar entre elas.

## REGRAS DE NEGÓCIO ##

* Aplicar SSL/TLS com autenticação através de certificados de chave pública.

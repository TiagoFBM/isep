# UC 1010 - Specify XSD

## **DESIGN**

Utilizar a estrutura base standard da aplicação baseada em camadas.

### ESTRUTURA GERAL

- Dado que o ficheiro XML exportado pelo sistema poderá conter várias coleções de vários tipos de elementos, será utilizada uma root - _fabrica_ - que possuirá várias coleções (e.g. produtos, matérias primas, ordens de produção, etc).
- Cada coleção possuirá vários elementos do tipo que constitui essa coleção (e.g. A coleção de produtos possui vários produtos).
- Para cada elemento simples constituinte de uma lista, serão adicionados atributos constituintes desse elemento e efetuadas validações semelhantes às validações efetuadas pelo sistema utilizando _restrictions_ e _patterns_ quando necessário.
- Nos casos em que um conceito engloba outro (e.g. O Produto possui Ficha de Produção), seriam utilizados sub-elementos (Ficha de Produção é um sub-elemento de Produto).

### ESPECIFICAÇÃO

![diagram.png](diagram.png)

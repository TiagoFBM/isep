# UC 1010 - Specify XSD #

## Pós-Condições ##
* Será possível validar os ficheiros XML gerados pelo sistema com a informação relativa ao chão de fábrica.

## ANÁLISE ##
* O ficheiro XSD só poderá validar ficheiros XML gerados pelo sistema pelo que esta funcionalidade (exportar dados do sistema para um ficheiro XML) deve estar implementada.
* O XSD deve validar as datas e os códigos de identificação.

## REGRAS DE NEGÓCIO ##
* O XSD deve contemplar toda a infomação subjacente ao chão de fábrica (e.g. produtos, matérias-primas, máquinas, linhas de produção, categorias, ordens de produção, fichas de produção, lotes, consumos reais e efetivos, estornos, desvios, tempos de produção,  entre outros).
* Devem ser contemplada a possibilidade de para alguma informação (e.g. consumos, estornos, produção) obter totais agregados (e.g. por depósito, por matéria-prima).
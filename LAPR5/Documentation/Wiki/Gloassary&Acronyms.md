# Glossary and Acronyms

No âmbito simplificado deste projeto utilizaremos as seguintes definições:

* **Nó** - Um nó é um ponto da rede de transportes com importância para o processo de planeamento. Exemplos de nós são estações de recolha, términos, e paragens ao público.
* **Estação de recolha** - Uma estação de recolha é um local (normalmente pertencendo à empresa) onde é feito o estacionamento das viaturas que não estão a ser utilizadas.
* **Ponto de Rendição** - Um ponto de rendição é um nó onde é possível render tripulações. As estações de recolha são implicitamente consideradas pontos de rendição.
* **Tempo de deslocação** - Tempo que uma tripulação necessita para se deslocar de um ponto de rendição para outro.
* **Segmento de rede** – ligação direta entre dois nós, ex, Paredes-Cete.
* **Percurso** - Um percurso é um trajeto correspondendo a uma sequência de nós, ex., Paredes -> Cete -> Parada-> Recarei ->Aguiar.
* **Linha** - Uma linha corresponde a um conjunto de percursos Uma linha terá, pelo menos, dois percursos em sentidos opostos. Por vezes poderá ter percursos alternativos que permitem reforçar a oferta em algumas das áreas cobertas pela linha, ex., a linha C do Metro do Porto tem o percurso Campanhã – ISMAI mas também o percurso Campanhã – Fórum Maia.
* **Viagem** - Uma viagem é a definição de horário do percurso
* **Términos** - Um término é um local (nó) de início e/ou de fim de percurso
* **Hora de passagem** - A hora de passagem indica a hora em que uma viagem deve passar num determinado nó.
* **Serviço de viatura** - Um serviço de viatura corresponde ao período de trabalho diário de uma viatura. Um serviço é definido como uma sequência de blocos de trabalho obedecendo a um conjunto de regras.
* **Bloco de trabalho** - Um bloco de trabalho é um conjunto de viagens feitas, sem interrupções, pela mesma viatura, e com ou sem interrupções, pelo mesmo tripulante.
* **Serviço de tripulante** - Um serviço de tripulante corresponde ao período diário de trabalho de um tripulante. Um serviço é definido como uma sequência de blocos de trabalho obedecendo a um conjunto de regras.
* **Etapa** - Uma etapa é um período de tempo sem interrupções em que um tripulante ou viatura está ao serviço da empresa. Um serviço pode ter uma ou mais etapas sendo os casos mais habituais os serviços de uma etapa (ou seguidos), duas etapas e três etapas.
* **Rede** - Conjunto de todos os elementos que fazem parte da rede de planeamento: nós, percursos, linhas, e
tipos de viatura.
* **WGS84** - O WGS84 (World Geodetic System) é um standard usado na cartografia, geodesia e navegação. É a referência de sistema de coordenadas utilizado pelo Sistema de Posicionamento Global (GPS). Limites: -180.0000, -90.0000, 180.0000, 90.0000
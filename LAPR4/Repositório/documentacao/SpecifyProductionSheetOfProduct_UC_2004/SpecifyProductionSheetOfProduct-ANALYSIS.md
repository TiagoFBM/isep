# UC 2004 - Specify Production Sheet of Product

## Pre-Condições ##
* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* É necessário que existam matérias primas e/ou produtos para serem adicionados à ficha de produção.
* Devem existir produtos do sistema sem uma ficha de produção associada.

## Pós-Condições ##
* Exista mais um produto com ficha de produção associada no sistema.

## ANÁLISE ##
* O Produto e os seus derivados têm a mesma ficha de produção (e.g. Rolhas de Cortiça e Rolhas Premium).
* Ao longo do tempo podem existir alterações na ficha de produção de um produto (Contudo, estas a ocorrer serão pontuais e, por tal motivo não foi visto como algo prioritário para o sistema a desenvolver).
* Caso o utilizador selecione um Produto sem ficha de Produção como constituinte da Ficha de Produção, este deve ser notificado de que o Produto selecionado apenas poderá ser consumido (nunca sendo produzido).

## REGRAS DE NEGÓCIO ##
* A ficha de produção pode conter matérias primas e/ou produtos.
* A ficha de produção só pode conter produtos existentes no catálogo de produtos.
* A ficha de produção só pode conter matérias primas existentes no catálogo de matérias primas.
* As quantidades de cada utilizável (Matéria Prima ou Produto) na ficha de produção devem ser superiores a 0.
* A quantidade de produtos possível de produzir com os dados da ficha de produção deve ser superior a 0.
* O Produto ao qual se pretende associar a ficha de produção deve já estar registado no sistema.
* Só pode existir uma ficha de produção para um dado produto.
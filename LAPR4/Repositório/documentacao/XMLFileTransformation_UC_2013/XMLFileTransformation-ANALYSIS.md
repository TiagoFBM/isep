### UC 2013 - XMLFileTransformation ###

## Pré-Condições

- O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
- É necessário que seja gerado um ficheiro XML para que seja feita a sua transformação .        

## Pos-condições

- Irão ser transformadas todas as informações presentes no ficheiro XML para o formato escolhido pelo utilizador.

## Análise

- Irão ser disponibilizadas 3 visualizações possíveis: HTML, JSON e CSV.
- A transformação irá sempre ocorrer através de XSLT.
- O ficheiro transformado será armazenado na pasta Files do sistema.

## Regras de negócio

- O resultado será outro ficheiro, mantendo o original inalterado.
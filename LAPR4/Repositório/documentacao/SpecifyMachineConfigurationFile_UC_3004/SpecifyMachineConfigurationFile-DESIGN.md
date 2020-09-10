### UC 2004 - SPECIFY MACHINE CONFIGURATION FILE

## DESIGN

Utilizar a estrutura base standard da aplicação baseada em camadas

## CLASSES DE DOMINIO

- Machine
- ConfigurationFile

## CONTROLERS

- AddRawMaterialController

## REPOSITÓRIOS

- RepositoryFactory
- MachineRepository

## SERVIÇOS

- **ListMachines** é responsável por listar todas as máquinas existentes no sistema para o utilizador selecionar uma para especificar o ficheiro de configuração.
- **ImportFileToBytesService** é responsável por transformar o conteúdo do ficheiro num vetor de bytes para posteriormente ser armazenado na base de dados

![Imagem](UC_2001_AddRawMaterial_SD.jpg)

	## Contents
- [Views](#views)
	- [Introduction](#introduction)
	- [Nível 1](#nível-1)
		- [Vista Lógica](#vista-lógica)
		- [Vista de Processos](#vista-de-processos)
			- [SSD US1](#ssd-us1)
			- [SSD US2](#ssd-us2)
			- [SSD US3/4](#ssd-us3/4)
			- [SSD US5](#ssd-us5)
			- [SSD US6](#ssd-us6)
			- [SSD US7](#ssd-us7)
			- [SSD US9](#ssd-us9)
			- [SSD US10](#ssd-us10)
	- [Nível 2](#nível-2)
		- [Vista Lógica](#vista-lógica-1)
		- [Vista de Processos](#vista-de-processos-1)
			- [SSD US1](#ssd-us1-1)
			- [SSD US2](#ssd-us2-1)
			- [SSD US3/4](#ssd-us3/4-1)
			- [SSD US5](#ssd-us5-1)
			- [SSD US6](#ssd-us6-1)
			- [SSD US7](#ssd-us7-1)
			- [SSD US9](#ssd-us9-1)
			- [SSD US10](#ssd-us10-1)
		- [Vista de Implementação](#vista-de-implementação)
		- [Vista Física](#vista-física)
	- [Nível 3 (MDR)](#nível-3-mdr)
		- [Vista Lógica](#vista-lógica-2)
		- [Vista de Implementação](#vista-de-implementação-1)
		- [Vista Física](#vista-física-1)
	- [Nível 3 (UI)](#nível-3-ui)
		- [Vista Lógica](#vista-lógica-3)
		- [Vista de Implementação](#vista-de-implementação-2)
		- [Vista Física](#vista-física-2)
	- [Nível 3 (MDV)](#nível-3-mdv)
		- [Vista Lógica](#vista-lógica-4)
		- [Vista de Implementação](#vista-de-implementação-3)
		- [Vista Física](#vista-física-3)
	- [Nível 3 (Planeamento)](#nível-3-planeamento)
		- [Vista Lógica](#vista-lógica-5)
		- [Vista de Implementação](#vista-de-implementação-4)
		- [Vista Física](#vista-física-4)

# Views

## Introduction
Será adotada a combinação de dois modelos de representação arquitetural: C4 e 4+1.

O Modelo de Vistas 4+1 [[Krutchen-1995]](References.md#Kruchten-1995) propõe a descrição do sistema através de vistas complementares permitindo assim analisar separadamente os requisitos dos vários stakeholders do software, tais como utilizadores, administradores de sistemas, project managers, arquitetos e programadores. As vistas são deste modo definidas da seguinte forma:

- Vista lógica: relativa aos aspetos do software visando responder aos desafios do negócio;
- Vista de processos: relativa ao fluxo de processos ou interações no sistema;
- Vista de desenvolvimento: relativa à organização do software no seu ambiente de desenvolvimento;
- Vista física: relativa ao mapeamento dos vários componentes do software em hardware, i.e. onde é executado o software;
- Vista de cenários: relativa à associação de processos de negócio com atores capazes de os espoletar.

O Modelo C4 [[Brown-2020]](References.md#Brown-2020)[[C4-2020]](References.md#C4-2020) defende a descrição do software através de quatro níveis de abstração: sistema, contentor, componente e código. Cada nível adota uma granularidade mais fina que o nível que o antecede, dando assim acesso a mais detalhe de uma parte mais pequena do sistema. Estes níveis podem ser equiparáveis a mapas, e.g. a vista de sistema corresponde ao globo, a vista de contentor corresponde ao mapa de cada continente, a vista de componentes ao mapa de cada país e a vista de código ao mapa de estradas e bairros de cada cidade.
Diferentes níveis permitem contar histórias diferentes a audiências distintas.

Os níveis encontram-se definidos da seguinte forma:
- Nível 1: Descrição (enquadramento) do sistema como um todo;
- Nível 2: Descrição de contentores do sistema;
- Nível 3: Descrição de componentes dos contentores;
- Nível 4: Descrição do código ou partes mais pequenas dos componentes (e como tal, não será abordado neste DAS/SAD).

Pode-se dizer que estes dois modelos se expandem ao longo de eixos distintos, sendo que o Modelo C4 apresenta o sistema com diferentes níveis de detalhe e o Modelo de Vista 4+1 apresenta o sistema de diferentes perspetivas. Ao combinar os dois modelos torna-se possível representar o sistema de diversas perspetivas, cada uma com vários níveis de detalhe.

Para modelar/representar visualmente, tanto o que foi implementado como as ideias e alternativas consideradas, recorre-se à Unified Modeling Language (UML) [[UML-2020]](References.md#UML-2020) [[UMLDiagrams-2020]](References.md#UMLDiagrams-2020).

## Nível 1
### Vista Lógica

![N1-VL](diagramas/nivel1/N1-VL.png)

### Vista de Processos
#### SSD US1
![N1-VP-US1](diagramas/nivel1/AddVehicle-N1-SSD.png)

#### SSD US2
![N1-VP-US2](diagramas/nivel1/AddDriver-N1-SSD.jpg)

#### SSD US3/4
![N1-VP-US34](diagramas/nivel1/AddTrip-N1-SSD.png)

#### SSD US5
![N1-VP-US5](diagramas/nivel1/AddVehicleDuty-N1-SSD.png)

#### SSD US7
![N1-VP-US7](diagramas/nivel1/AddWorkBlock-N1-SSD.jpg)

#### SSD US9
![N1-VP-US9](diagramas/nivel1/ImportToMDV-N1-SSD.png)

#### SSD US10
![N1-VP-US10](diagramas/nivel1/AddDriver-N1-SSD.jpg)

## Nível 2
### Vista Lógica

![N2-VL](diagramas/nivel2/N2-VL.png)

### Vista de Processos

#### SSD US1
![N1-VP-US1](diagramas/nivel2/AddVehicle-N2-SSD.png)

#### SSD US2
![N1-VP-US2](diagramas/nivel2/AddDriver-N2-SSD.jpg)

#### SSD US3/4
![N1-VP-US2](diagramas/nivel2/AddTrip-N2-SSD.png)

#### SSD US5
![N1-VP-US2](diagramas/nivel2/AddVehicleDuty-N2-SSD.png)

#### SSD US7
![N1-VP-US7](diagramas/nivel2/AddWorkBlock-N2-SSD.jpg)

#### SSD US9
![N1-VP-US9](diagramas/nivel2/ImportToMDV-N2-SSD.png)

#### SSD US10
![N1-VP-US10](diagramas/nivel2/AddDriverDuty-N2-SSD.jpg)

### Vista de Implementação
![N2-VL](diagramas/nivel2/N2-VI.png)

### Vista Física

Uma proposta muito simplificada. 
![N2-VL](diagramas/nivel2/N2-VF.png)

De facto, deve-se ter em consideração os requisitos não funcionais ["Physical Contraints"](Background.md#Physical_Constraints).

## Nível 3 (MDR)
### Vista Lógica
Alternativa baseada numa arquitetura por camadas sobrepostas:
![N3-VL-MDR-alt1](diagramas/nivel3/MDR/N3-VL-MDR-alt1.png)

Alternativa baseada numa arquitetura por camadas concêntricas (Onion):
![N3-VL-MDR-alt2](diagramas/nivel3/MDR/N3-VL-MDR-alt2.png)

A alternativa Onion será a adotada.

### Vista de Implementação
![N3-VI-MDR-alt2](diagramas/nivel3/MDR/N3-VI-MDR-alt2.png)

Alguns detalhes mais (se existissem pais do que 4 níveis, podia ser considerado nível 4):

![N3.1-VI-MDR-alt2](diagramas/nivel3/MDR/N3.1-VI-MDR-alt2.png)

### Vista Física

Por agora, não existe necessidade de ser representada.


## Nível 3 (UI)
### Vista Lógica
![N3-VL-SPA](diagramas/nivel3/SPA/N3-VL-SPA.png)

### Vista de Implementação
![N3-VL-SPA](diagramas/nivel3/SPA/N3-VI-SPA.png)


## Nível 3 (MDV)
### Vista Lógica
![N3-VL-MDV](diagramas/nivel3/MDV/N3-VL-MDV.png)

### Vista de Implementação
![N3-VI-MDV](diagramas/nivel3/MDV/N3-VI-MDV.png)

### Vista Física
Por agora, não existe necessidade de ser representada.


## Nível 3 (Planeamento)

### Vista Lógica
![N3-VL-Planning](diagramas/nivel3/Planning/N3-VL-Planning.png)

### Vista de Implementação
Por agora, não existe necessidade de ser representada.

### Vista Física

![N3-VI-Planning](diagramas/nivel3/Planning/N3-VF-Planning.png)
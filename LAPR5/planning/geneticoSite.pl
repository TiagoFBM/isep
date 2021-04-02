:-consult(bc).
:-consult(planeamentoDistancia).

% Bibliotecas HTTP
:-use_module(library(http/thread_httpd)).
:-use_module(library(http/http_dispatch)).
:-use_module(library(http/http_parameters)).
:-use_module(library(http/http_client)).

% Bibliotecas JSONxcd
:-use_module(library(http/json_convert)).
:-use_module(library(http/http_json)).
:-use_module(library(http/json)).

:-use_module(library(http/http_cors)).
:-set_setting(http:cors, [*]).

:-dynamic listaMot/2.

% Relação entre pedidos HTTP e predicados que os processa
:-http_handler('/genericAlgorithm', geneticAlgorithm, []).

:-json_object listaMotJSON(
        drivers:list(string),
		peso:integer,
		times:list(integer)
).

geneticAlgorithm(Request):-
        cors_enable,
        http_parameters(Request, [vehicleID(VehicleID, [number]), generationNumber(GenerationNumber, [number]), dimension(Dimension, [number]),
			crossingProbability(CrossingProbability, [number]), mutationProbability(MutationProbability, [number])]),
        
		retractall(listaMot(_,_)),
		geraSite(VehicleID, GenerationNumber, Dimension, CrossingProbability,MutationProbability),
		listaMot(A,B),
		vehicleduty(VehicleID, ListaWorkblocks),
		trataListaTimes(ListaWorkblocks, ListaTimes),
		prolog_to_json(listaMotJSON(A,B, ListaTimes), JSONObject), 
		reply_json(JSONObject, [json_object(dict)]).

trataListaTimes([], []).
trataListaTimes([A|B],[Time|ListaTimes]):- workblock(A,_,Time,_), trataListaTimes(B, ListaTimes).

inicializa(IDVeiculo, NG, DP, P1, P2):-
		(retract(idVeiculo(_));true), asserta(idVeiculo(IDVeiculo)),
		(retract(geracoes(_));true), asserta(geracoes(NG)),
		(retract(populacao(_));true),asserta(populacao(DP)),
		PC is P1/100,
		(retract(prob_cruzamento(_));true),asserta(prob_cruzamento(PC)),
		PM is P2/100, 
		(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)),
		(retract(tempo_max_execucao(_));true), asserta(tempo_max_execucao(60)),
		(retract(melhor_peso(_));true), asserta(melhor_peso(0)).

geraSite(IDVeiculo, NG, DP, P1, P2):- 
		inicializa(IDVeiculo, NG, DP, P1, P2),
		get_time(TimeAtual),
		asserta(tempo_inicial_execucao(TimeAtual)),
		gera_populacao(Pop),
		%write('Pop'),nl,write_array(Pop),nl,
		avalia_populacao(Pop,PopAv),
		%write('PopAv'),nl,write_array(PopAv),nl,
		ordena_populacao(PopAv,PopOrd),
		%write('PopOrd'),nl,write_array(PopOrd),nl,
		geracoes(NG),
		gera_geracao(NG,PopOrd),!.

gera_populacao(Pop):-
		populacao(TamPop),
		idVeiculo(IDVeiculo),
		lista_motoristas_nworkblocks(IDVeiculo,ListaWorkblocks),
		gerarListaMotoristasInicial(ListaWorkblocks,ListaMotoristas),
		gera_populacao(TamPop,ListaMotoristas,Pop).

gera_populacao(0,_,[]):-!.
gera_populacao(TamPop,ListaWorkblocks,[Ind|Resto]):-
		TamPop1 is TamPop-1,
		gera_populacao(TamPop1,ListaWorkblocks,Resto),
		gera_individuo(ListaWorkblocks,Ind),
		(
			not(member(Ind,Resto))
			;
			gera_populacao(TamPop,ListaWorkblocks,[Ind|Resto])
		).

retira(1,[G|Resto],G,Resto).
retira(N,[G1|Resto],G,[G1|Resto1]):-
		N1 is N-1,
		retira(N1,Resto,G,Resto1).

ordena_populacao(PopAv,PopAvOrd):-
		bsort(PopAv,PopAvOrd).

bsort([X],[X]):-!.
bsort([X|Xs],Ys):-
		bsort(Xs,Zs),
		btroca([X|Zs],Ys).

btroca([X],[X]):-!.
btroca([X*VX,Y*VY|L1],[Y*VY|L2]):-
		VX>VY,!,
		btroca([X*VX|L1],L2).
btroca([X|L1],[X|L2]):-btroca(L1,L2).

% Valida se o programa pode continuar a gerar novas gerações.
check_end_geracao(N, [Ind|Resto]):-
		(
			check_end_NumGeracoes(N),
			!
			;
			check_end_melhor_peso(Ind),
			!
			;
			check_end_tempo,
			!
			;
			check_estabilizado([Ind|Resto]),
			!
    	),
    criaObjetoDriver(Ind),!.

% Criação do json object que vai ser enviado como resposta no pedido get
criaObjetoDriver(H*T):- assertz(listaMot(H,T)).
%listaMot(H,T).

imprimeLista([]).
imprimeLista([H|T]):-imprimeLista(T).

check_estabilizado([_]).
check_estabilizado([_*V,Ind2*V|Resto]):-check_estabilizado([Ind2*V|Resto]),!.

check_end_NumGeracoes(N):-
		N =< 0.

check_end_melhor_peso(_*V):-
		melhor_peso(P),
		V=<P,!.

check_end_tempo:-
		tempo_max_execucao(MaxTempo),
		tempo_inicial_execucao(TempoInicial),
		get_time(TempoAtual),
		T is TempoAtual - TempoInicial,
		T>=MaxTempo,!.

gera_geracao(N,Pop):-
		geracoes(G),
		OutputNumber is (G-N)+1,
		check_end_geracao(OutputNumber,Pop),!.

gera_geracao(N,Pop):-
		cruzamentoAleatorio(Pop,NPop1),
		mutacao(NPop1,NPop),
		avalia_populacao(NPop,NPopAv),
		ordena_populacao(NPopAv,NPopOrd),
		append(Pop, NPopOrd, Juncao),
		ordena_populacao(Juncao,JuncaoOrdenada),
		proximaGeracao(JuncaoOrdenada,NovaGeracao),
		N1 is N-1,
		gera_geracao(N1,NovaGeracao).

% Gera uma nova geração com os ProbMelhores melhores da anterior.
proximaGeracao(ListaMaior, NovaGeracao):-
		length(ListaMaior,SizeListaMaior),
		prob_escolher_melhores(ProbMelhores),
		ProbMelhoresPerc is ProbMelhores/200,
		ProbPioresPerc is 0.5 - ProbMelhoresPerc,
		NMelhores is round(SizeListaMaior*ProbMelhoresPerc),
		%writeln(NMelhores),
		getFirstN(ListaMaior,NMelhores,ListaNMelhores,Resto),
		random_permutation(Resto, RestoRandom),
		NPiores is round(SizeListaMaior*ProbPioresPerc),
		getFirstN(RestoRandom,NPiores,ListaNPiores,_),
		%writeln(ListaNMelhores),
		%writeln(ListaNPiores),
		append(ListaNMelhores,ListaNPiores,NovaGeracao),!.

getFirstN([],_,[],[]).
getFirstN(L,0,[],L).
getFirstN([H|TListaMaior],N,[H|ListaNMelhores],Resto):-
		N1 is N - 1,
		getFirstN(TListaMaior,N1,ListaNMelhores,Resto),!.

cruzamento([],[]).
cruzamento([Ind*_],[Ind]).
cruzamento([Ind1*_,Ind2*_|Resto],[NInd1,NInd2|Resto1]):-
		length(Ind1, SizeInd1),
		gerar_pontos_cruzamento(P1,P2,SizeInd1),
		prob_cruzamento(Pcruz),random(0.0,1.0,Pc),
		(
			(Pc =< Pcruz,!,
			cruzar(Ind1,Ind2,P1,P2,NInd1), 
			cruzar(Ind2,Ind1,P1,P2,NInd2))
			;
			(NInd1=Ind1,NInd2=Ind2)
		),
		cruzamento(Resto,Resto1).

% Devolve uma lista (Ind1,) com os elementos da ListaIndividuo baralhados.
% Ex: geraDoisIndividuos([1,2,3],X1).
% X1 = [2, 3, 1],
gera_individuo(ListaIndividuo,Ind1):-random_permutation(ListaIndividuo, Ind1).

% Devolve Lista com todos os códigos de motoristas com o número de Workblocks desse motorista, por ordem.
% Ex: gerarListaMotoristasInicial([(276,2),(5188,3),(16690,2),(18107,6)])
% L = [5188,5188,5188,276, 276,16690,16690,18107,18107,18107,18107,18107,18107].
gerarListaMotoristasInicial([(Codigo,N)|[]], L):-preencheListaNVezes(Codigo,N,L), !.

gerarListaMotoristasInicial([(Codigo,N)|T], L):-preencheListaNVezes(Codigo,N,L1), 
												append(L1,L2,L),
												gerarListaMotoristasInicial(T,L2).

% Preenche Lista com Codigo N vezes.
% Ex: preencheListaNVezes(123,5,L).
% L = [123, 123, 123, 123, 123].
preencheListaNVezes(Codigo, 1, [Codigo]):-!.
preencheListaNVezes(Codigo, N, Lista):- N1 is N-1, 
										append([Codigo],L1,Lista), 
										preencheListaNVezes(Codigo, N1, L1).

%Elimina da primeira lista os elementos da segunda (inclui repetidos).
eliminaElementosDeIndividuo(L,[],L).
eliminaElementosDeIndividuo(L,[H|T],LR):-eliminaUltimo(H,L,L1),
										eliminaElementosDeIndividuo(L1,T,LR),!.

eliminaPrimeiro(X,L,L):-not(member(X,L)),!.
eliminaPrimeiro(X,[X|T],T):-!.
eliminaPrimeiro(X,[Y|T],[Y|L]):-eliminaPrimeiro(X,T,L).

eliminaUltimo(X, L, LR):-
		reverse(L, L1), 
    	eliminaPrimeiro(X, L1, L2),
    	reverse(L2, LR),!.

% Cria uma sublista de L1 entre os indices I1 e I2
sublista(L1,I1,I2,L):-
		I1 > I2, NovoI1 = I2, NovoI2 = I1,!,
		sublista(L1,NovoI1,NovoI2,L).

sublista(L1,I1,I2,L):-
		length(L1,SizeL1), NovoI1 = I1,I2 > SizeL1, NovoI2 = SizeL1,!,
		sublista(L1,NovoI1,NovoI2,L).

sublista(L,P2,P2,[X]):- nth1(P2,L,X),!.
sublista(L,P1,P2,[X|LR]):- P11 is P1+1,
				sublista(L,P11,P2,LR),
				nth1(P1,L,X).

rotateRight(Lista, N, ListaRodada):-
    length(Lista, Length),
    AntiN is Length - N,
    rotateLeft(Lista, AntiN, ListaRodada).

rotateLeft(ListaRodada, 0, ListaRodada):-!.
rotateLeft([H|Lista], N, ListaRodada):-
    N1 is N-1,
    append(Lista, [H], ListaTemp),
    rotateLeft(ListaTemp, N1, ListaRodada).

cruzar(Ind1,Ind2,P1,P2,NInd11):-
		sublista(Ind1,P1,P2,Sub1),
		length(Ind1, SizeInd1),
		R is SizeInd1-P2,
		rotateRight(Ind2,R,Ind21),
		eliminaElementosDeIndividuo(Ind21,Sub1,Sub2),
		%writeln("Sem Elementos da Lista de Corte: "),
		%writeln(Sub2),
		append(Sub2,Sub1,ListaNova),
		rotateLeft(ListaNova,R,NInd11).
		%writeln(NInd11).

gerar_pontos_cruzamento(P1,P2,SizeLista):-
		NTemp is SizeLista+1,
		random(1,NTemp,P11),
		random(1,NTemp,P21),
		P11\==P21,!,
		(
			(P11<P21,!,P1=P11,P2=P21)
			;
			(P1=P21,P2=P11)
		).
gerar_pontos_cruzamento(P1,P2,SizeLista):-
		gerar_pontos_cruzamento(P1,P2,SizeLista).

% Cria uma agenda temporal a partir do Driver Duty de um motorista
% % Ex: [5188,5188,5188] = [(34080,37620,5188), (37620,41220,5188), (41220,44820,5188)]
criarAgendaTemporal(ListaMotoristas,L):-
					idVeiculo(IDVeiculo),
					vehicleduty(IDVeiculo,LWorkBlocks),
					criarAgendaTemporal1(ListaMotoristas,LWorkBlocks,L).

criarAgendaTemporal1([HMotorista|[]],[HWorkBlock|[]],[(HInicial,HFinal,HMotorista)]):-
												workblock(HWorkBlock,_,HInicial,HFinal).

criarAgendaTemporal1([HMotorista|TListaMotoristas],[HWorkBlock|TWorkBlocks],[(HInicial,HFinal,HMotorista)|L]):-
																					workblock(HWorkBlock,_,HInicial,HFinal),	
																					criarAgendaTemporal1(TListaMotoristas,TWorkBlocks,L).

% Aglomera uma lista de agenda temporal pelos motoristas
% Ex: (34080,37620,5188), (37620,41220,5188), (41220,44820,5188) = (34080,44820,5188)
comprimeAgendaTemporal([X],[X]).
comprimeAgendaTemporal([AT1,AT2|ListaAgendaTemporal],L):-
									AT1 = (HInicial1,_,Motorista),
									AT2 = (_,HFinal2,Motorista),
									NovaHInicial = HInicial1,
									NovaHFinal = HFinal2,
									NovaH = (NovaHInicial,NovaHFinal,Motorista),
									comprimeAgendaTemporal([NovaH|ListaAgendaTemporal],L),!.
comprimeAgendaTemporal([AT1,AT2|ListaAgendaTemporal],[AT1|L]):-
									comprimeAgendaTemporal([AT2|ListaAgendaTemporal],L),!.

% Dada uma lista de driver duties retorna uma lista com os driver duties de cada motorista
% obtemListaDriverDutyPorMotorista([(34080,44820,5188), (44820,52020,276), (52020,59220,16690), (59220,77340,18107),(77340,78000,276)],L).
% [
% 	[(34080,44820,5188)]
% 	[(44820,52020,276),(77340, 78000,276)]
% 	[(52020,59220,16690)]
% 	[(59220,77340,18107)]
% ]
obtemListaDriverDutyPorMotorista([],[]).
obtemListaDriverDutyPorMotorista([H|ListaAgendaTemporalComprimida],L):-
									H = (_,_,Motorista),
									geraListaDriverDutyMotorista(Motorista,[H|ListaAgendaTemporalComprimida],L1),
									delete(ListaAgendaTemporalComprimida,(_,_,Motorista),L2),
									obtemListaDriverDutyPorMotorista(L2,L3),
									append([L1],L3,L).

% Dado um motorista e uma lista de driver duties retorna apenas os driver duty desse motorista
% Ex: geraListaDriverDutyMotorista(5188,[(34080,44820,5188), (44820,52020,276), (52020,59220,16690), (59220,77340,18107), (78000,79000,5188)],L).
% L = [(34080, 44820, 5188),  (78000, 79000, 5188)].
geraListaDriverDutyMotorista(_, [], []).
geraListaDriverDutyMotorista(Motorista, [H|TListaDriverDuty], [H|L]):-
									H = (_,_,Motorista),
									geraListaDriverDutyMotorista(Motorista,TListaDriverDuty,L),!.
geraListaDriverDutyMotorista(Motorista, [_|TListaDriverDuty], L):-
									geraListaDriverDutyMotorista(Motorista,TListaDriverDuty,L),!.

validaHardConstraint1HDescansoApos4H([_],0).
validaHardConstraint1HDescansoApos4H([H1,H2|TDriverDuty],Penalizacao):-
											H1 = (HInicial1,HFinal1,_),
											horaToMinutosSegundos(4,_,SegundosLimite4H),
											Dif is HFinal1-HInicial1,
											(
												(
													Dif>=SegundosLimite4H,
													validaHardConstraint1HDescanso(H1,H2,Pen1),!
													;
													Pen1 is 0
												),
												validaHardConstraint1HDescansoApos4H([H2|TDriverDuty],Pen2),!
											),
											Penalizacao is Pen1 + Pen2.

validaHardConstraint1HDescanso(H1, H2, Penalizacao):-
							H1 = (_,HFinal1,_),
							H2 = (HInicial2,_,_),
							Dif is HInicial2-HFinal1,
							horaToMinutosSegundos(1,_,SegundosLimite1H),
							Dif<SegundosLimite1H,
							SegundosPen is SegundosLimite1H-Dif,
							penalizacao_hard_MuitoGrave(Peso),
							Penalizacao is SegundosPen*Peso.

validaHardConstraint4HSeguidasDriverDuty([],0).
validaHardConstraint4HSeguidasDriverDuty([H|TDriverDuty],Penalizacao):-
											H = (HInicial1,HFinal2,_),
											validaHardConstraint4HSeguidas(HInicial1,HFinal2,PenAtual),
											validaHardConstraint4HSeguidasDriverDuty(TDriverDuty,PenResto),
											Pen is PenAtual + PenResto,
											Penalizacao is Pen.

validaHardConstraint4HSeguidas(HInicial,HFinal,Penalizacao):-
											horaToMinutosSegundos(4,_,SegundosLimite),
											Dif is HFinal-HInicial,
											Dif>SegundosLimite, 
											Dif4H is Dif-SegundosLimite,
											penalizacao_hard_MuitoGrave(Peso),
											Penalizacao is Dif4H*Peso,!.
validaHardConstraint4HSeguidas(_,_,0).

validaHardConstraint8HTotaisDriverDuty(DriverDuty,Penalizacao):-
						somaTotalHorasDriverDuty(DriverDuty,NHorasTotais),
						horaToMinutosSegundos(8,_,SegundosLimite),
						NHorasTotais>SegundosLimite,
						Dif8H is NHorasTotais-SegundosLimite,
						penalizacao_hard_MuitoGrave(Peso),
						Penalizacao is Dif8H*Peso,!.
validaHardConstraint8HTotaisDriverDuty(_,0).

somaTotalHorasDriverDuty([],0).
somaTotalHorasDriverDuty([H|TDriverDuty],NHorasTotais):-
						H = (HInicial,HFinal,_),
						somaTotalHorasDriverDuty(TDriverDuty,NHorasRestantes),
						NHorasWorkblock is HFinal-HInicial,
						NHorasTotais is NHorasWorkblock+NHorasRestantes.

validaHardConstraintAlmoco(DriverDuty,Penalizacao):-
							DD1 = (0,0,_),
							DD2 = (86400,86400,_),
							append([DD1],DriverDuty,NovaLista),
							append(NovaLista,[DD2],ListaFinal),
							horaToMinutosSegundos(1,_,SegundosLimite1H),
							maxTempoPausaDriverDuty(ListaFinal,MaxTempoPausa,11,15),
							(
								(MaxTempoPausa<SegundosLimite1H,
								DifPenalizacao is SegundosLimite1H-MaxTempoPausa,
								penalizacao_hard_Grave(Peso),
								Penalizacao is DifPenalizacao*Peso),!
								;
								Penalizacao is 0
							).

validaHardConstraintJantar(DriverDuty,Penalizacao):-
							DD1 = (0,0,_),
							DD2 = (86400,86400,_),
							append([DD1],DriverDuty,NovaLista),
							append(NovaLista,[DD2],ListaFinal),
							horaToMinutosSegundos(1,_,SegundosLimite1H),
							maxTempoPausaDriverDuty(ListaFinal,MaxTempoPausa,18,22),
							(
								(MaxTempoPausa<SegundosLimite1H,
								DifPenalizacao is SegundosLimite1H-MaxTempoPausa,
								penalizacao_hard_Grave(Peso),
								Penalizacao is DifPenalizacao*Peso),!
								;
								Penalizacao is 0
							).

% ================================================================================================================================

% Itera a lista de Driver Duty para encontrar a maior pausa
maxTempoPausaDriverDuty([_],0,_,_).
maxTempoPausaDriverDuty([H1,H2|TDriverDuty],MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
						%writeln("entrei"),
						%writeln(H1),
						%writeln(H2),
						(maxTempoPausa(H1,H2,MaxTempoPausaTmp,HoraInicialRefeicao,HoraFinalRefeicao);MaxTempoPausaTmp is 0),
						maxTempoPausaDriverDuty([H2|TDriverDuty],MaxTempoResto,HoraInicialRefeicao,HoraFinalRefeicao),
						(
							(MaxTempoPausaTmp>MaxTempoResto,
							MaxTempoPausa is MaxTempoPausaTmp),!
							;
							MaxTempoPausa is MaxTempoResto
						).

% H1 e H2 dentro do intervalo de almoço
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_),
			H2 = (HInicial2,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 >= HoraInicialAlmoco,
			HFinal1 =< HoraFinalAlmoco,
			HInicial2 >= HoraInicialAlmoco,
			HInicial2 =< HoraFinalAlmoco,
			MaxTempoPausa is HInicial2-HFinal1,!.

% H1 e H2 fora do intervalo de almoço
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_),
			H2 = (HInicial2,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 =< HoraInicialAlmoco,
			HInicial2 >= HoraFinalAlmoco,
			MaxTempoPausa is HoraFinalAlmoco-HoraInicialAlmoco,!.

% H1 dentro do intervalo de almoço e H2 fora
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_),
			H2 = (HInicial2,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 >= HoraInicialAlmoco,
			HFinal1 =< HoraFinalAlmoco,
			HInicial2 >= HoraFinalAlmoco,
			MaxTempoPausa is HoraFinalAlmoco-HFinal1,!.

% H2 dentro do intervalo de almoço e H1 fora
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_),
			H2 = (HInicial2,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HInicial2 >= HoraInicialAlmoco,
			HInicial2 =< HoraFinalAlmoco,
			HFinal1=<HoraInicialAlmoco,
			MaxTempoPausa is HInicial2-HoraInicialAlmoco,!.

% ================================================================================================================================

validaHardConstraintLimitesDriverDuty([],0).
validaHardConstraintLimitesDriverDuty([H|TDriverDuty],Penalizacao):-
									penalizacao_hard_Grave(Peso),
									validaConstraintLimites(H,8,12,PenAtual,Peso),
									validaHardConstraintLimitesDriverDuty(TDriverDuty,PenResto),
									Penalizacao is PenAtual + PenResto.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,_,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					HInicial<SegundosLimiteInferior,
					HFinal=<SegundosLimiteInferior,
					Dif is HFinal - HInicial,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba dentro do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial<SegundosLimiteInferior,
					HFinal>SegundosLimiteInferior,
					HFinal=<SegundosLimiteSuperior,
					Dif is SegundosLimiteInferior-HInicial,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial<SegundosLimiteInferior,
					HFinal>SegundosLimiteSuperior,
					DifInferior is SegundosLimiteInferior-HInicial,
					DifSuperior is HFinal-SegundosLimiteSuperior,
					Dif = DifInferior + DifSuperior,
					Penalizacao is Dif*Peso,!.

%Começa dentro e acaba dentro do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,_):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>=SegundosLimiteInferior,
					HInicial=<SegundosLimiteSuperior,
					HFinal>=SegundosLimiteInferior,
					HFinal=<SegundosLimiteSuperior,
					Penalizacao is 0,!.

%Começa dentro e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>=SegundosLimiteInferior,
					HInicial=<SegundosLimiteSuperior,
					HFinal>SegundosLimiteSuperior,
					Dif is HFinal-SegundosLimiteSuperior,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,_,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>SegundosLimiteSuperior,
					HFinal>SegundosLimiteSuperior,
					Dif is HFinal-HInicial,
					Penalizacao is Dif*Peso,!.

% ================================================================================================================================

validaSoftConstraintLimitesDriverDuty([],0).
validaSoftConstraintLimitesDriverDuty([H|TDriverDuty],Penalizacao):-
									penalizacao_soft(Peso),
									validaConstraintLimites(H,10,20,PenAtual,Peso),
									validaSoftConstraintLimitesDriverDuty(TDriverDuty,PenResto),
									Penalizacao is PenAtual + PenResto.

% ================================================================================================================================

avalia_populacao([],[]).
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
		avalia(Ind,V),
		avalia_populacao(Resto,Resto1),!.

avalia([],0):-!.
avalia(Ind,V):-
		criarAgendaTemporal(Ind,AgendaTemporal),
		%writeln(AgendaTemporal),nl,
		comprimeAgendaTemporal(AgendaTemporal,AgendaTemporalComprimida),
		%writeln(AgendaTemporalComprimida),nl,
		obtemListaDriverDutyPorMotorista(AgendaTemporalComprimida,ListaMatriz),
		%writeln(ListaMatriz),nl,
		restricoesIndividuo(ListaMatriz,V),!.

restricoesIndividuo([],0).
restricoesIndividuo([DriverDuty|TDriverDuty],PenalizacaoTotal):-
		%writeln(DriverDuty),
		(validaHardConstraint4HSeguidasDriverDuty(DriverDuty,P1),!;P1 is 0),
		validaHardConstraint8HTotaisDriverDuty(DriverDuty,P2),
		validaHardConstraint1HDescansoApos4H(DriverDuty,P3),
		validaHardConstraintAlmoco(DriverDuty,P4),
		validaHardConstraintJantar(DriverDuty,P5),
		validaHardConstraintLimitesDriverDuty(DriverDuty,P6),
		validaSoftConstraintLimitesDriverDuty(DriverDuty,P7),
		P1_2 is P1 + P2,
		P2_3 is P1_2 + P3,
		P3_4 is P2_3 + P4,
		P4_5 is P3_4 + P5,
		P5_6 is P4_5 + P6,
		PenalizacaoTotalAtual is P5_6 + P7,
		%writeln(P1),
		%writeln(P2),
		%writeln(P3),
		%writeln(P4),
		%writeln(P5),
		%writeln(P6),
		%writeln(P7),
		%writeln(PenalizacaoTotalAtual),nl,
		restricoesIndividuo(TDriverDuty,PenalizacaoTotalResto),
		PenalizacaoTotal is PenalizacaoTotalAtual + PenalizacaoTotalResto.

% ================================================================================================================================

mutacao([],[]).
mutacao([Ind|Rest],[NInd|Rest1]):-
		prob_mutacao(Pmut),
		random(0.0,1.0,Pm),
		((Pm < Pmut,!,mutacao1(Ind,NInd));NInd = Ind),
		mutacao(Rest,Rest1).

mutacao1(Ind,NInd):-
		length(Ind, SizeInd),
		gerar_pontos_cruzamento(P1,P2,SizeInd),
		mutacao22(Ind,P1,P2,NInd).

mutacao22([G1|Ind],1,P2,[G2|NInd]):-
		!, P21 is P2-1,
		mutacao23(G1,P21,Ind,G2,NInd).
mutacao22([G|Ind],P1,P2,[G|NInd]):-
		P11 is P1-1, P21 is P2-1,
		mutacao22(Ind,P11,P21,NInd).

mutacao23(G1,1,[G2|Ind],G2,[G1|Ind]):-!.
mutacao23(G1,P,[G|Ind],G2,[G|NInd]):-
		P1 is P-1,
		mutacao23(G1,P1,Ind,G2,NInd).

cruzamentoAleatorio([],[]):-!.
cruzamentoAleatorio([Ind*_],[Ind]):-!.
cruzamentoAleatorio(ListaIndividuos,[NInd1,NInd2|Resto]):-
					length(ListaIndividuos, SizeListaInd),
					SizeListaInd1 is SizeListaInd+1,
					geraDoisNumerosAleatoriosDiferentes(1,SizeListaInd1,N1,N2),
					nth1(N1,ListaIndividuos,Ind1*_),
					nth1(N2,ListaIndividuos,Ind2*_),

					eliminaPrimeiro(Ind1*_,ListaIndividuos,ListaIndividuos1),

					eliminaPrimeiro(Ind2*_,ListaIndividuos1,ListaIndividuos2),
					
					length(Ind1, SizeInd1),
					gerar_pontos_cruzamento(P1,P2,SizeInd1),
					Pcruz is 1, Pc is 0,
					(
						(Pc =< Pcruz,!,
						cruzar(Ind1,Ind2,P1,P2,NInd1), 
						cruzar(Ind2,Ind1,P1,P2,NInd2))
						;
						(NInd1=Ind1,NInd2=Ind2)
					),
					cruzamentoAleatorio(ListaIndividuos2,Resto).

geraDoisNumerosAleatoriosDiferentes(LimiteInferior,LimiteSuperior,N1,N2):-
								random(LimiteInferior,LimiteSuperior,Num1),
								random(LimiteInferior,LimiteSuperior,Num2),
								Num1\==Num2,
								N1 is Num1,
								N2 is Num2,!.
geraDoisNumerosAleatoriosDiferentes(LimiteInferior,LimiteSuperior,N1,N2):-
	geraDoisNumerosAleatoriosDiferentes(LimiteInferior,LimiteSuperior,N1,N2).
	

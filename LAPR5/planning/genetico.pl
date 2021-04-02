:-consult(bc).
:-consult(geneticoD).
:-consult(planeamentoDistancia).

% parameterizaçao
inicializa(NG,DP,P1,P2,T,PI):-
			(retract(geracoes(_));true), asserta(geracoes(NG)),
			(retract(populacao(_));true),asserta(populacao(DP)),
			PC is P1/100, 
			(retract(prob_cruzamento(_));true),asserta(prob_cruzamento(PC)),
			PM is P2/100, 
			(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)),
			(retract(tempo_max_execucao(_));true), asserta(tempo_max_execucao(T)),
			(retract(melhor_peso(_));true), asserta(melhor_peso(PI)).

genetico(IDVehicleDuty,IndR,Mensagem):-
		(retract(idVehicleDuty(_));true), asserta(idVehicleDuty(IDVehicleDuty)),
		get_time(TimeAtual),
		(retract(tempo_inicial_execucao(_));true),asserta(tempo_inicial_execucao(TimeAtual)),
		gera_populacao(Pop),
		%write('Pop'),nl,write_array(Pop),nl,
		avalia_populacao(Pop,PopAv),
		%write('PopAv'),nl,write_array(PopAv),nl,
		ordena_populacao(PopAv,PopOrd),
		%write('PopOrd'),nl,write_array(PopOrd),nl,
		geracoes(NG),
		gera_geracao(NG,PopOrd,IndR,Mensagem), !.

%Assumindo que o número de populações é sempre superior a 2
gera_populacao([Ind1,Ind2|Pop]):-
		populacao(TamPop),
		idVehicleDuty(IDVeiculo),
		lista_motoristas_nworkblocks(IDVeiculo,ListaWorkblocks),
		gerarListaMotoristasInicial(ListaWorkblocks,ListaMotoristas),
		criar_individuo_euristica_4hMax(IDVeiculo,Ind1),
		criar_individuo_euristica_alternada(IDVeiculo,Ind2),
		NovoTamPop is TamPop - 2,
		gera_populacao(NovoTamPop,ListaMotoristas,Pop).

gera_populacao(0,_,[]):-!.
gera_populacao(TamPop,ListaWorkblocks,[Ind|Resto]):-
		TamPop1 is TamPop-1,
		gera_populacao(TamPop1,ListaWorkblocks,Resto),
		gera_individuo(ListaWorkblocks,Ind)
		/*
		(
			not(member(Ind,Resto)),!
			;
			gera_populacao(TamPop,ListaWorkblocks,[Ind|Resto])
		)
		*/
		.

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
check_end_geracao(N, [Ind|Resto], IndR, Mensagem):-
		(
			check_end_NumGeracoes(N),
			Mensagem = "Número de gerações excedido.",!
			;
			check_end_melhor_peso(Ind),
			Mensagem = "O melhor peso foi encontrado!",!
			;
			check_end_tempo,
			Mensagem = "Excedeu o tempo máximo de execução.",!
			;
			count_estabilizados([Ind|Resto]),
			Mensagem = "O algoritmo estabilizou.",!
    	),
		IndR = Ind,!
.

count_estabilizados(Populacao):-
	(	
		check_estabilizado(Populacao),!
		;
		retractall(estabilizador(_)),
		asserta(estabilizador(0))
	),
	estabilizador(N),
	estabilizador_max(M),
	N>=M.

check_estabilizado([_]):-retract(estabilizador(N)),
						N1 is N + 1,
						asserta(estabilizador(N1)),!.
check_estabilizado([_*V,Ind2*V|Resto]):-check_estabilizado([Ind2*V|Resto]),!.

check_estabilizadoAntigo([_]).
check_estabilizadoAntigo([_*V,Ind2*V|Resto]):-check_estabilizadoAntigo([Ind2*V|Resto]),!.


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

gera_geracao(N,Pop, IndR, Mensagem):-
		check_end_geracao(N,Pop,IndR, Mensagem),!.

gera_geracao(N,Pop, IndR, Mensagem):-
		cruzamentoAleatorio(Pop,NPop1),
		mutacao(NPop1,NPop),
		avalia_populacao(NPop,NPopAv),
		ordena_populacao(NPopAv,NPopOrd),
		append(Pop, NPopOrd, Juncao),
		ordena_populacao(Juncao,JuncaoOrdenada),
		proximaGeracao(JuncaoOrdenada,NovaGeracao),
		N1 is N-1,
		gera_geracao(N1,NovaGeracao, IndR, Mensagem), !.

% Gera uma nova geração com os ProbMelhores melhores da anterior.
proximaGeracao(ListaMaior, NovaGeracao):-
		length(ListaMaior,SizeListaMaior),
		prob_escolher_melhores(ProbMelhores),
		ProbMelhoresPerc is ProbMelhores/200,
		ProbPioresPerc is 0.5 - ProbMelhoresPerc,
		NMelhores is round(SizeListaMaior*ProbMelhoresPerc),
		getFirstN(ListaMaior,NMelhores,ListaNMelhores,Resto),
		random_permutation(Resto, RestoRandom),
		NPiores is round(SizeListaMaior*ProbPioresPerc),
		getFirstN(RestoRandom,NPiores,ListaNPiores,_),
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
preencheListaNVezes(_, 0, []):-!.
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
		append(Sub2,Sub1,ListaNova),
		rotateLeft(ListaNova,R,NInd11).

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
					idVehicleDuty(IDVeiculo),
					vehicleduty(IDVeiculo,LWorkBlocks),
					criarAgendaTemporal1(ListaMotoristas,LWorkBlocks,L).

criarAgendaTemporal1([],[],[]):-!.

criarAgendaTemporal1([HMotorista|TListaMotoristas],[HWorkBlock|TWorkBlocks],[(HInicial,HFinal,HMotorista,HWorkBlock)|L]):-
																					workblock(HWorkBlock,_,HInicial,HFinal),	
																					criarAgendaTemporal1(TListaMotoristas,TWorkBlocks,L).
criarAgendaTemporal1([_|TListaMotoristas],[_|TWorkBlocks],L):-
	criarAgendaTemporal1(TListaMotoristas,TWorkBlocks,L),!.

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
									H = (_,_,Motorista,_),
									geraListaDriverDutyMotorista(Motorista,[H|ListaAgendaTemporalComprimida],L1),
									delete(ListaAgendaTemporalComprimida,(_,_,Motorista,_),L2),
									obtemListaDriverDutyPorMotorista(L2,L3),
									append([L1],L3,L).

% Dado um motorista e uma lista de driver duties retorna apenas os driver duty desse motorista
% Ex: geraListaDriverDutyMotorista(5188,[(34080,44820,5188), (44820,52020,276), (52020,59220,16690), (59220,77340,18107), (78000,79000,5188)],L).
% L = [(34080, 44820, 5188),  (78000, 79000, 5188)].
geraListaDriverDutyMotorista(_, [], []).
geraListaDriverDutyMotorista(Motorista, [H|TListaDriverDuty], [H|L]):-
									H = (_,_,Motorista,_),
									geraListaDriverDutyMotorista(Motorista,TListaDriverDuty,L),!.
geraListaDriverDutyMotorista(Motorista, [_|TListaDriverDuty], L):-
									geraListaDriverDutyMotorista(Motorista,TListaDriverDuty,L),!.

validaHardConstraint1HDescansoApos4H(DD, Pen):-
    validaHardConstraint1HDescansoApos4H(DD,0,Pen),!
.

validaHardConstraint1HDescansoApos4H([_],_,0).

validaHardConstraint1HDescansoApos4H([DD1,DD2|Resto],TempoAcumulado,Pen):-
    DD1 = (I1,F1,_,_),
    DD2 = (I2,_,_,_),

    Dur is (F1 - I1) + TempoAcumulado,
    (
        I2 =< F1,
        validaHardConstraint1HDescansoApos4H([DD2|Resto],Dur,Pen1),
        Pen is Pen1
    ;
        I2 > F1,

        validaHardConstraint1HDescansoApos4H([DD2|Resto],0,Pen1),

        penalizacao_hard_MuitoGrave(Mult),
        horaToMinutosSegundos(4,_,Sec),
        Seconds is Dur - Sec,

        (
            Seconds >= 0,
            Pausa is I2 - F1,
            horaToMinutosSegundos(1,_,Sec1),
            Seconds1 is Sec1 - Pausa,

            (
                Seconds1 > 0,
                Pen is Pen1 + (Seconds1 * Mult)
            ;
                Seconds =< 0,
                Pen is Pen1
            )
        ;
            Seconds < 0,
            Pen is Pen1
        )
    )
.

validaHardConstraint1HDescanso(H1, H2, Penalizacao):-
							H1 = (_,HFinal1,_,_),
							H2 = (HInicial2,_,_,_),
							Dif is HInicial2-HFinal1,
							horaToMinutosSegundos(1,_,SegundosLimite1H),
							Dif<SegundosLimite1H,
							SegundosPen is SegundosLimite1H-Dif,
							penalizacao_hard_MuitoGrave(Peso),
							Penalizacao is SegundosPen*Peso.

validaHardConstraint4HSeguidasDriverDuty(DD,Pen):-
	validaHardConstraint4HSeguidasDriverDuty(DD,0,Pen),!
.

validaHardConstraint4HSeguidasDriverDuty([(A,B,_,_)],Tempo,Pen):-
    Dur is B - A,
    Tempo1 is Tempo + Dur,
    penalizacao_hard_MuitoGrave(Mult),
    horaToMinutosSegundos(4,_,Sec),
    Seconds is Tempo1 - Sec,
    (
        Seconds > 0,
        Pen is Seconds * Mult,
        !
    ;
        Pen is 0
    )
.
validaHardConstraint4HSeguidasDriverDuty([DD1,DD2|Resto],Tempo,Pen):-

    DD1 = (Ta1,Ta2,_,_),
    DD2 = (Tb1,Tb2,_,_),

    (
        Ta2 >= Tb1,
        Dur is Tb2 - Ta1,
        Tempo1 is Tempo + Dur,
        !
    ;
        Tempo1 is 0
    ),
    validaHardConstraint4HSeguidasDriverDuty([DD2|Resto], Tempo1, Pen1),

    penalizacao_hard_MuitoGrave(Mult),
    horaToMinutosSegundos(4,_,Sec),
    Seconds is Tempo1 - Sec,
    (
        Seconds > 0,
        Pen is Pen1 + (Seconds * Mult),
        !
    ;
        Pen is Pen1
    )
.

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
						H = (HInicial,HFinal,_,_),
						somaTotalHorasDriverDuty(TDriverDuty,NHorasRestantes),
						NHorasWorkblock is HFinal-HInicial,
						NHorasTotais is NHorasWorkblock+NHorasRestantes.

validaHardConstraintAlmoco(DriverDuty,Penalizacao):-
							DD1 = (0,0,_,_),
							DD2 = (86400,86400,_,_),
							append([DD1],DriverDuty,NovaLista),
							append(NovaLista,[DD2],ListaFinal),
							validaHardConstraintRefeicao(ListaFinal,11,15,Penalizacao)
.

validaHardConstraintRefeicao(DriverDuty,HoraInicio,HoraFim,Penalizacao):-
		horaToMinutosSegundos(1,_,SegundosLimite1H),
		maxTempoPausaDriverDuty(DriverDuty,MaxTempoPausa,HoraInicio,HoraFim),
		(
			(MaxTempoPausa<SegundosLimite1H,
			DifPenalizacao is SegundosLimite1H-MaxTempoPausa,
			penalizacao_hard_Grave(Peso),
			Penalizacao is DifPenalizacao*Peso),!
		;
			Penalizacao is 0
		)
.

validaHardConstraintJantar(DriverDuty,Penalizacao):-
							DD1 = (0,0,_,_),
							DD2 = (86400,86400,_,_),
							append([DD1],DriverDuty,NovaLista),
							append(NovaLista,[DD2],ListaFinal),
							validaHardConstraintRefeicao(ListaFinal,18,22,Penalizacao)
.
% ================================================================================================================================

% Itera a lista de Driver Duty para encontrar a maior pausa
maxTempoPausaDriverDuty([_],0,_,_).
maxTempoPausaDriverDuty([H1,H2|TDriverDuty],MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
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
			H1 = (_,HFinal1,_,_),
			H2 = (HInicial2,_,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 >= HoraInicialAlmoco,
			HFinal1 =< HoraFinalAlmoco,
			HInicial2 >= HoraInicialAlmoco,
			HInicial2 =< HoraFinalAlmoco,
			MaxTempoPausa is HInicial2-HFinal1,!.

% H1 e H2 fora do intervalo de almoço
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_,_),
			H2 = (HInicial2,_,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 =< HoraInicialAlmoco,
			HInicial2 >= HoraFinalAlmoco,
			MaxTempoPausa is HoraFinalAlmoco-HoraInicialAlmoco,!.

% H1 dentro do intervalo de almoço e H2 fora
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_,_),
			H2 = (HInicial2,_,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HFinal1 >= HoraInicialAlmoco,
			HFinal1 =< HoraFinalAlmoco,
			HInicial2 >= HoraFinalAlmoco,
			MaxTempoPausa is HoraFinalAlmoco-HFinal1,!.

% H2 dentro do intervalo de almoço e H1 fora
maxTempoPausa(H1,H2,MaxTempoPausa,HoraInicialRefeicao,HoraFinalRefeicao):-
			H1 = (_,HFinal1,_,_),
			H2 = (HInicial2,_,_,_),
			horaToMinutosSegundos(HoraInicialRefeicao,_,HoraInicialAlmoco),
			horaToMinutosSegundos(HoraFinalRefeicao,_,HoraFinalAlmoco),
			HInicial2 >= HoraInicialAlmoco,
			HInicial2 =< HoraFinalAlmoco,
			HFinal1=<HoraInicialAlmoco,
			MaxTempoPausa is HInicial2-HoraInicialAlmoco,!.

% ================================================================================================================================

validaConstraintLimitesDriverDuty([],_,0,_).
validaConstraintLimitesDriverDuty([H|TDriverDuty],Intervalos,Penalizacao,Peso):-
								validaConstraintListaLimites(H,Intervalos,PenAtual,Peso),
								validaConstraintLimitesDriverDuty(TDriverDuty,Intervalos,PenResto,Peso),
								Penalizacao is PenAtual + PenResto
.

validaConstraintListaLimites(DriverDuty,[(HoraLimiteInferior,HoraLimiteSuperior)],Penalizacao,Peso):-
	validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso)
.

validaConstraintListaLimites(DriverDuty,[(HoraLimiteInferior,HoraLimiteSuperior)|RestoLimites],Penalizacao, Peso):-
	validaConstraintListaLimites(DriverDuty,RestoLimites,PenalizacaoResto,Peso),

	validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,PenalizacaoAtual,Peso),
	(
		PenalizacaoAtual = 0,
		Penalizacao is 0,
		!
		;
		PenalizacaoResto = 0,
		Penalizacao is 0,
		!
		;
		Penalizacao is PenalizacaoAtual + PenalizacaoResto
	)
.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,_,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					HInicial<SegundosLimiteInferior,
					HFinal=<SegundosLimiteInferior,
					Dif is HFinal - HInicial,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba dentro do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial<SegundosLimiteInferior,
					HFinal>SegundosLimiteInferior,
					HFinal=<SegundosLimiteSuperior,
					Dif is SegundosLimiteInferior-HInicial,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_,_),
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
					DriverDuty = (HInicial,HFinal,_,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>=SegundosLimiteInferior,
					HInicial=<SegundosLimiteSuperior,
					HFinal>=SegundosLimiteInferior,
					HFinal=<SegundosLimiteSuperior,
					Penalizacao is 0,!.

%Começa dentro e acaba fora do intervalo
validaConstraintLimites(DriverDuty,HoraLimiteInferior,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_,_),
					horaToMinutosSegundos(HoraLimiteInferior,_,SegundosLimiteInferior),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>=SegundosLimiteInferior,
					HInicial=<SegundosLimiteSuperior,
					HFinal>SegundosLimiteSuperior,
					Dif is HFinal-SegundosLimiteSuperior,
					Penalizacao is Dif*Peso,!.

%Começa fora e acaba fora do intervalo
validaConstraintLimites(DriverDuty,_,HoraLimiteSuperior,Penalizacao,Peso):-
					DriverDuty = (HInicial,HFinal,_,_),
					horaToMinutosSegundos(HoraLimiteSuperior,_,SegundosLimiteSuperior),
					HInicial>SegundosLimiteSuperior,
					HFinal>SegundosLimiteSuperior,
					Dif is HFinal-HInicial,
					Penalizacao is Dif*Peso,!.

% ================================================================================================================================

avalia_populacao([],[]):-!.
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
		avalia(Ind,V),!,
		avalia_populacao(Resto,Resto1),!.

avalia([],0):-!.
avalia(Ind,V):-
		
		criarAgendaTemporal(Ind,AgendaTemporal),

		criarAgendasDriverDuty(AgendaTotalDriverDuty),
		sort(1,@=<,AgendaTotalDriverDuty, AgendaTotalDriverDutyOrdenada),

		append(AgendaTemporal,AgendaTotalDriverDutyOrdenada,AgendaTrabalho),
		sort(1,@=<,AgendaTrabalho, AgendaTrabalhoOrdenada),

		obtemListaDriverDutyPorMotorista(AgendaTrabalhoOrdenada,ListaMatrizTrabalho),

		analisarGruposMotoristasWorkblocksDeIndividuo(ListaMatrizTrabalho,Pen1,AgendaViagem),
		
		append(AgendaTrabalho,AgendaViagem,AgendaPausa),		
		sort(1,@=<,AgendaPausa, AgendaPausaOrdenada),

		obtemListaDriverDutyPorMotorista(AgendaPausaOrdenada,ListaMatrizPausa),

		restricoesIndividuo(ListaMatrizTrabalho,ListaMatrizPausa,Pen2),

		V is Pen1 + Pen2, !
.

criarAgendasDriverDuty(AgendaTotal):-
	idVehicleDuty(IDVehicleDuty),
	lista_motoristas_nworkblocks(IDVehicleDuty,ListaNWorkblocks),
	criarAgendasDriverDuty(ListaNWorkblocks,AgendaTotal)
.

criarAgendasDriverDuty([],[]):-!.
criarAgendasDriverDuty([(DriverID,_)|ListaMotoristasNWorkblocks],AgendaTemporal):-
	(
		listaDriverIdRepetidoDriverDuty(DriverID,ListaWorkblocks,ListaDriverIdRepetido),

		criarAgendaTemporal1(ListaDriverIdRepetido,ListaWorkblocks,AgendaDriver),!
		;

		AgendaDriver = []
	),

	criarAgendasDriverDuty(ListaMotoristasNWorkblocks,AgendaResto),

	append(AgendaDriver,AgendaResto,AgendaTemporal)
.

restricoesIndividuo([],[],0):-!.

restricoesIndividuo([ListaDriverDutyTrabalho|RestoMatrizTrabalho],[],PenalizacaoTotal):-
	restricoesTrabalhoIndividuo(ListaDriverDutyTrabalho,PenTrabalho),
	restricoesIndividuo(RestoMatrizTrabalho,[],PenResto),
	PenalizacaoTotal is PenTrabalho + PenResto,!
.

restricoesIndividuo([],[ListaDriverDutyPausa|RestoMatrizPausa],PenalizacaoTotal):-
	restricoesPausaIndividuo(ListaDriverDutyPausa,PenPausa),
	restricoesIndividuo([],RestoMatrizPausa,PenResto),
	PenalizacaoTotal is PenPausa + PenResto,!
.

restricoesIndividuo([ListaDriverDutyTrabalho|RestoMatrizTrabalho],[ListaDriverDutyPausa|RestoMatrizPausa],PenalizacaoTotal):-
	restricoesTrabalhoIndividuo(ListaDriverDutyTrabalho,PenTrabalho),
	restricoesPausaIndividuo(ListaDriverDutyPausa,PenPausa),
	restricoesIndividuo(RestoMatrizTrabalho,RestoMatrizPausa,PenResto),
	PenalizacaoAtual is PenTrabalho + PenPausa,
	PenalizacaoTotal is PenalizacaoAtual + PenResto,!
.

validaHardConstraintLimitesDriverDuty([DriverDuty|RestoListaDriverDuty],Penalizacao):-
	penalizacao_hard_Grave(PesoGrave),

	DriverDuty = (_,_,DriverId,_),
	horariomotorista(DriverId,HInicial,HFinal,_,_),
	validaConstraintLimitesDriverDuty([DriverDuty|RestoListaDriverDuty],[(HInicial,HFinal)],Penalizacao,PesoGrave),!
.

validaSoftConstraintLimitesDriverDuty([DriverDuty|RestoListaDriverDuty],Penalizacao):-
	penalizacao_soft(PesoSoft),

	DriverDuty = (_,_,DriverId,_),
		
	findall((Inicio,Fim), tupleGasto(Inicio,Fim,_,DriverId), ListaIntervalos),
		
	validaConstraintLimitesDriverDuty([DriverDuty|RestoListaDriverDuty],ListaIntervalos,Penalizacao,PesoSoft),!
.

restricoesTrabalhoIndividuo([],0):-!.
restricoesTrabalhoIndividuo(ListaDriverDuty,PenalizacaoTotalAtual):-
	(validaHardConstraint4HSeguidasDriverDuty(ListaDriverDuty,P1),!;P1 is 0),
	validaHardConstraint8HTotaisDriverDuty(ListaDriverDuty,P2),
	validaHardConstraintLimitesDriverDuty(ListaDriverDuty,P3),
	validaSoftConstraintLimitesDriverDuty(ListaDriverDuty,P4),
	validaSobreposicaoConstraint(ListaDriverDuty,P5),
	P1_2 is P1 + P2,
	P2_3 is P1_2 + P3,
	P3_4 is P2_3 + P4,
	PenalizacaoTotalAtual is P3_4 + P5
.

restricoesPausaIndividuo([],0):-!.
restricoesPausaIndividuo(DriverDuty,PenalizacaoTotalAtual):-
		validaHardConstraint1HDescansoApos4H(DriverDuty,P1),
		validaHardConstraintAlmoco(DriverDuty,P2),
		validaHardConstraintJantar(DriverDuty,P3),
		P1_2 is P1 + P2,
		PenalizacaoTotalAtual is P1_2 + P3
.

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
	

% <====================================== Heuristica 1 - 4H Max ======================================>

criar_individuo_euristica_4hMax(ID,Individuo):-
	criar_lista_workblocks_ordenada_por_duracao(ID,ListaWorkblocksOrdenada),
	ordenaMotoristasPorNWorkblocks(ID,ListaMotoristasOrdenada),
	relacionar_lista_workblock_lista_motoristas(ListaWorkblocksOrdenada,ListaMotoristasOrdenada,ListaResultado),
	vehicleduty(ID,ListaOriginal),
	corrigir_individuo_gerado_na_atribuicao(ListaOriginal,ListaWorkblocksOrdenada,ListaResultado,Individuo).

corrigir_individuo_gerado_na_atribuicao([],_,_,[]).
corrigir_individuo_gerado_na_atribuicao([ID|ListaOriginal],ListaWorkblocksDuracao,ListaMotoristas,[Motorista|ListaResultado]):-
	nth1(PosWorkblock,ListaWorkblocksDuracao,(ID,_)),
	nth1(PosWorkblock,ListaMotoristas,Motorista),
	corrigir_individuo_gerado_na_atribuicao(ListaOriginal,ListaWorkblocksDuracao,ListaMotoristas,ListaResultado).

criar_lista_workblocks_ordenada_por_duracao(VehicleDutyID,ListaWorkblocksOrdenada):-
	vehicleduty(VehicleDutyID,ListaWorkblocks),
	criaWorkblockList_Duracao(ListaWorkblocks,ListaWorkblocksDuracao),
	ordenaWorkblockListDuracao(ListaWorkblocksDuracao,ListaWorkblocksOrdenada).

criaWorkblockList_Duracao([],[]).
criaWorkblockList_Duracao([ID|WorkBlockList],[WBDuracao|WorkBlockListDuracao]):-
	workblock(ID,_,HoraInicial,HoraFinal),
	Duracao is HoraFinal - HoraInicial,
	WBDuracao = (ID,Duracao),
	criaWorkblockList_Duracao(WorkBlockList,WorkBlockListDuracao).

ordenaWorkblockListDuracao([],[]).
ordenaWorkblockListDuracao(WorkBlockListDuracao,ListaOrdenada):-
	sort(2,@=<,WorkBlockListDuracao, ListaOrdenada).

ordenaMotoristasPorNWorkblocks(VehicleID,ListaMotoristasOrdenada):-
	lista_motoristas_nworkblocks(VehicleID,ListaMotoristas),
	sort(2,@>=,ListaMotoristas, ListaMotoristasOrdenada).

ordenaListaMotoristasPorNWorkblocks(ListaMotoristas,ListaMotoristasOrdenada):-
	sort(2,@>=,ListaMotoristas, ListaMotoristasOrdenada).

relacionar_lista_workblock_lista_motoristas(ListaWorkblocksOrdenada,ListaMotoristasOrdenada,ListaResultado):-
	relacionar_lista_workblock_lista_motoristas1(ListaWorkblocksOrdenada,ListaMotoristasOrdenada,0,ListaResultado).

relacionar_lista_workblock_lista_motoristas1([],[],_,[]):-!.
relacionar_lista_workblock_lista_motoristas1([Workblock|ListaWorkblocks],[(Motorista,NWorkblocks)|ListaMotoristas],DuracaoAtual,[Motorista|ListaResultado]):-
	NWorkblocks > 0,
	Workblock = (_,DuracaoWorkBlock),
	NovaDuracaoMotorista = DuracaoAtual + DuracaoWorkBlock,
	horaToMinutosSegundos(4,_,Sec4H),
	(DuracaoAtual >= Sec4H, !; NovaDuracaoMotorista =< Sec4H),
	NWorkblocks1 is NWorkblocks - 1,
	MotoristaAtualizado = (Motorista,NWorkblocks1),
	relacionar_lista_workblock_lista_motoristas1(ListaWorkblocks,[MotoristaAtualizado|ListaMotoristas],NovaDuracaoMotorista,ListaResultado).

relacionar_lista_workblock_lista_motoristas1(ListaWorkblocks,[(_,NWorkblocks)|ListaMotoristas],_,ListaResultado):-
	NWorkblocks is 0,
	relacionar_lista_workblock_lista_motoristas1(ListaWorkblocks,ListaMotoristas,0,ListaResultado).

relacionar_lista_workblock_lista_motoristas1(ListaWorkblocks,[M|ListaMotoristas],_,ListaResultado):-
	append(ListaMotoristas,[M],NovaListaMotoristas),
	relacionar_lista_workblock_lista_motoristas1(ListaWorkblocks,NovaListaMotoristas,0,ListaResultado).


% <====================================== Heuristica 2 - Alternada ======================================>

criar_individuo_euristica_alternada(ID,Individuo):-
	ordenaMotoristasPorNWorkblocks(ID,ListaMotoristasOrdenada),
	atribuir_workblock_motorista_alternados(ListaMotoristasOrdenada,Individuo),!
.

atribuir_workblock_motorista_alternados([],[]):-!.
atribuir_workblock_motorista_alternados([(IDMotorista,NWorkblocks)],Lista):-
	preencheListaNVezes(IDMotorista,NWorkblocks,Lista),
	!
.

atribuir_workblock_motorista_alternados([Motorista1,Motorista2|ListaMotoristasOrdenada],[IDMotorista1|ListaResultado]):-
	Motorista1 = (IDMotorista1,NWorkblocksMotorista1),

	NWorkblocksMotorista11 is NWorkblocksMotorista1-1,

	MotoristaAtualizado = (IDMotorista1,NWorkblocksMotorista11),

	(
		NWorkblocksMotorista11>0,
		append([MotoristaAtualizado],ListaMotoristasOrdenada,NovaLista), !
		;
		NovaLista = ListaMotoristasOrdenada
	),

	append([Motorista2],NovaLista,ProximaLista),

	ordenaListaMotoristasPorNWorkblocks(ProximaLista,ProximaListaOrdenada),

	(
		ProximaListaOrdenada = [ProximoMotorista1, ProximoMotorista2|Resto],
		ProximoMotorista1 = (ProximoMotoristaID,_),
		ProximoMotoristaID = IDMotorista1, 
		ProximaListaOrdenadaCorrigida = [ProximoMotorista2, ProximoMotorista1|Resto],
		!
		;
		ProximaListaOrdenadaCorrigida = ProximaListaOrdenada
	),

	atribuir_workblock_motorista_alternados(ProximaListaOrdenadaCorrigida,ListaResultado).

% ================================================================================================================== %

validaSobreposicaoConstraint([_],0):-!.
validaSobreposicaoConstraint([DriverDuty1, DriverDuty2|Resto],Penalizacao):-
		penalizacao_paradoxo(Peso),
		
		DriverDuty1 = (_, HoraFinal1, _, _),
		DriverDuty2 = (HoraInicio2, _, _, _),

		(
			HoraInicio2 < HoraFinal1,
			Diff is HoraFinal1 - HoraInicio2,
			PenAtual is Diff * Peso,
			!
			;
			PenAtual is 0
		),

		validaSobreposicaoConstraint([DriverDuty2|Resto],PenResto),
		Penalizacao is PenAtual + PenResto,!
.

% ================================================================================================================== %

constroiListaMotoristaWorkblock(Individuo, ListaResultado):-
	idVehicleDuty(IDVehicleDuty),
	vehicleduty(IDVehicleDuty,ListaWorkblocks),
	juntaMotoristasComWorkblocks(Individuo,ListaWorkblocks,ListaResultado)
.

juntaMotoristasComWorkblocks([],[],[]):-!.
juntaMotoristasComWorkblocks([Motorista|RestoMotoristas],[Workblock|RestoWorkblocks],[Atual|ResultadoRestante]):-
	juntaMotoristasComWorkblocks(RestoMotoristas,RestoWorkblocks,ResultadoRestante),
	workblock(Workblock,_,HoraInicioWorkblock,HoraFimWorkblock),
	Atual = (HoraInicioWorkblock,HoraFimWorkblock,Motorista,Workblock),
	!
.

juntaMotoristasComWorkblocks([_|RestoMotoristas],[_|RestoWorkblocks],Resultado):-
	juntaMotoristasComWorkblocks(RestoMotoristas,RestoWorkblocks,Resultado),!
.

juntaDriverDutiesComWorkblocks(ListaResutado):-
	idVehicleDuty(IDVehicleDuty),
	lista_motoristas_nworkblocks(IDVehicleDuty,ListaNWorkblocks),
	juntaDriverDutiesComWorkblocks1(ListaNWorkblocks,ListaResutado)
.

juntaDriverDutiesComWorkblocks1([],[]).
juntaDriverDutiesComWorkblocks1([(DriverDuty,_)|RestoDriverDuties],ListaResutado):-
	listaDriverIdRepetidoDriverDuty(DriverDuty,ListaNWorkblocks,Individuo),
	juntaMotoristasComWorkblocks(Individuo,ListaNWorkblocks,MotoristasComWorkblocks),
	juntaDriverDutiesComWorkblocks1(RestoDriverDuties,ResultadoResto),
	append(MotoristasComWorkblocks,ResultadoResto,ListaResutado)
.

listaDriverIdRepetidoDriverDuty(DriverID,ListaWorkblocks,ListaDriverIdRepetido):-
	(driverduty(DriverID,ListaWorkblocks),!;ListaWorkblocks = []),
	length(ListaWorkblocks, SizeListaWorkblocks),
	preencheListaNVezes(DriverID,SizeListaWorkblocks,ListaDriverIdRepetido)
.

juntaIndividuoComDriverDuties(Individuo,ListaAgrupadaOrdenada):-
	constroiListaMotoristaWorkblock(Individuo,IndividuoWorkblocks),
	juntaDriverDutiesComWorkblocks(ListaDriverDutiesComWorkblocks),
	append(IndividuoWorkblocks,ListaDriverDutiesComWorkblocks,ListaResutado),
	sort(1,@=<,ListaResutado,ListaResutadoOrdenada),
	agrupaListaDriverDutyWorkblockPorMotorista(ListaResutadoOrdenada,ListaAgrupada),
	ordenaListaGruposMotoristaWorkblocksAgrupada(ListaAgrupada,ListaAgrupadaOrdenada)
.

agrupaListaDriverDutyWorkblockPorMotorista([X], [X]):-!.
agrupaListaDriverDutyWorkblockPorMotorista([MotoristaWorkblock1,MotoristaWorkblock2|AglomeradoOrdenado], Matriz):-
    MotoristaWorkblock2 = (Mot, _),
	
    (
        is_list(MotoristaWorkblock1),
        MotoristaWorkblock1 = [(Mot, _)|_],
        append(MotoristaWorkblock1,[MotoristaWorkblock2],A3),
        !
    ;
        MotoristaWorkblock1 = (Mot, _),
        A3 = [MotoristaWorkblock1,MotoristaWorkblock2]
    ),
    agrupaListaDriverDutyWorkblockPorMotorista([A3|AglomeradoOrdenado], Matriz),
    !
.

agrupaListaDriverDutyWorkblockPorMotorista([MotoristaWorkblock1,MotoristaWorkblock2|AglomeradoOrdenado], [A|Matriz]):-
    (
        not(is_list(MotoristaWorkblock1)),
        A = [MotoristaWorkblock1],
        !
    ;  
        is_list(MotoristaWorkblock1),
        A = MotoristaWorkblock1
    ),
    agrupaListaDriverDutyWorkblockPorMotorista([[MotoristaWorkblock2]|AglomeradoOrdenado], Matriz),
    !
.

ordenaListaGruposMotoristaWorkblocksAgrupada([Grupo],[GrupoOrdenado]):-
	sort(2,@=<,Grupo,GrupoOrdenado),!
.
ordenaListaGruposMotoristaWorkblocksAgrupada([Grupo|RestoGrupo],[GrupoOrdenado|RestoGrupoOrdenado]):-
	sort(2,@=<,Grupo,GrupoOrdenado),
	ordenaListaGruposMotoristaWorkblocksAgrupada(RestoGrupo,RestoGrupoOrdenado),!
.

analisarGruposMotoristasWorkblocksDeIndividuo(ListaGrupos, Penalizacao,AgendaViagem):-
	analisarGrupoMotoristasWorkblocksDeIndividuo(ListaGrupos,P,AgendaViagem), 
	penalizacao_paradoxo(Peso),
	Penalizacao is P * Peso,
	!
.

analisarGrupoMotoristasWorkblocksDeIndividuo([],0,[]):-!.

analisarGrupoMotoristasWorkblocksDeIndividuo([Grupo|RestoGrupo],Penalizacao,AgendaViagem):-
	Grupo = [MotoristaWorkblock1, MotoristaWorkblock2|RestoDesteGrupo],
	MotoristaWorkblock1 = (_,HoraFimWorkblock1,DriverID,IDWorkblock1),
	MotoristaWorkblock2 = (HoraWorkblock2,_,DriverID,IDWorkblock2),

	workblock(IDWorkblock1, ListaViagens1,_,_),
	workblock(IDWorkblock2, [Viagem2|_],_,_),

	last(ListaViagens1,Viagem1),

	viagem(_,Viagem1,HorasPassagem1),
	noMaisProximo(HoraFimWorkblock1,HorasPassagem1,NoIdMaisProximo1,_),!,
	
	viagem(_,Viagem2,HorasPassagem2),
	noMaisProximo(HoraWorkblock2,HorasPassagem2,NoIdMaisProximo2,_),!,

	noID(NoIdMaisProximo1,No1Abv),
	noID(NoIdMaisProximo2,No2Abv),

	(
		aStar(No1Abv,No2Abv,HoraFimWorkblock1,_,Tempo),
		TempoComDeslocacao is Tempo + HoraFimWorkblock1,
		(
			TempoComDeslocacao > HoraWorkblock2,
			PenAtual is TempoComDeslocacao - HoraWorkblock2,
			AgendaDeslocacaoAtual = [(HoraFimWorkblock1,TempoComDeslocacao,DriverID,_)],
			!
		;
			PenAtual is 0,
			AgendaDeslocacaoAtual = []
		),
		!
	;
		PenAtual is HoraWorkblock2 - HoraFimWorkblock1,
		AgendaDeslocacaoAtual = [(HoraFimWorkblock1,HoraWorkblock2,DriverID,_)],!
	),


	analisarGrupoMotoristasWorkblocksDeIndividuo([[MotoristaWorkblock2|RestoDesteGrupo]|RestoGrupo],PenResto,AgendaViagemResto),
	
	Penalizacao is PenAtual + PenResto,
	append(AgendaDeslocacaoAtual,AgendaViagemResto,AgendaViagem),!
.

analisarGrupoMotoristasWorkblocksDeIndividuo([_|RestoGrupo],P,AgendaViagem):-
	analisarGrupoMotoristasWorkblocksDeIndividuo(RestoGrupo,P,AgendaViagem),!
.

noMaisProximo(Hora,[PrimeiroPassingTime|RestoPassingTimes],NodeResultadoID, HoraPassagemNodeResultado):-
	noMaisProximo(Hora,[PrimeiroPassingTime|RestoPassingTimes], _, NodeResultadoID, HoraPassagemNodeResultado), !
.

noMaisProximo(Hora,[PassingTime|RestoPassingTimes],MenorDiferenca,MenorNo,HoraPassagemMenorNo):-
	noMaisProximo(Hora,RestoPassingTimes,MenorDiferencaResto,MenorNoResto,HoraPassagemMenorNoResto),
	
	PassingTime = (NodeID,NodeTime),
	Dif is abs(NodeTime - Hora),

	(
		Dif > MenorDiferencaResto,
		MenorDiferenca = MenorDiferencaResto,
		MenorNo = MenorNoResto,
		HoraPassagemMenorNo = HoraPassagemMenorNoResto, !
		;
		MenorDiferenca = Dif,
		MenorNo = NodeID,
		HoraPassagemMenorNo = NodeTime
	),
	!
.

noMaisProximo(Hora,[(NodeID,NodeTime)],Dif,NodeID,NodeTime):-
	Dif is abs(NodeTime - Hora),!
.

teste(MensagemRetorno):-
	importa,
	startup(MensagemRetorno),!,
	chamarGenetico(5,10,50,50,60,0),!
.
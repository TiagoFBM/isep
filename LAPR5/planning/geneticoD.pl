criarTodosRangevd:-
    findall(VehicleDutyID,vehicleduty(VehicleDutyID,_),ListaVehicleDutyIDs),
    criarTodosRangevd(ListaVehicleDutyIDs)
.

criarTodosRangevd([]):-!.
criarTodosRangevd([VehicleDutyID|TListaVehicleDutyIDs]):-
    criarRangevd(VehicleDutyID),
    criarTodosRangevd(TListaVehicleDutyIDs)
.

criarRangevd(VehicleDutyID):-
    vehicleduty(VehicleDutyID, [FirstWorkblock|TListaWorkblocks]),
    (last(TListaWorkblocks, LastWorkblock), ! ; LastWorkblock = FirstWorkblock),
    workblock(FirstWorkblock,_,StartTimeFirstWorkblock,_),
    workblock(LastWorkblock,_,_,EndTimeLastWorkblock),
    
    (retract(rangevd(VehicleDutyID,_,_));true),
    asserta(rangevd(VehicleDutyID,StartTimeFirstWorkblock,EndTimeLastWorkblock))
.

calcularCargaTotal:-
    findall(S,(rangevd(_,Start,End), S is End-Start),ListaDuracoesRangevd),
    somatorio(ListaDuracoesRangevd,CargaTotal),
    
    (retract(cargaTotal(_));true),
    asserta(cargaTotal(CargaTotal))
.

calcularCapacidadeTotal:-
    findall(Duracao,horariomotorista(_,_,_,Duracao,_),ListaDuracoesHorarioMotorista),
    somatorio(ListaDuracoesHorarioMotorista,CapacidadeTotal),
    
    (retract(capacidadeTotal(_));true),
    asserta(capacidadeTotal(CapacidadeTotal))
.

verificarMargem(Mensagem):-
    margemMinima(MargemMinima),
    (retract(capacidadeTotal(CapacidadeTotal));true),
    (retract(cargaTotal(CargaTotal));true),
    PercentagemOcupado is (CargaTotal/CapacidadeTotal)*100,
    PercentagemLivre is 100 - PercentagemOcupado,

    (
        PercentagemLivre > MargemMinima,
        Mensagem = "A Margem atual é superior à minima ", !
        ;
        Mensagem = "A Margem atual é inferior à minima "
    ),!
        
    %write("Percentagem Não Usada = "),write(PercentagemLivre),nl,nl,!
.

criarTodosTuples:-
    retractall(tuple(_,_,_,_)),
    findall(IDMotorista,horariomotorista(IDMotorista,_,_,_,_),ListaIDMotoristas),
    criarTuples(ListaIDMotoristas)
.

criarTuples([]):-!.
criarTuples([H|RestoListaIDMotoristas]):-
    criarTuple(H),
    criarTuples(RestoListaIDMotoristas)
.

criarTuple(IDMotorista):-
    horariomotorista(IDMotorista, HorarioPodeComecar, HorarioPodeTerminar, DuracaoConducao, ListaDuracaoBlocosMotorista),

    length(ListaDuracaoBlocosMotorista, SizeLista),

    (
        SizeLista > 1,
        criarDoisTuplesDeMotorista(IDMotorista, HorarioPodeComecar, HorarioPodeTerminar, DuracaoConducao, ListaDuracaoBlocosMotorista), !
        ;
        criarTupleUnico(IDMotorista, HorarioPodeComecar, HorarioPodeTerminar, DuracaoConducao, ListaDuracaoBlocosMotorista)
    )
.

criarTupleUnico(IDMotorista, HorarioPodeComecar, HorarioPodeTerminar, _, [DuracaoBlocoMotorista]):-
    asserta(tuple(HorarioPodeComecar,HorarioPodeTerminar,DuracaoBlocoMotorista,IDMotorista))
.

criarDoisTuplesDeMotorista(IDMotorista, HorarioPodeComecar, HorarioPodeTerminar, _, [PrimeiraDuracaoBlocoMotorista, SegundaDuracaoBlocoMotorista]):-
    FimIntervaloInicial is HorarioPodeComecar + PrimeiraDuracaoBlocoMotorista,
    InicioIntervaloFinal is HorarioPodeTerminar - SegundaDuracaoBlocoMotorista,
    asserta(tuple(HorarioPodeComecar,FimIntervaloInicial,PrimeiraDuracaoBlocoMotorista,IDMotorista)),
    asserta(tuple(InicioIntervaloFinal,HorarioPodeTerminar,SegundaDuracaoBlocoMotorista,IDMotorista))
.

ordenaVehicleDutyPorHoraInicio(ListaVehicleDutiesOrdenada):-
    findall((VehicleDutyID,HoraInicio),(
        vehicleduty(VehicleDutyID,_),
        horaInicioDeVehicleDuty(VehicleDutyID,HoraInicio)
    ),
    ListaVehicleDuties),
    sort(2, @=<, ListaVehicleDuties, ListaVehicleDutiesOrdenada)
.

horaInicioDeVehicleDuty(VehicleDutyID, HoraInicio):-
    vehicleduty(VehicleDutyID, [PrimeiroWorkblock | _]),
    workblock(PrimeiroWorkblock,_,HoraInicio,_)
.

ordenaTuplesPorHoraInicioBloco(ListaTuplesOrdenada):-
    findall((HoraInicioBloco, HoraFimBloco, NumeroSegundosTrabalhoEmBloco, DriverID),
    retract(tuple(HoraInicioBloco, HoraFimBloco, NumeroSegundosTrabalhoEmBloco, DriverID)),
    ListaTuples),

    sort(1, @=<, ListaTuples, ListaTuplesOrdenada)
.

workblockMaiorDuracaoVehicleduty(VehicleDutyID, MaiorDuracao):-
    vehicleduty(VehicleDutyID, WorkblockIDList),
    criaWorkblockList_Duracao(WorkblockIDList,WorkBlockListDuracao),
    sort(2, @>=, WorkBlockListDuracao, [WorkblockMaiorDuracao|_]),
    WorkblockMaiorDuracao = (_,MaiorDuracao)
.

criarListaNWorkblocks:-
    retractall(lista_motoristas_nworkblocks(_,_)),
    ordenaVehicleDutyPorHoraInicio(ListaOrdenadaVehicleDuties),
    ordenaTuplesPorHoraInicioBloco(ListaOrdenadaTuples),
    %write_array(ListaOrdenadaTuples),
    criarListaNWorkblocks(ListaOrdenadaVehicleDuties,ListaOrdenadaTuples)
.

criarListaNWorkblocks([], []).
criarListaNWorkblocks([], [(A,B,C,D)|RestoTuplesGastos]):-
    asserta(tuple(A,B,C,D)),
    criarListaNWorkblocks([],RestoTuplesGastos)
.

criarListaNWorkblocks([PrimeiroVehicleDuty|RestoVehicleDuties], [PrimeiroTuple | RestoTuples]):-
    PrimeiroVehicleDuty = (VehicleDutyID,_),
    workblockMaiorDuracaoVehicleduty(VehicleDutyID,MaiorDuracao),
    PrimeiroTuple = (HInicioPrimeiroTuple,HFimPrimeiroTuple,DuracaoPrimeiroTuple,DriverID),
    NWorkblocks is floor(DuracaoPrimeiroTuple / MaiorDuracao),
    (NWorkblocks > 0; writeln("Existe um workblock demasiado grande para os horários de disponibilidade dos motoristas."), fail),
    (
        retract(lista_motoristas_nworkblocks(VehicleDutyID,ListaMotoristasNWorkblocks)),
        updateDriverListaMotoristasNWorkblocks(DriverID,NWorkblocks,ListaMotoristasNWorkblocks,NovaListaMotoristasNWorkblocks), !
        ;
        NovaListaMotoristasNWorkblocks = [(DriverID,NWorkblocks)]
    ),

    asserta(tupleGasto(HInicioPrimeiroTuple,HFimPrimeiroTuple,DuracaoPrimeiroTuple,DriverID)),

    (
        checkListaNWorkblocksPreenchida(VehicleDutyID, NovaListaMotoristasNWorkblocks, NovaListaCorrigida),
        asserta(lista_motoristas_nworkblocks(VehicleDutyID,NovaListaCorrigida)),
        criarListaNWorkblocks(RestoVehicleDuties,RestoTuples), !
        ;
        asserta(lista_motoristas_nworkblocks(VehicleDutyID,NovaListaMotoristasNWorkblocks)),
        criarListaNWorkblocks([PrimeiroVehicleDuty|RestoVehicleDuties],RestoTuples), !
    )
.

updateDriverListaMotoristasNWorkblocks(DriverID, NWorkblocks, [],[(DriverID,NWorkblocks)]).

updateDriverListaMotoristasNWorkblocks(DriverID, NovoNWorkblocks, [MotoristaNWorkblocks|ListaMotoristasNWorkblocks],[NovoMotoristaNWorkblocks|ListaMotoristasNWorkblocks]):-
    MotoristaNWorkblocks = (DriverID,NWorkblocks),
    NSomado is NWorkblocks + NovoNWorkblocks,
    NovoMotoristaNWorkblocks = (DriverID,NSomado)
.

updateDriverListaMotoristasNWorkblocks(DriverID, NWorkblocks, [MotoristaNWorkblock|ListaMotoristasNWorkblocks],[MotoristaNWorkblock|ListaNovaMotoristaNWorkblocks]):-
    updateDriverListaMotoristasNWorkblocks(DriverID, NWorkblocks, ListaMotoristasNWorkblocks,ListaNovaMotoristaNWorkblocks).

checkListaNWorkblocksPreenchida(VehicleDutyID, ListaMotoristasNWorkblocks, NovaListaCorrigida):-
    vehicleduty(VehicleDutyID,ListaWorkblocks),
    length(ListaWorkblocks, NumberWorkblocks),
    somaWorkblocksListaMotoristaNWorkblocks(ListaMotoristasNWorkblocks, Soma),    
    Soma >= NumberWorkblocks,

    reverse(ListaMotoristasNWorkblocks, [Last|ListaMotoristasNWorkblocksReverse]),
    Last = (MotoristaID, NWorkblocks),
    Dif is Soma - NumberWorkblocks,
    N is NWorkblocks - Dif,

    NovaListaReverse = [(MotoristaID,N)|ListaMotoristasNWorkblocksReverse],

    reverse(NovaListaReverse, NovaListaCorrigida)
.

somaWorkblocksListaMotoristaNWorkblocks([],0).
somaWorkblocksListaMotoristaNWorkblocks([MotoristaNWorkblock|ListaMotoristaNWorkblocks],Soma):-
    somaWorkblocksListaMotoristaNWorkblocks(ListaMotoristaNWorkblocks, SomaResto),
    MotoristaNWorkblock = (_,NMotorista),
    Soma is SomaResto + NMotorista
.

startup(Mensagem):-
    criarTodosRangevd,
    calcularCargaTotal,
    calcularCapacidadeTotal,
    verificarMargem(Mensagem),
    criarTodosTuples,
    criarListaNWorkblocks,!.

chamarGenetico(NG,DP,P1,P2,T,PI):-
    inicializa(NG,DP,P1,P2,T,PI),
    findall(_,
    (
        vehicleduty(VehicleDutyID,ListaWorkblocksVehicleDuty),
        genetico(VehicleDutyID,IndR*_,_),
        %writeln(IndR),
        %writeln(Avaliacao),
        criaDriverDuty(IndR,ListaWorkblocksVehicleDuty)
    ),
    L),
    length(L, SizeL),
    SizeL > 0,
    corrigirDriverDuties
.

criaDriverDuty([],[]):-!.
criaDriverDuty([DriverId|RestoDriverID],[WorkBlock|RestoWorkblocks]):-
    (
        retract(driverduty(DriverId,WorkblocksDriverDuty)),
        append(WorkblocksDriverDuty,[WorkBlock],ListaNovaWorkblocks),!
        ;
        ListaNovaWorkblocks = [WorkBlock]
    ),
    asserta(driverduty(DriverId,ListaNovaWorkblocks)),
    criaDriverDuty(RestoDriverID,RestoWorkblocks),!
.

corrigirDriverDuties:-
    corrigirDriverDuties(10)
.

corrigirDriverDuties(0).
corrigirDriverDuties(N):-
    findall(_, (
        driverduty(DriverId,_),
        corrigirDriverDuty(DriverId)
    ), _),
    N1 is N-1,
    corrigirDriverDuties(N1),!
.

corrigirDriverDuty(DriverId):-
    corrigir4H8HDescanso(DriverId),
    corrigirRefeicoes(DriverId),
    percorrerWorkblocks(DriverId)
    %corrigirHorarios(DriverId)
.

percorrerWorkblocks(DriverId):-
    criarAgendasDriverDuty([(DriverId,_)],AgendaDriverDuty),
    sort(1, @=<, AgendaDriverDuty, AgendaDriverDutyOrdenada),

    agendaCompleta(AgendaCompleta),

    percorrerWorkblocks(AgendaDriverDutyOrdenada,AgendaCompleta)
.

percorrerWorkblocks([],_).
percorrerWorkblocks([_],_).
percorrerWorkblocks([Workblock1,Workblock2|_],AgendaCompleta):-
    analisarGrupoMotoristasWorkblocksDeIndividuo([[Workblock1,Workblock2]],PenDeslocacao,_),
    validaSobreposicaoConstraint([Workblock1,Workblock2],PenSobreposicao),
    PenTotal is PenDeslocacao + PenSobreposicao,
    PenTotal > 0,
    corrigirDriverDutyDeslocacaoSobreposicao(Workblock1,Workblock2,AgendaCompleta)
.

percorrerWorkblocks([_|ListaWorkblocks],AgendaCompleta):-
    percorrerWorkblocks(ListaWorkblocks,AgendaCompleta),!
.

corrigirDriverDutyDeslocacaoSobreposicao(Workblock1,Workblock2,AgendaCompleta):-
    random_between(1, 2, R),

    (
        R=1, WorkblockAlterar = Workblock1
        ;
        R=2, WorkblockAlterar = Workblock2
    ),

    encaixarWorkblockAgenda(WorkblockAlterar,AgendaCompleta)
.

encaixarWorkblockAgenda(Workblock,Agenda):-
    encaixarWorkblockAgenda(Workblock,Agenda,[])
.

encaixarWorkblockAgenda(Workblock,[],[]):-
    Workblock = (_,_,DriverID,WorkblockID),

    findall((HoraInicio,HoraFim,Duracao,DriverId), tuple(HoraInicio,HoraFim,Duracao,DriverId), ListaTuples),
    melhorTupleParaWorkblock(Workblock,ListaTuples,Tuples),

    random_permutation(Tuples,TuplesBaralhados),
    TuplesBaralhados = [Tuple|_],

    retract(driverduty(DriverID, Workblocks)),

    delete(Workblocks, WorkblockID, WorkblocksBNovos),
        
    asserta(driverduty(DriverID, WorkblocksBNovos)),

    atribuirWorkblockTuple(Workblock,Tuple)
.

encaixarWorkblockAgenda(WorkBlock,[],WorkBlocksValidos):-
    updateWorkblock(WorkBlock,WorkBlocksValidos)
.

encaixarWorkblockAgenda(Workblock,[WB1,WB2|RestoAgenda],WorkblocksValidos):-
    Workblock = (HoraInicio1,HoraFim1,Motorista1,_),
    WB1 = (HoraInicio2,HoraFim2,Motorista2,_),
    WB2 = (HoraInicio3,HoraFim3,Motorista3,_),

    Motorista1 \= Motorista2,
    Motorista1 \= Motorista3,

    (
        HoraFim1 =< HoraInicio2,
        analisarGrupoMotoristasWorkblocksDeIndividuo([[Workblock,WB1]],PenDeslocacao,_),
        PenDeslocacao = 0,
        encaixarWorkblockAgenda(Workblock,RestoAgenda,[WB1|WorkblocksValidos]),
        !
    ;
        HoraInicio1 >= HoraFim2,
        HoraFim1 =< HoraInicio3,
        analisarGrupoMotoristasWorkblocksDeIndividuo([[WB1,Workblock]],Pen1,_),
        analisarGrupoMotoristasWorkblocksDeIndividuo([[Workblock,WB2]],Pen2,_),
        encaixarWorkblockAgenda(Workblock,RestoAgenda,[WB1,WB2|WorkblocksValidos]),
        PenDeslocacao is Pen1 + Pen2,
        PenDeslocacao = 0,
        !
    ;
        HoraInicio1 >= HoraFim3,
        analisarGrupoMotoristasWorkblocksDeIndividuo([[WB2,Workblock]],PenDeslocacao,_),
        encaixarWorkblockAgenda(Workblock,RestoAgenda,[WB2|WorkblocksValidos]),
        PenDeslocacao = 0,
        !
    )
.

encaixarWorkblockAgenda(Workblock,[_|RestoAgenda],WorkblocksValidos):-
    encaixarWorkblockAgenda(Workblock,RestoAgenda,WorkblocksValidos)
.

updateWorkblock(WorkBlock,WorkBlocksValidos):-
    WorkBlock = (_,_,Motorista,WorkblockId),

    random_permutation(WorkBlocksValidos,WorkBlocksValidosBaralhados),
    WorkBlocksValidosBaralhados = [NovoWorkblock|_],

    NovoWorkblock = (_,_,MotoristaNovo,_),

    retract(driverduty(Motorista,ListaWorkblocksMotorista)),

    delete(ListaWorkblocksMotorista,WorkblockId,ListaSemWorkblockAntigo),
    asserta(driverduty(Motorista,ListaSemWorkblockAntigo)),

    retract(driverduty(MotoristaNovo,ListaWorkblocksMotoristaNovo)),
    append(ListaWorkblocksMotoristaNovo,[WorkblockId],ListaNovaWorkblocksMotoristaNovo),

    asserta(driverduty(MotoristaNovo,ListaNovaWorkblocksMotoristaNovo))
.

atribuirWorkblockTuple(Workblock,Tuple):-
    Workblock = (HoraInicioWorkblock,HoraFimWorkblock,_,WorkBlockId),
    Tuple = (HoraInicioTuple,HoraFimTuple,DuracaoTuple,DriverIdTuple),

    NovaDuracaoTuple is HoraFimWorkblock - HoraInicioWorkblock,

    retract(tuple(HoraInicioTuple,HoraFimTuple,DuracaoTuple,DriverIdTuple)),
    asserta(tupleGasto(HoraInicioWorkblock,HoraFimWorkblock,NovaDuracaoTuple,DriverIdTuple)),

    (retract(driverduty(DriverIdTuple,ListaWorkblocksDriverId)),!;ListaWorkblocksDriverId=[]),
    asserta(driverduty(DriverIdTuple,[WorkBlockId|ListaWorkblocksDriverId])),

    DurInicio is HoraInicioWorkblock - HoraInicioTuple,

    (
        DurInicio > 0,
        asserta(tuple(HoraInicioTuple,HoraInicioWorkblock,DurInicio,DriverIdTuple)),
        !
    ;
        true
    ),

    DurFim is HoraFimTuple - HoraFimWorkblock,

    (
        DurFim > 0,
        asserta(tuple(HoraFimWorkblock,HoraFimTuple,DurFim,DriverIdTuple)),
        !
    ;
        true
    )
.

corrigirHorarios(DriverId):-
    criarAgendasDriverDuty([(DriverId,_)],AgendaDriverDuty),
    sort(1,@=<,AgendaDriverDuty,AgendaDriverDutyOrdenada),

    penalizacao_hard_Grave(Peso),
    horariomotorista(DriverId,HoraInicial,HoraFinal,_,_),

    corrigirHorarios(AgendaDriverDutyOrdenada,HoraInicial,HoraFinal,Peso,WorkblockParaCorrigir1),
    corrigirWorkblocks(WorkblockParaCorrigir1)
.

corrigirHorarios([_],_,_,_,[]).
corrigirHorarios([Workblock|Resto],HoraInicio,HoraFim,Peso,WorkblockList):-
    corrigirHorarios(Resto,HoraInicio,HoraFim,Peso,Workblocks),

    validaConstraintLimitesDriverDuty([Workblock],[(HoraInicio,HoraFim)],Penalizacao,Peso),
    
    (
        Penalizacao > 0,
        WorkblockList = [Workblock|Workblocks],
        !
    ;
        WorkblockList = []
    )
.


corrigirRefeicoes(DriverId):-
    criarAgendasDriverDuty([(DriverId,_)],AgendaDriverDuty),
    sort(1,@=<,AgendaDriverDuty,AgendaDriverDutyOrdenada),

    corrigirRefeicoes(AgendaDriverDutyOrdenada,11,15,WorkblockParaCorrigir1),
    corrigirWorkblocks(WorkblockParaCorrigir1),

    agendaCompleta(AgendaCompletaNova),

    corrigirRefeicoes(AgendaCompletaNova,18,22,WorkblockParaCorrigir2),
    corrigirWorkblocks(WorkblockParaCorrigir2)
.

corrigirRefeicoes([_],[]).
corrigirRefeicoes(AgendaDriverDuty,HoraInicial,HoraFinal,WorkblockList):-

    validaHardConstraintRefeicao(AgendaDriverDuty,HoraInicial,HoraFinal,Penalizacao),

    (
        Penalizacao > 0,
        removerWorkblockEntreLimitesAgenda(AgendaDriverDuty,HoraInicial,HoraFinal,MaiorWorkblock,RestoAgenda),

        WorkblockList = [MaiorWorkblock|RestoWorkblocks],
        corrigirRefeicoes(RestoAgenda,RestoWorkblocks),
        !
    ;
        WorkblockList = []
    )
.


corrigir4H8HDescanso(DriverId):-
    criarAgendasDriverDuty([(DriverId,_)],AgendaDriverDuty),
    sort(1,@=<,AgendaDriverDuty,AgendaDriverDutyOrdenada),

    corrigir4H8HDescanso(AgendaDriverDutyOrdenada,WorkblockParaCorrigir),

    corrigirWorkblocks(WorkblockParaCorrigir)
.

corrigirWorkblocks([]).
corrigirWorkblocks([Workblock|Resto]):-
    agendaCompleta(AgendaCompletaOrdenada),
    encaixarWorkblockAgenda(Workblock,AgendaCompletaOrdenada),
    corrigirWorkblocks(Resto)
.

corrigir4H8HDescanso([_],[]).
corrigir4H8HDescanso(AgendaDriverDuty,WorkblockList):-

    validaHardConstraint4HSeguidasDriverDuty(AgendaDriverDuty,Pen4H),
    validaHardConstraint8HTotaisDriverDuty(AgendaDriverDuty,Pen8H),
    validaHardConstraint1HDescansoApos4H(AgendaDriverDuty,Pen1HDescanso),

    PenAtual is Pen4H + Pen8H + Pen1HDescanso,

    removerMaiorWorkblockAgenda(AgendaDriverDuty,MaiorWorkblock,RestoAgenda),

    (
        PenAtual > 0,
        WorkblockList = [MaiorWorkblock|RestoWorkblocks],
        corrigir4H8HDescanso(RestoAgenda,RestoWorkblocks),
        !
    ;
        WorkblockList = []
    )
.

agendaCompleta(AgendaCompletaOrdenada):-
    findall((D,_), driverduty(D,_), ListaDriverDutiesID),
    criarAgendasDriverDuty(ListaDriverDutiesID,AgendaCompleta),
    sort(1,@=<,AgendaCompleta,AgendaCompletaOrdenada)
.

removerWorkblockEntreLimitesAgenda(AgendaDriverDuty,LimiteInferior,LimiteSuperior,MaiorWorkblock,RestoAgenda):-
    workblockEntreLimitesAgenda(AgendaDriverDuty,LimiteInferior,LimiteSuperior,WorkblocksEntreLimites),
    removerMaiorWorkblockAgenda(WorkblocksEntreLimites,MaiorWorkblock,RestoAgenda)
.

workblockEntreLimitesAgenda([],_,_,[]):-!.
workblockEntreLimitesAgenda([DriverDuty|RestoAgendaDriverDuty],LimiteInferior,LimiteSuperior,[(HInicio,HFim,DriverId,WbId)|RestoWorkblocksEntreLimites]):-
    workblockEntreLimitesAgenda(RestoAgendaDriverDuty,LimiteInferior,LimiteSuperior,RestoWorkblocksEntreLimites),

    DriverDuty = (HInicio,HFim,DriverId,WbId),

    HInicio>=LimiteInferior,
    HFim=<LimiteSuperior,!
.
workblockEntreLimitesAgenda([_|RestoAgendaDriverDuty],LimiteInferior,LimiteSuperior,RestoWorkblocksEntreLimites):-
    workblockEntreLimitesAgenda(RestoAgendaDriverDuty,LimiteInferior,LimiteSuperior,RestoWorkblocksEntreLimites),!
.

removerMaiorWorkblockAgenda(Agenda,MaiorWorkblock,RestoAgenda):-
    removerMaiorWorkblockAgenda(Agenda,MaiorWorkblock),
    apagaPrimeiro(Agenda,MaiorWorkblock,RestoAgenda)
.

removerMaiorWorkblockAgenda(Agenda,MaiorWorkblock):-
    criarAgendaComDuracoes(Agenda,AgendaComDuracoes),
    sort(1,@>=,AgendaComDuracoes,AgendaComDuracoesOrdenada),

    AgendaComDuracoesOrdenada = [MaiorWorkblockComDuracao|_],
    MaiorWorkblockComDuracao = (_,HInicio,HFim,DriverId,WbId),
    MaiorWorkblock = (HInicio,HFim,DriverId,WbId)
.

apagaPrimeiro([],_,[]):-!.
apagaPrimeiro([A|T],A,T):-!.
apagaPrimeiro([H|T],A,[H|T1]):-
    apagaPrimeiro(T,A,T1),!
.

criarAgendaComDuracoes([],[]).
criarAgendaComDuracoes([HAgenda|RestoAgenda],[HAgendaDuracoes|RestoAgendaDuracoes]):-
    criarAgendaComDuracoes(RestoAgenda,RestoAgendaDuracoes),

    HAgenda = (HInicio,HFim,DriverId,WbId),

    Dur is HFim - HInicio,

    HAgendaDuracoes = (Dur,HInicio,HFim,DriverId,WbId)
.

melhorTupleParaWorkblock(_,[Tuple],[Tuple]).
melhorTupleParaWorkblock(W,[Tuple|Resto],MelhoresTuples):-
    melhorTupleParaWorkblock(W,Resto,MelhorTupleResto),

    W = (HoraInicioWorkblock,HoraFimWorkblock,_,_),
    Tuple = (HoraInicioTuple,HoraFimTuple,_,_),
    MelhorTupleResto = [(MelhorRestoHoraInicio,MelhorRestoHoraFim,_,_)|_],

    intersecaoEntreDoisIntervalos(HoraInicioWorkblock,HoraFimWorkblock,HoraInicioTuple,HoraFimTuple,IntersecaoAtual),
    intersecaoEntreDoisIntervalos(HoraInicioWorkblock,HoraFimWorkblock,MelhorRestoHoraInicio,MelhorRestoHoraFim,MelhorIntersecao),

    (
        IntersecaoAtual > MelhorIntersecao,
        MelhoresTuples = [Tuple],
        !
    ;
        MelhorIntersecao > IntersecaoAtual,
        MelhoresTuples = MelhorTupleResto,
        !
    ;
        MelhorIntersecao = IntersecaoAtual,
        MelhoresTuples = [Tuple|MelhorTupleResto]
    )
.

intersecaoEntreDoisIntervalos(A1,A2,B1,B2,Intersecao):-
    LimiteInferior is max(A1,B1),
    LimiteSuperior is min(A2,B2),
    Size is LimiteSuperior - LimiteInferior,
    (Size =< 0, Intersecao is 0, !; Size > 0, Intersecao is Size)
.
:-consult(bc).

% Bibliotecas HTTP
:-use_module(library(http/thread_httpd)).
:-use_module(library(http/http_dispatch)).
:-use_module(library(http/http_parameters)).
:-use_module(library(http/http_client)).

% Bibliotecas JSONx
:-use_module(library(http/json_convert)).
:-use_module(library(http/http_json)).
:-use_module(library(http/json)).

:-use_module(library(http/http_cors)).
:-set_setting(http:cors, [*]).
:-use_module(library(lists)).


% Relação entre pedidos HTTP e predicados que os processa
:-http_handler('/bestPath', p_json, []).
:-http_handler('/generateDriverDuties', generateDriverDuties , []).


% Criação de servidor HTTP em 'Port' que trata pedidos em JSON
server(Port) :- http_server(http_dispatch, [port(Port)]),
        importa.

importa:-
        retractor,
        getNodes,
        getLines,
        gera_ligacoes,
        getTrips,
        getVehicleDutys,
        criarHorariosAPartirDeViagens
.

retractor:-
    retractall(no(_,_,_,_,_,_)),
    retractall(liga(_,_,_)),
    retractall(path(_,_,_,_,_)),
    retractall(workblock(_,_,_,_)),
    retractall(horario(_,_,_)),
    retractall(viagem(_,_,_)),
    retractall(noID(_,_)),
    retractall(driverduty(_,_)),
    retractall(vehicleduty(_,_)),
    retractall(rangevd(_,_,_)),
    retractall(tuple(_,_,_,_)),
    retractall(tupleGasto(_,_,_,_)),
    retractall(lista_motoristas_nworkblocks(_,_)),
    !
.

:-dynamic no/6.
:-dynamic noID/2.
:-dynamic path/5.
:-dynamic linha/4.
:-dynamic liga/3.
:-dynamic  melhor_caminho/3.
%Hora do primeiro nó do percurso 
:-dynamic  hora_primeiro_no/1.
%Hora do último nó do percurso 
:-dynamic  hora_ultimo_no/1.
:-dynamic  primeiro_horario/1.
:-dynamic  nr_sol/1.
:-dynamic workblockI/4.



:-json_object no(name:string, shortName:string, isDepot:boolean, isReliefPoint:boolean, longitude:string, latitude:string).

:-json_object noID(code:string,shortName:string).

:-json_object linha(code:string, description:string, linePaths:list(string)).

:-json_object path(name:string, code:string, listNodes:list(string), totalTime:integer, totalDist:integer).

:-json_object workblockI(
        id:string, 
        trips:list(string), 
        startingTime:integer,
        endingTime:integer
).


:-json_object trip(
        tripId:string,
        listNodePassages:list(integer)
).

:-json_object vDuty(
        code:string, 
        workblocks:list(string)
).

:-json_object path(
        noInicial:string, 
        noFinal:string, 
        idCaminho:string
).

:-json_object paths(
        lista:list(path/3),
        tempo:integer
).

:-json_object vehicleDuty(
        vehicleDutyCode:string,
        workBlocks:list(string)
).

:-json_object pathsObject(
        lista:list(string),
        tempo:integer
).


:-json_object driverDutysObject(
        lista:list(driverDutyObject/2)
).

:-json_object driverDutyObject(
        driverdutyId: string,
        workBlocks: list(string)
).

:-json_object workblockJSON(
        workblock: string
).
      

generateDriverDuties(_):-
        cors_enable,
        teste(_),
        findall((DriverDutyId, Workblocks), driverduty(DriverDutyId, Workblocks), Lista),
        trataLista(Lista, ListaJSON),
        prolog_to_json(driverDutysObject(ListaJSON), JSONObject),
        reply_json(JSONObject, [json_object(dict)]).

trataLista([], []).
trataLista([(Id, Wb)|B], [JSONObject| ListaJSON]):-
        atom_string(Id, IdS),
        trataListaWB(Wb, ListaWB),
        prolog_to_json(driverDutyObject(IdS, ListaWB), JSONObject),
        trataLista(B, ListaJSON).

trataListaWB([], []).
trataListaWB([A|B], [Workblock|Wb]):-
        atom_string(A,AS),
        prolog_to_json(AS, Workblock),
        trataListaWB(B,Wb).


p_json(Request):-

        cors_enable,
        http_parameters(Request, [initialNode(NoInicial, []), finalNode(NoFinal, []), schedule(Horario, [])]),
        atom_number(Horario, HoraInicio),
        atom_string(NoInicial, NoInicialS),
        atom_string(NoFinal, NoFinalS),

        getNodes(),
        getLines(),
        
        %plan_viagem_horario(NoInicialS, NoFinalS, HoraInicio, Lista, TTotal,NSol,TSol),
        aStar(NoInicialS, NoFinalS, HoraInicio, Lista,TTotal),

        %transformElementsToJSON(Lista, ListaJSON),

        prolog_to_json(pathsObject(Lista, TTotal), JSONObject),
        reply_json(JSONObject, [json_object(dict)]).


transformElementsToJSON([], []).
transformElementsToJSON([(NoI, NoF, PathID)|T], [path(NoI, PathID, NoF) | ListaJSON]):- transformElementsToJSON(T, ListaJSON).

%PATH:8 = PARED, MOURZ, BALTR, VANDO
%PATH:11 = PARED, MOURZ, BALTR, VANDO, LORDL
%horario("Path:11",[72100,72280,72380,74880,75420,75550]).
%horario("Path:8",[72000,72480,72780,73080,73620]).

%horario("Path:1",1,[70200,70740,71040,71340,71820]).
%horario("Path:1",2,[71100,71640,71940,72240,72720]).
%horario("Path:1",3,[72000,72540,72840,73140,73620]).
%horario("Path:1",4,[73800,74340,74640,74940,75420]).
%horario("Path:1",5,[77400,77940,78240,78540,79020]).
%
%horario("Path:3",6,[73800,74280,74580,74880,75420]).
%horario("Path:3",7,[72900,73380,73680,73980,74520]).
%horario("Path:3",8,[72000,72480,72780,73080,73620]).
%horario("Path:3",9,[71100,71580,71880,72180,72720]).
%horario("Path:3",10,[70200,70680,70980,71280,71820]).
%
%horario("Path:5",11,[66360,66960,67200,67440,67920]).
%horario("Path:5",12,[69960,70560,70800,71040,71520]).
%horario("Path:5",13,[73560,74160,74400,74640,75120]).
%horario("Path:5",14,[77160,77760,78000,78240,78720]).
%horario("Path:5",15,[80760,81360,81600,81840,82320]).
%
%horario("Path:8",16,[64800,65280,65520,65760,66360]).
%horario("Path:8",17,[68400,68880,69120,69360,69960]).
%horario("Path:8",18,[72000,72480,72780,73080,73620]).
%horario("Path:8",19,[75600,76080,76320,76560,77160]).
%horario("Path:8",20,[79200,79680,79920,80160,80760]).
%
%horario("Path:9",21,[70560,71340,71580,71820,72300]).
%horario("Path:9",22,[71460,72240,72480,72720,73200]).
%horario("Path:9",23,[72360,73140,73380,73620,74100]).
%horario("Path:9",24,[73260,74040,74280,74520,75000]).
%horario("Path:9",25,[74160,74940,75180,75420,75900]).
%
%horario("Path:11",26,[70620,71100,71340,71580,72360]).
%horario("Path:11",27,[71520,72000,72240,72480,73260]).
%horario("Path:11",28,[72420,72900,73140,73380,74160]).
%horario("Path:11",29,[73320,73800,74040,74280,75060]).
%horario("Path:11",30,[74220,74700,74940,75180,75960]).
%
%horario("Path:20",41,[70980,71280,71520,71760,72120,72360]).
%horario("Path:20",42,[72180,72480,72720,72960,73320,73560]).
%horario("Path:20",43,[73380,73680,73920,74160,74520,74760]).
%horario("Path:20",44,[74580,74880,75120,75360,75720,75960]).
%horario("Path:20",45,[75780,76080,76320,76560,76920,77160]).
%
%horario("Path:22",46,[69600,69840,70200,70440,70680,70980]).
%horario("Path:22",47,[70800,71040,71400,71640,71880,72180]).
%horario("Path:22",48,[72000,72240,72600,72840,73080,73380]).
%horario("Path:22",49,[73200,73440,73800,74040,74280,74580]).
%horario("Path:22",50,[74400,74640,75000,75240,75480,75780]).
%
%horario("Path:24",51,[66300,66600,66840,67080,67320,67620]).
%horario("Path:24",52,[67620,67920,68160,68400,68640,68940]).
%horario("Path:24",53,[68940,69240,69480,69720,69960,70260]).
%horario("Path:24",54,[70260,70560,70800,71040,71280,71580]).
%horario("Path:24",55,[71580,71880,72120,72360,72600,72900]).
%
%horario("Path:26",56,[69240,69540,69780,70020,70260,70560]).
%horario("Path:26",57,[70560,70860,71100,71340,71580,71880]).
%horario("Path:26",58,[71880,72180,72420,72660,72900,73200]).
%horario("Path:26",59,[73200,73500,73740,73980,74220,74520]).
%horario("Path:26",60,[74520,74820,75060,75300,75540,75840]).

getNodes():- retractall(no(_,_,_,_,_,_)),
        retractall(noID(_,_)),
        %getenv('APIURL', APIURL),
        APIURL = 'http://localhost:8080/api',
        string_concat(APIURL, '/node', URL),
        http_get(URL, ListNodes, [json_object(dict)]), importNodes((ListNodes)).

importNodes([]).
importNodes([H|T]):- assertz(no(H.name, H.shortName, H.isReliefPoint, H.isDepot, H.longitude, H.latitude)), assertz(noID(H.code, H.shortName)), importNodes(T).

getLines():- retractall(path(_,_,_,_,_)), 
        %getenv('APIURL', APIURL),
        APIURL = 'http://localhost:8080/api',
        string_concat(APIURL, '/line', URL),
        http_get(URL, ListLines, [json_object(dict)]), importLines(ListLines).

importLines([]):-!.
importLines([H|T]):- 
                assertz(linha(H.code, H.description, H.linePaths)),
                %getenv('APIURL', APIURL),      
                APIURL = 'http://localhost:8080/api',
                string_concat(APIURL, '/line/', URL),
                string_concat(URL, H.code, Link),
                string_concat(Link, '/paths', URL2),
                http_get(URL2, ListPaths, [json_object(dict)]), trataPaths(ListPaths),
                importLines(T).

trataPaths([]):-!.
trataPaths([H|T]):- definePath(H.path.segments, ListNames, Time, Distance), 
                    ListNames = [FirstName|_],
                    last(ListNames, LastName),
                    string_concat(FirstName,'_', Name),
                    string_concat(Name, LastName, FinalName),
                    string_lower(FinalName, Final),
                    TimeMinutos is Time / 60,
                    (retract(path(Final,H.path.code, ListNames, TimeMinutos, Distance));true),
                    assertz(path(Final,H.path.code, ListNames, TimeMinutos, Distance)),
                    trataPaths(T).

definePath([H],[H.firstNodeID.shortName, H.secondNodeID.shortName], H.travelTimeBetweenNodes, H.distanceBetweenNodes):-!.

definePath([H|T], [H.firstNodeID.shortName | X], Time, Distance):- 
        definePath(T, X, TimeAux, DistanceAux),
        Time is TimeAux + H.travelTimeBetweenNodes,
        Distance is DistanceAux + H.distanceBetweenNodes.



getTrips():-retractall(trip(_,_,_)),
        string_concat('https://localhost:5001/api', '/trip', URL),
        http_get(URL, ListTrips,[json_object(dict), cert_verify_hook(cert_accept_any)]), importTrips(ListTrips), !.

importTrips([]).
importTrips([H|T]):- trataListaNodePassages(H.nodePassages, ListaTimes), 
        assertz(viagem(H.pathID.id,H.tripId, ListaTimes)),
        importTrips(T).

trataListaNodePassages([], []).
trataListaNodePassages([A|B], [(A.nodeId, S)|ListaTimes]):-
        split_string(A.passageTime, ":", ":", Times),

        nth0(0, Times, HoursS),
        nth0(1, Times, MinutesS),
        nth0(2, Times, SecondsS),

        atom_number(HoursS, Hours),
        atom_number(MinutesS, Minutes),
        atom_number(SecondsS, Seconds),
        H is Hours*3600,
        M is H + Minutes*60,
        S is M + Seconds,
        trataListaNodePassages(B, ListaTimes).    

criarHorariosAPartirDeViagens:-
        findall(_,(
        viagem(PathID,TripID,PassingTimes),
        extrairHorariosNodePassageList(PassingTimes,ListaPassagemSegundos),
        asserta(horario(PathID,TripID,ListaPassagemSegundos))
        ),_)
.

extrairHorariosNodePassageList([],[]).
extrairHorariosNodePassageList([(_,HoraPassagem)|RestoPassingTimes],[HoraPassagem|RestoResultado]):-
        extrairHorariosNodePassageList(RestoPassingTimes,RestoResultado)
.

getVehicleDutys():- retractall(vDuty(_,_)),retractall(workblockI(_,_,_,_)),
                string_concat('https://localhost:5001/api', '/vehicleDuty', URL),
                http_get(URL, ListVehicleDutys,[json_object(dict), cert_verify_hook(cert_accept_any)]), importVehicleDutys(ListVehicleDutys), !.


importVehicleDutys([]).
importVehicleDutys([H|T]):- trataListaWorkBlocks(H.workBlocks, ListaWB),
        assertz(vehicleduty(H.vehicleDutyId, ListaWB)),
        importVehicleDutys(T).

trataListaWorkBlocks([], []).
trataListaWorkBlocks([H|T], [H.workBlockId|ListaWB]):-

        split_string(H.startingTime, ":", ":", Times),
        
        nth0(0, Times, HoursS),
        nth0(1, Times, MinutesS),
        nth0(2, Times, SecondsS),

        atom_number(HoursS, Hours),
        atom_number(MinutesS, Minutes),
        atom_number(SecondsS, Seconds),

        
        HS is Hours*3600,
        MS is HS + Minutes*60,
        SS is MS + Seconds,

        split_string(H.endingTime, ":", ":", TimesE),
        
        nth0(0, TimesE, HoursSE),
        nth0(1, TimesE, MinutesSE),
        nth0(2, TimesE, SecondsSE),

        atom_number(HoursSE, HoursE),
        atom_number(MinutesSE, MinutesE),
        atom_number(SecondsSE, SecondsE),

        
        HE is HoursE*3600,
        ME is HE + MinutesE*60,
        SE is ME + SecondsE,

        trataListaTrips(H.trips, ListaTripsId),

        assertz(workblock(H.workBlockId, ListaTripsId, SS, SE)),

        trataListaWorkBlocks(T, ListaWB).

        

trataListaTrips([], []).
trataListaTrips([H|T], [H.tripId| ListaTripsId]):-
        trataListaTrips(T, ListaTripsId).

% Devolve o primeiro caminho encontrado de Orig ate Dest
caminho(Orig,Dest,Cam):-caminho(Orig,Dest,[],Cam).
caminho(Dest,Dest,LA,Cam):-reverse(LA,Cam).
caminho(No,NoDest,LA,Cam):-liga(No,Nx,N),
                        \+ member((_,_,N),LA),
                        \+ member((Nx,_,_),LA),
                        \+ member((_,Nx,_),LA),
                        caminho(Nx,NoDest,[(No,Nx,N)|LA],Cam).


gera_ligacoes:- retractall(liga(_,_,_)),
                findall(_,ligacao,_).

ligacao:- 
        path(_,N,LNos,_,_),
        (isRendicaoOuRecolha(No1); isNoTerminal(No1,LNos)),
        (isRendicaoOuRecolha(No2); isNoTerminal(No2,LNos)),
        No1\==No2,
        em_ordem(No1,No2,LNos),
        retractall(liga(No1,No2,N)),
        assert(liga(No1,No2,N))
.

isLigaValido(No1,No2,N):-
        findall((No1,No2,N), (liga(No1,No2,N)), ListaLigas),
        length(ListaLigas, SizeListaLigas),
        SizeListaLigas = 0
.

isRendicaoOuRecolha(No):-
        no(_,No,true,_,_,_);no(_,No,_,true,_,_)
.

isNoTerminal(No,[No|_]).
isNoTerminal(No,LNos):-last(LNos,No).

em_ordem(No1,No2,[No1|L]):- member(No2,L).
em_ordem(No1,No2,[_|L]):- em_ordem(No1,No2,L).


plan_viagem_horario(Noi,Nof,HoraAtual,LCaminho_menorDistancia, TTotal,NSol,TSol):-
                                get_time(Ti),
                                asserta(nr_sol(0)),
                                (find_melhor_caminho(Noi,Nof,HoraAtual);true),
                                retract(melhor_caminho(LCaminho_menorDistancia,_,HoraChegada)),
                                HoraChegada\==86400,
                                retract(nr_sol(NSol)),
                                get_time(Tf),
                                TSol is Tf-Ti,
                                %writeln('Caminho:'),writeln(LCaminho_menorDistancia),
                                TTotal is HoraChegada - HoraAtual.
                                %write('Tempo Total: '),write(TTotal),nl,
                                %write("Numero de solucoes: "), writeln(NSol),
                                %write('Tempo de geracao da solucao: '),write(TSol),nl.

find_melhor_caminho(Noi,Nof,HoraInicial):- asserta(melhor_caminho(_,_,86400)),
                                caminho(Noi,Nof,LCaminho),
                                asserta(hora_primeiro_no(86400)),
                                asserta(hora_ultimo_no(0)),
                                duracao_caminho(LCaminho,HoraInicial, DuracaoCaminho),
                                atualiza_melhor(LCaminho, DuracaoCaminho),
                                fail.

atualiza_melhor(LCaminho, Duracao):- 
                                retract(nr_sol(X)),
                                X1 is X+1,
                                asserta(nr_sol(X1)),
                                melhor_caminho(_,_,MelhorHoraChegada),
                                retract(hora_ultimo_no(HoraChegada)),
                                retract(hora_primeiro_no(HoraPartida)),
                                HoraChegada<MelhorHoraChegada,
                                retract(melhor_caminho(_,_,_)),
                                asserta(melhor_caminho(LCaminho,Duracao,HoraChegada)),
                                asserta(primeiro_horario(HoraPartida)).

%ver a duracao total subtraindo o ultimo com o primeiro
duracao_caminho([],_,ValorFinal):-
                                hora_primeiro_no(HoraI),
                                hora_ultimo_no(HoraFinal),
                                ValorFinal is HoraFinal-HoraI.
duracao_caminho([S|Segmentos],HoraI,DuracaoTotal):-
                                descobrir_horario_certo(S,HoraI,HorarioNo2),
                                duracao_caminho(Segmentos,HorarioNo2,DuracaoTotal).

%Descobrir o melhor segmento a escolher (menor horário para o No 2)
descobrir_horario_certo((No1,No2,IdPath),HoraMinima,HorarioNo2):-
                                path(_,IdPath,ListaAux,_,_),
                                nth1(Pos1,ListaAux,No1),
                                nth1(Pos2,ListaAux,No2),
                                proximo_horario(Pos1,Pos2,IdPath,HoraMinima,Horario),
                                nth1(Pos1,Horario,HorarioNo1),
                                nth1(Pos2,Horario,HorarioNo2),
                                (ultimo_horario(HorarioNo2);true),
                                (primeiro_horario(HorarioNo1);true).

%Identifica o horário com a menor duração da viagem
proximo_horario(Pos1,Pos2,PathId,HoraMinima,MenorHorario):-
                                findall(Horario,horario(PathId,_,Horario),ListaHorario),
                                verificar_horarios(Pos1,HoraMinima,ListaHorario,HorariosValidos),
                                menor_horario(HorariosValidos,Pos2,MenorHorario).

%Recebe todos os horários da BC e preenche o 4º argumento com os horários válidos
% Válido = Hora inicial maior que a HoraMinima
verificar_horarios(_,_,[],[]).
verificar_horarios(Pos1,HoraMinima,[Horario|ListaHorario],[Horario|ListaHorariosValidos]):-
                                nth1(Pos1,Horario,Hora),
                                Hora>=HoraMinima,
                                verificar_horarios(Pos1,HoraMinima,ListaHorario,ListaHorariosValidos).
verificar_horarios(Pos1,HoraMinima,[_|ListaHorario],ListaHorariosValidos):-
                                verificar_horarios(Pos1,HoraMinima,ListaHorario,ListaHorariosValidos).

%Identifica o horário com a menor duração de uma lista de horários
menor_horario([HV],_,HV).
menor_horario([HV|HorariosValidos],Pos2,HV):-
                                menor_horario(HorariosValidos,Pos2,MenorHorarioSF),
                                nth1(Pos2,MenorHorarioSF,HoraSF),
                                nth1(Pos2,HV,Hora),
                                Hora<HoraSF.
menor_horario([_|HorariosValidos],Pos2,MenorHorarioSF):-
                                menor_horario(HorariosValidos,Pos2,MenorHorarioSF).

primeiro_horario(Hora):-
                hora_primeiro_no(N),
                Hora<N,findall(_,retract(hora_primeiro_no(_)),_),
                asserta(hora_primeiro_no(Hora)).

ultimo_horario(Hora):-
                hora_ultimo_no(N),
                Hora>N,findall(_,retract( hora_ultimo_no(_)),_),
                asserta(hora_ultimo_no(Hora)).  

%Em M
distanciaEntre2Nos(No1,No2,D):- no(_,No1,_,_,Lat1,Lon1),
                                no(_,No2,_,_,Lat2,Lon2),
                                distanceGeodesic(Lat1,Lon1,Lat2,Lon2,D1),
                                D is D1 * 1000.

%Em Km
distanceGeodesic(Lat1, Lon1, Lat2, Lon2, Dis):- P is 0.017453292519943295,
                                                atom_number(Lat1, La1),
                                                atom_number(Lon1, Lo1),
                                                atom_number(Lat2, La2),
                                                atom_number(Lon2, Lo2),
                                                A is (0.5 - cos((La2 - La1) * P) / 2 + cos(La1 * P) * cos(La2 * P) * (1 - cos((Lo2 - Lo1) * P)) / 2),
                                                Dis is (12742 * asin(sqrt(A))).

%Em segundos
tempoEntre2Nos(No1,No2,IDLinha,T):-     distanciaEntre2Nos(No1,No2,D),
                                        velocLinhaMPorS(IDLinha,V),
                                        T is D/V.

%Em m/s
velocLinhaMPorS(IDLinha,VelocLinha):-   path(_,IDLinha,_,Tempo,Distancia),
                                        TempoSegundos is Tempo * 60,
                                        VelocLinha is Distancia/TempoSegundos.

somaTemposDeCaminho([(_,_,_,Tempo)],Tempo).
somaTemposDeCaminho([(_,_,_,Tempo)|T],TempoTotal):- somaTemposDeCaminho(T,TempoParcial),
                                                    TempoTotal is TempoParcial + Tempo.

% Verifica se o primeiro nó do caminho tem algum horário após o pedido 
% e devolve a diferença entre o horário atual e o encontrado
% TEMPO ATÉ AUTOCARRO DE NO1 PARA NO2
checkCaminhoHorario(HoraMinima,Act, (No1,_,Linha), HorarioPartida):-
                                                    path(_,Linha,ListaLinha,_,_),
                                                    horario(Linha,_,ListaHorario),
                                                    nth1(Pos,ListaLinha,No1),
                                                    nth1(Pos,ListaHorario,HorarioPartida),
                                                    nth1(PosAct,ListaLinha,Act),
                                                    nth1(PosAct,ListaHorario,HorarioPartidaAct),
                                                    %write(HorarioPartidaAct),
                                                    HorarioPartidaAct>=HoraMinima.
                                                    %writeln("a").

findMenorFromHorario(HoraMinima,Act,(No1,_,Linha), HorarioMenor):-
                                                        findall(X, checkCaminhoHorario(HoraMinima,Act,(No1,_,Linha), X), Lista),
                                                        min_list(Lista, HorarioMenor).


aStar(Orig,Dest,HAtual,Cam,Custo):-aStar2(Dest,[(_,0,[Orig],HAtual)],Cam,Custo),!. %get_time(X), get_time(X2), R is X2-X, write(R).
aStar2(Dest,[(_,Custo,[Dest|T],_)|_],Cam,Custo):- reverse([Dest|T],Cam).
aStar2(Dest,[(_,Ca,LA,HAtual)|Outros],Cam,Custo):- LA=[Act|_],
                                             findall((CEX,CaX,[X|LA],HChegadaX),
                                                 (Dest\==Act,liga(Act,X,Linha),\+ member(X,LA),
                                                 findMenorFromHorario(HAtual,Act, (X,_,Linha),HChegadaX),
                                                 CustoX is HChegadaX - HAtual,
                                                 %write("X: "),writeln(X),
                                                 %write("HChegadaX: "),writeln(HChegadaX),
                                                 %write("Linha: "),writeln(Linha),
                                                 CaX is CustoX + Ca,
                                                 %estimativa
                                                 tempoEntre2Nos(X,Dest,Linha,EstX),
                                                 CEX is CaX +EstX
                                                 ),Novos),
                                             append(Outros,Novos,Todos),
                                             %write('Novos='),write_array(Novos),nl,
                                             sort(Todos,TodosOrd),
                                             %write('TodosOrd='),write_array(TodosOrd),nl,
                                             aStar2(Dest,TodosOrd,Cam,Custo),!.


bestfs(Orig,Dest,HAtual,Cam,Custo):-bestfs2(Dest,(0,[Orig],HAtual),Cam,Custo). %get_time(X), get_time(X2), R is X2-X, write(R).
bestfs2(Dest,(Custo,[Dest|T],_),Cam,Custo):- !, reverse([Dest|T],Cam).
bestfs2(Dest,(Ca,LA,HAtual),Cam,Custo):- LA=[Act|_],
                                             findall((EstX,CaX,[X|LA],HChegadaX),
                                                 (liga(Act,X,Linha),\+ member(X,LA),
                                                 findMenorFromHorario(HAtual, (X,_,Linha),HChegadaX),
                                                 CustoX is HChegadaX - HAtual,
                                                 Custo is HChegadaX,
                                                 CaX is CustoX + Ca,
                                                 %estimativa
                                                 tempoEntre2Nos(X,Dest,Linha,EstX)),Novos),
                                                 sort(Novos,NovosOrd),
                                                 NovosOrd = [(_,CM,Melhor)|_],
                                                 bestfs2(Dest,(CM,Melhor),Cam,Custo).
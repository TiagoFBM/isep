:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.

:-dynamic melhor_geracao/1.
:-dynamic melhor_global/1.

% Em Segundos
:-dynamic tempo_max_execucao/1.
:-dynamic tempo_inicial_execucao/1.

:-dynamic melhor_peso/1.

:-dynamic estabilizador/1.
:-dynamic estabilizador_max/1.

% ========= SPRINT D =========

:-dynamic cargaTotal/1.
:-dynamic capacidadeTotal/1.

:-dynamic workblock/4.
:-dynamic tuple/4.
:-dynamic tupleGasto/4.
:-dynamic lista_motoristas_nworkblocks/2.

% horario(Path,Trip,List_of_Time)
%horario(38,459,[34080,34200]).
%horario(3,31,[37800,38280,38580,38880,39420]).
%horario(1,63,[39600,40140,40440,40740,41220]).
%horario(3,33,[41400,41880,42180,42480,43020]).
%horario(1,65,[43200,43740,44040,44340,44820]).
%horario(3,35,[45000,45480,45780,46080,46620]).
%horario(1,67,[46800,47340,47640,47940,48420]).
%horario(3,37,[48600,49080,49380,49680,50220]).
%horario(1,69,[50400,50940,51240,51540,52020]).
%horario(3,39,[52200,52680,52980,53280,53820]).
%horario(1,71,[54000,54540,54840,55140,55620]).
%horario(3,41,[55800,56280,56580,56880,57420]).
%horario(1,73,[57600,58140,58440,58740,59220]).
%horario(3,43,[59400,59880,60180,60480,61020]).
%horario(1,75,[61200,61740,62040,62340,62820]).
%horario(3,45,[63000,63480,63780,64080,64620]).
%horario(1,77,[64800,65340,65640,65940,66420]).
%horario(3,48,[66600,67080,67380,67680,68220]).
%horario(1,82,[68400,68940,69240,69540,70020]).
%horario(3,52,[70200,70680,70980,71280,71820]).
%horario(1,86,[72000,72540,72840,73140,73620]).
%horario(3,56,[73800,74280,74580,74880,75420]).
%horario(1,432,[75600,76140,76440,76740,77220]).
%horario(39,460,[77220,77340]).

% workblock(WorkBlock, List_of_Trips,StartTime, EndTime)
/*workblock("12",["459"],10000,11000).
workblock("211",["31","63"],11000,12000).
workblock("212",["65","33"],12000,13000).
workblock("213",["35","67"],13000,15000).
workblock("214",["37","69"],15000,18000).
workblock("215",["39","71"],18000,20000).
workblock("216",["41","73"],20000,24000).
workblock("217",["43","75"],24000,27000).
workblock("218",["45","77"],27000,30000).
workblock("219",["48","82"],30000,34000).
workblock("220",["52","86"],34000,39000).
workblock("221",["56","432"],39000,42000).
workblock("222",["460"],42000,43000).*/

%workblock(1,[460],26580,29100).
%workblock(16,[460],29100,31200).
%workblock(17,[460],31200,33060).
%workblock(18,[460],33060,35160).
%workblock(19,[460],35160,37020).
%workblock(20,[460],37020,39120).
%workblock(21,[460],39120,40980).
%workblock(22,[460],40980,43080).
%workblock(23,[460],43080,44940).
%workblock(24,[460],44940,47040).
%workblock(25,[460],47040,48900).
%workblock(26,[460],48900,51000).
%workblock(27,[460],51000,52860).
%workblock(28,[460],52860,54960).
%workblock(29,[460],54960,56820).
%workblock(30,[460],56820,58920).
%workblock(31,[460],58920,60780).
%workblock(32,[460],60780,62880).
%workblock(33,[460],62880,64740).
%workblock(34,[460],64740,66840).
%workblock(35,[460],66840,68700).
%workblock(36,[460],68700,70800).
%workblock(37,[460],70800,72660).
%workblock(38,[460],72660,73320).

% (VehicleDutyID, InicioPrimeiroWorkblock, FimUltimoWorkblock)
:-dynamic rangevd/3.

% (DriverID, Horario em que pode começar a trabalhar, Horario em que pode terminar de trabalhar, duração total de condução, lista com a duração dos blocos do motorista)
% Somatório da ListaDuracaoBlocosMotorista = DuraçãoCondução

% (DriverID, HorarioPodeComeçar, HorarioPodeTerminar, DuraçãoCondução, ListaDuracaoBlocosMotorista)
% Tuples = (HoraInicioBloco, HoraFimBloco, NumeroSegundosTrabalhoEmBloco, DriverID)
% 25200, 61200, 21600, 276
% 25200, 61200, 7200, 276
horariomotorista(276,25200,61200,28800, [21600,7200]).
horariomotorista(527, 25200, 61200, 28800, [21600,7200]).
horariomotorista(889, 25200, 61200, 28800, [21600,7200]).
horariomotorista(1055, 25200, 61200, 28800, [14400,14400]).
horariomotorista(1461, 25200, 61200, 28800, [14400,14400]).
horariomotorista(1640, 25200, 61200, 28800, [21600,7200]).
horariomotorista(2049, 25200, 61200, 28800, [21600,7200]).
horariomotorista(5188,33300,69300, 28800, [7200,21600]).
horariomotorista(6616,33300, 69300, 28800, [14400,14400]).
horariomotorista(6697,33300, 69300, 28800, [21600,7200]).
horariomotorista(11018,41400,77400, 28800, [21600,7200]).
horariomotorista(11692,41400, 77400, 28800, [21600,7200]).
horariomotorista(14893,45000,81000, 28800, [10800,18000]).
horariomotorista(16458,50400,86400, 28800, [14400,14400]).
horariomotorista(16690, 50400, 86400, 28800, [7200,21600]).
horariomotorista(16763, 50400, 86400, 28800, [14400,14400]).
horariomotorista(17015, 50400, 86400, 28800, [10800,18000]).
horariomotorista(17552,54000,90000,28800, [10800,18000]).
horariomotorista(17623,25200, 61200, 28800, [21600,7200]).
horariomotorista(17630,25200, 61200, 28800, [21600,7200]).
horariomotorista(17639,27000,48600,21600,[21600]).
horariomotorista(17673,25200, 61200, 28800, [21600,7200]).
horariomotorista(18009,50400, 86400, 28800, [7200,21600]).
horariomotorista(18050,54000,90000,28800, [21600,7200]).
horariomotorista(18097,57600,79200,21600,[21600]).
horariomotorista(18105,57600,79200,21600,[21600]).
horariomotorista(18107,57600,79200,21600,[21600]).
horariomotorista(18119,59400,81000,21600,[21600]).
horariomotorista(18131,66600,88200,21600,[21600]).

% vehicleduty(VehicleDuty, List_of_WorkBlocks)
% vehicleduty("12",["12","211","212","213","214","215","216","217","218","219","220","221","222"]). % 13
%vehicleduty(1,[1,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38]). % 24

% driverduty(DriverDuty, List_of_WorkBlocks)
:-dynamic vehicleduty/2.
:-dynamic driverduty/2.
:-dynamic viagem/3.

% lista_motoristas_nworkblocks(VehicleDutyID, List_of_(DriverId,NumberWorkblocks) )
% lista_motoristas_nworkblocks(12,[(276,2),(5188,3),(16690,2),(18107,6)]).
/*viagem("Path:1","459",[("Node:14", 10000),  ("Node:4", 10500),  ("Node:13", 10800),  ("Node:15", 10900), ("Node:1", 11000)]).

viagem("Path:3","31",[("Node:1", 11000),  ("Node:2", 11200),  ("Node:3", 11300),  ("Node:4", 11400), ("Node:56", 11500)]).
viagem("Path:1","63",[("Node:14", 11500),  ("Node:4", 11600),  ("Node:13", 11700),  ("Node:15", 11800),  ("Node:1", 12000)]).

viagem("Path:1","65",[("Node:14", 12000),  ("Node:4", 12200),  ("Node:13", 12400),  ("Node:15", 12500),  ("Node:1", 12600)]).
viagem("Path:3","33",[("Node:1", 12700),  ("Node:15", 12800),  ("Node:13", 12900),  ("Node:4", 12930),  ("Node:14", 13000)]).

viagem("Path:1","35",[("Node:14", 13000),  ("Node:4", 13400),  ("Node:13", 13600),  ("Node:15", 13800),  ("Node:1", 14000)]).
viagem("Path:3","67",[("Node:1", 14200),  ("Node:15", 14400),  ("Node:13", 14600),  ("Node:4", 14800),  ("Node:14", 15000)]).

viagem("Path:3","37",[("Node:1", 15000),  ("Node:15", 15400),  ("Node:13", 15800),  ("Node:4", 16000),  ("Node:14", 16400)]).
viagem("Path:1","69",[("Node:14", 16800),  ("Node:4", 17000),  ("Node:13", 17400),  ("Node:15", 17800),  ("Node:1", 18000)]).

viagem("Path:1","39",[("Node:14", 18000),  ("Node:4", 18400),  ("Node:13", 18600),  ("Node:15", 19200),  ("Node:1", 19400)]).
viagem("Path:3","71",[("Node:1", 19500),  ("Node:15", 19600),  ("Node:13", 19700),  ("Node:4", 19800),  ("Node:14", 20000)]).

viagem("Path:1","41",[("Node:14", 20000),  ("Node:4", 20700),  ("Node:13", 21000),  ("Node:15", 21400),  ("Node:1", 21800)]).
viagem("Path:3","73",[("Node:1", 22400),  ("Node:15", 22800),  ("Node:13", 23400),  ("Node:4", 24380),  ("Node:14", 24000)]).

viagem("Path:1","43",[("Node:14", 24000),  ("Node:4", 24200),  ("Node:13", 24500),  ("Node:15", 24800),  ("Node:1", 25000)]).
viagem("Path:3","75",[("Node:1", 25000),  ("Node:15", 25500),  ("Node:13", 26000),  ("Node:4", 26400),  ("Node:14", 27000)]).

viagem("Path:1","45",[("Node:14", 27000),  ("Node:4", 27350),  ("Node:13", 27600),  ("Node:15", 28000),  ("Node:1", 28500)]).
viagem("Path:3","77",[("Node:1", 28500),  ("Node:15", 29000),  ("Node:13", 29300),  ("Node:4", 29800),  ("Node:14", 30000)]).

viagem("Path:1","48",[("Node:14", 30000),  ("Node:4", 30500),  ("Node:13", 31200),  ("Node:15", 31900),  ("Node:1", 32000)]).
viagem("Path:3","82",[("Node:1", 32000),  ("Node:15", 32600),  ("Node:13", 33000),  ("Node:4", 33300),  ("Node:14", 34000)]).

viagem("Path:1","52",[("Node:14", 34000),  ("Node:4", 35000),  ("Node:13", 35600),  ("Node:15", 36000),  ("Node:1", 37000)]).
viagem("Path:3","86",[("Node:1", 37000),  ("Node:15", 37800),  ("Node:13", 38200),  ("Node:4", 38900),  ("Node:14", 39000)]).

viagem("Path:1","56",[("Node:14", 39000),  ("Node:4", 39400),  ("Node:13", 39800),  ("Node:15", 40200),  ("Node:1", 40700)]).
viagem("Path:3","432",[("Node:1", 40700),  ("Node:15", 4100),  ("Node:13", 41300),  ("Node:4", 41800),  ("Node:14", 42000)]).

viagem("Path:1","460",[("Node:14", 42000),  ("Node:4", 42200),  ("Node:13", 42500),  ("Node:15", 42800),  ("Node:1", 43000)]).
*/
penalizacao_paradoxo(100).
penalizacao_hard_MuitoGrave(10).
penalizacao_hard_Grave(8).
penalizacao_soft(1).

estabilizador_max(5).
estabilizador(0).

% 0 a 100
prob_escolher_melhores(20).

%idVehicleDuty(12).

:-dynamic violation_hard/2.

write_array([]).
write_array([H|T]):-writeln(H),write_array(T).

horaToMinutosSegundos(Hora,Minutos,Segundos):-
                        Minutos is Hora*60,
                        Segundos is Minutos*60.

somatorio([], 0).
somatorio([H|T], Resultado):-
    somatorio(T,SomaResto),
    Resultado is SomaResto + H.

margemMinima(20).


%no(1,1,false,false,"-8.4464785432391","41.1293363229325").
%no(2,2,true,false, "-8.34043029659082", "41.217018845589").
%
%no(3,3,true,false,"-8.38448520831829", "41.9082119860192").
%no(4,4,false,false,"-8.4464785432391","41.1293363229325").
%
%noID(1,1).
%noID(2,2).
%noID(3,3).
%noID(4,4).
%
%path(_, 1, [1,2,3,4], 31, 15700).
%path(_, 2, [4,3,2,1], 31, 15700).

% Deslocação / Sobreposição
%driverduty(276,[1,2]).
%driverduty(5188,[3,4]).
%
%% 4 horas
%driverduty(16690, [5,6,7]).
%
%% 8 horas
%driverduty(889,[8,9,10]).
%
%% Almoco / Jantar
%driverduty(2049, [11,12,13,14]).
%
%workblock(1,[1],100,200).
%workblock(2,[1],300,400).
%
%workblock(3,[2],1000,1200).
%workblock(4,[2],1199,1400).
%
%workblock(5,[3],0,3600).
%workblock(6,[4],3600,12000).
%workblock(7,[5],12000,15600).
%
%workblock(8,[6],0,10800).
%workblock(9,[7],10801,21600).
%workblock(10,[8],21601,32400).
%
%workblock(11,[9],39600,46800).
%workblock(12,[10],46800,54000).
%workblock(13,[11],64800,72000).
%workblock(14,[12],72000,79200).
%
%viagem(1,1,[(1,100), (2,200), (3,300), (4,400)]).
%viagem(1,2,[(1,1000), (2,1200), (3,1300), (4,1400)]).
%viagem(1,3,[(1,0), (2,10), (3,20), (4,3600)]).
%viagem(1,4,[(4,3600), (2,1200), (3,1300), (1,12000)]).
%viagem(1,5,[(1,12000), (2,1200), (3,1300), (4,13000)]).
%viagem(1,6,[(1,0), (2,100), (3,200), (4,10800)]).
%viagem(2,7,[(4,10801), (2,10900), (3,11000), (1,21600)]).
%viagem(1,8,[(1,21601), (2,21700), (3,21800), (4,32400)]).
%viagem(1,9,[(1,39600), (2,40000), (3,43000), (4,46800)]).
%viagem(2,10,[(4,46800), (2,48000), (3,50000), (1,54000)]).
%viagem(1,11,[(1,64800), (2,66000), (3,70000), (4,72000)]).
%viagem(2,12,[(4,72000), (2,74000), (3,76000), (1,79200)]).
%
%horario(1,1,[100,200,500,600]).
%horario(2,2,[400,600,700,800]).
%horario(1,3,[3600,3700,3800,3900]).
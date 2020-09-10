package fabrica.spm.messageprocessing.application;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.domain.*;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;
import fabrica.spm.messageprocessing.thread.MessageHandlerService;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ProcessAvailableMessagesController {
    ProductionLineRepository plRepo = PersistenceContext.repositories().productionLine();
    private final ScheduledExecutorService executor;
    private final Map<ProductionLine, ScheduledFuture<?>> map = new HashMap<>();

    public ProcessAvailableMessagesController() {
        List<ProductionLine> plList = plRepo.findAll();
        executor = Executors.newScheduledThreadPool(plList.size());
        for (int i = 0; i < plList.size(); i++) {
            MessageHandlerService thread = new MessageHandlerService(plList.get(i).obtainInternalCode().toString());
            map.put(plList.get(i), executor.scheduleAtFixedRate(thread, 0, 15, TimeUnit.MINUTES));
        }
    }

    public void processMessageBlock(ProductionLine pl, Date startDate, Date endDate) {
        Thread thread = new Thread(new MessageHandlerService(pl.obtainInternalCode().toString(), startDate, endDate));
        thread.start();
    }

    public List<ProductionLine> obtainInnactiveProductionLines() {
        List<ProductionLine> lst = new LinkedList<>();
        for (Map.Entry<ProductionLine, ScheduledFuture<?>> entry : map.entrySet()) {
            if (entry.getValue().isCancelled()) {
                lst.add(entry.getKey());
            }
        }
        return lst;
    }

    public void changeProcessmentStatus(ProductionLine pl) {
        ScheduledFuture<?> sf = map.get(pl);
        if (sf == null) {
            return;
        }

        if (sf.isCancelled()) {
            MessageHandlerService thread = new MessageHandlerService(pl.obtainInternalCode().toString());
            map.put(pl, executor.scheduleAtFixedRate(thread, 0, 15, TimeUnit.MINUTES));
        } else {
            sf.cancel(false);
        }
    }

    public List<ProductionLine> obtainAllProductionLines() {
        return plRepo.findAll();
    }

    public void shutdownThreads() {
        executor.shutdown();
    }
}

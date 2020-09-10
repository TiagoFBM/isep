package fabrica.errornotificationmanagement.application;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.services.QueryArchivedErrorsService;
import fabrica.scm.messagesmanagement.domain.Failure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QueryArchivedErrorsController {
    private final QueryArchivedErrorsService queryArchivedErrorsService = new QueryArchivedErrorsService();

    public List<ErrorNotification> listArchivedErrorsByDate(Calendar beginDate, Calendar endDate){
        return new ArrayList<>(queryArchivedErrorsService.allArchivedErrorsByDate(beginDate,endDate));
    }

    public List<ErrorNotification> listArchivedErrorsByWithErrorType(String type){
        return new ArrayList<>(queryArchivedErrorsService.allArchivedErrorsWithErrorType(type));
    }


}
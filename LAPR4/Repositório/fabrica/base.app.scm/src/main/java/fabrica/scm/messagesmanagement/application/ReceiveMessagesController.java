package fabrica.scm.messagesmanagement.application;

import fabrica.scm.messagesmanagement.server.SCMFunctionalities;

public class ReceiveMessagesController {

    public void readMessages(String id) throws Exception {
        SCMFunctionalities scm = new SCMFunctionalities();
        scm.runServer(id);
        scm.runClient();
    }
}

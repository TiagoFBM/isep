package fabrica.scm.messagesmanagement.exceptions;

public class InvalidProductionOrderException extends IllegalArgumentException {

    public InvalidProductionOrderException(){
        super("Invalid Production Order Code!");
    }

    public InvalidProductionOrderException(String mensagem){
        super(mensagem);
    }

}

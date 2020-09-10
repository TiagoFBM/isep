package fabrica.scm.messagesmanagement.exceptions;

public class InvalidProductException extends IllegalArgumentException {

    public InvalidProductException(){
        super("Invalid Product Code!");
    }

    public InvalidProductException(String mensagem){
        super(mensagem);
    }

}

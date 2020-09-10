package fabrica.scm.messagesmanagement.exceptions;

public class InvalidMessageParameters extends IllegalArgumentException {

    public InvalidMessageParameters(){
        super("Invalid Number of Parameters in Message!");
    }

    public InvalidMessageParameters(String mensagem){
        super(mensagem);
    }

}

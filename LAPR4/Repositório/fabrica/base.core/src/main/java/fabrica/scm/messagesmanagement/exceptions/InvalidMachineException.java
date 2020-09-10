package fabrica.scm.messagesmanagement.exceptions;

public class InvalidMachineException extends IllegalArgumentException {

    public InvalidMachineException(){
        super("Invalid Machine!");
    }

    public InvalidMachineException(String mensagem){
        super(mensagem);
    }

}

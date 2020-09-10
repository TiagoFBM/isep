package fabrica.scm.messagesmanagement.exceptions;

public class InvalidDepositException extends IllegalArgumentException {

    public InvalidDepositException(){
        super("Invalid Deposit!");
    }

    public InvalidDepositException(String mensagem){
        super(mensagem);
    }

}
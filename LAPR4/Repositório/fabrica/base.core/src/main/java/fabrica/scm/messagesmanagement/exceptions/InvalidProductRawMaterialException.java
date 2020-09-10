package fabrica.scm.messagesmanagement.exceptions;

public class InvalidProductRawMaterialException extends IllegalArgumentException {

    public InvalidProductRawMaterialException(){
        super("Invalid Product/Raw Material Code!");
    }

    public InvalidProductRawMaterialException(String mensagem){
        super(mensagem);
    }

}

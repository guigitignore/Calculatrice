public class UnknownOperationException extends Exception{
    UnknownOperationException(String name){
        super("Unknown operation "+name);
    }
}

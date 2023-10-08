public class InsufficiantStackException extends Exception{
    InsufficiantStackException(int n){
        super("The stack need to have at least "+n+" elements");
    }

    InsufficiantStackException(){
        this(1);
    }
}

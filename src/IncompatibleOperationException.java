public class IncompatibleOperationException extends Exception {
    IncompatibleOperationException(String message){
        super(message);
    }

    IncompatibleOperationException(){
        this("Incompatible operations");
    }

    IncompatibleOperationException(IObjEmp o1,IObjEmp o2){
        this("Incompatible operation between "+ o1.getClass().getName()+ "and "+o2.getClass().getName());
    }

}

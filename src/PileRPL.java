import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PileRPL {
    private IObjEmp[] pile;
    private int nbObj;

    PileRPL(int maxObj){
        pile=new IObjEmp[maxObj];
        nbObj=0;
    }

    protected boolean isFull(){
        return nbObj==pile.length;
    }

    protected boolean isEmpty(){
        return nbObj==0;
    }

    public void doOperation(String operation) throws IncompatibleOperationException,StackIsFullException,StackIsEmptyException,UnknownOperationException{
        IObjEmp premier=pop();
        IObjEmp deuxieme=pop();

        try{
            Method method=IObjEmp.class.getMethod(operation,IObjEmp.class);
            push((IObjEmp)method.invoke(premier,deuxieme));
        }catch(SecurityException|IllegalAccessException|NoSuchMethodException|InvocationTargetException e){
            throw new UnknownOperationException(operation);
        }
    }

    public void push(IObjEmp obj) throws StackIsFullException{
        if (isFull()) throw new StackIsFullException();
        pile[nbObj]=obj;
        nbObj++;
    }

    public IObjEmp pop() throws StackIsEmptyException{
        if (isEmpty()) throw new StackIsEmptyException();
        nbObj--;        
        return pile[nbObj];
    }

    protected IObjEmp[] dump(){
        return Arrays.copyOfRange(pile, 0, nbObj);
    }

    public String toString(){
        return Arrays.toString(dump());
    }
}

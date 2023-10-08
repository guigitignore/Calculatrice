public interface IObjEmp {
    public String toString();

    public IObjEmp add(IObjEmp autre) throws IncompatibleOperationException;

    public IObjEmp substract(IObjEmp autre) throws IncompatibleOperationException;

    public IObjEmp multiply(IObjEmp autre) throws IncompatibleOperationException;

    public IObjEmp divide(IObjEmp autre) throws IncompatibleOperationException;

    public IObjEmp checkCompatibility(IObjEmp autre) throws IncompatibleOperationException;
}

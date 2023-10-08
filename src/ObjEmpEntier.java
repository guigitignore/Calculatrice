public class ObjEmpEntier implements IObjEmp {
    private int valeur;

    ObjEmpEntier(int valeur){
        this.valeur=valeur;
    }

    public String toString(){
        return Integer.toString(valeur);
    }

    public int get(){
        return valeur;
    }

    public ObjEmpEntier checkCompatibility(IObjEmp autre) throws IncompatibleOperationException {
        if (autre.getClass()!=this.getClass()) throw new IncompatibleOperationException();
        return (ObjEmpEntier)autre;
    }

    public ObjEmpEntier add(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpEntier autre=checkCompatibility(autre_);

        return new ObjEmpEntier(get()+autre.get());
    }

    public ObjEmpEntier substract(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpEntier autre=checkCompatibility(autre_);

        return new ObjEmpEntier(get()-autre.get());
    }

    public ObjEmpEntier multiply(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpEntier autre=checkCompatibility(autre_);

        return new ObjEmpEntier(get()*autre.get());
    }

    public ObjEmpEntier divide(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpEntier autre=checkCompatibility(autre_);

        return new ObjEmpEntier(get()/autre.get());
    }
}

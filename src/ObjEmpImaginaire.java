public class ObjEmpImaginaire implements IObjEmp{
    private double reel;
    private double imaginaire;

    ObjEmpImaginaire(double reel,double imaginaire){
        this.reel=reel;
        this.imaginaire=imaginaire;
    }

    public double partieReelle(){
        return reel;
    }

    public double partieImaginaire(){
        return imaginaire;
    }

    public String toString(){
        if (partieReelle()==0) return Double.toString(partieImaginaire())+"i";
        if (partieImaginaire()==0) return Double.toString(partieReelle());
        return Double.toString(partieReelle())+"+"+Double.toString(partieImaginaire())+"i";
    }

    public ObjEmpImaginaire checkCompatibility(IObjEmp autre) throws IncompatibleOperationException {
        if (autre.getClass()!=this.getClass()) throw new IncompatibleOperationException();
        return (ObjEmpImaginaire)autre;
    }

    public ObjEmpImaginaire add(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpImaginaire autre=checkCompatibility(autre_);

        return new ObjEmpImaginaire(
            this.partieReelle()+autre.partieReelle(), 
            this.partieImaginaire()+autre.partieImaginaire()
        );
    }

    public ObjEmpImaginaire substract(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpImaginaire autre=checkCompatibility(autre_);

        return new ObjEmpImaginaire(
            this.partieReelle()-autre.partieReelle(), 
            this.partieImaginaire()-autre.partieImaginaire()
        );
    }

    public ObjEmpImaginaire multiply(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpImaginaire autre=checkCompatibility(autre_);

        return new ObjEmpImaginaire(
            this.partieReelle()*autre.partieReelle()-this.partieImaginaire()*autre.partieImaginaire(), 
            this.partieReelle()*autre.partieImaginaire()-this.partieImaginaire()*autre.partieReelle()
        );
    }

    public ObjEmpImaginaire divide(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpImaginaire autre=checkCompatibility(autre_);

        double dividende=autre.partieReelle()*autre.partieReelle() + autre.partieImaginaire()*autre.partieImaginaire();

        return new ObjEmpImaginaire(
            (this.partieReelle()*autre.partieReelle()+this.partieImaginaire()*autre.partieImaginaire())/dividende, 
            (this.partieImaginaire()*autre.partieReelle()-this.partieReelle()*autre.partieImaginaire())/dividende
        );
    }

 
}

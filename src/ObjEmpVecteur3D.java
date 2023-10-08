public class ObjEmpVecteur3D implements IObjEmp {
    private double x;
    private double y;
    private double z;

    ObjEmpVecteur3D(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

    public String toString(){
        return "("+Double.toString(getX())+","+Double.toString(getY())+","+Double.toString(getZ())+")";
    }

    public ObjEmpVecteur3D checkCompatibility(IObjEmp autre) throws IncompatibleOperationException {
        if (autre.getClass()!=this.getClass()) throw new IncompatibleOperationException();
        return (ObjEmpVecteur3D)autre;
    }

    public ObjEmpVecteur3D add(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpVecteur3D autre=checkCompatibility(autre_);

        return new ObjEmpVecteur3D(getX()+autre.getX(),getY()+autre.getY(),getZ()+autre.getZ());
    }

    public ObjEmpVecteur3D substract(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpVecteur3D autre=checkCompatibility(autre_);

        return new ObjEmpVecteur3D(getX()-autre.getX(),getY()-autre.getY(),getZ()-autre.getZ());
    }

    public ObjEmpVecteur3D multiply(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpVecteur3D autre=checkCompatibility(autre_);

        return new ObjEmpVecteur3D(getX()*autre.getX(),getY()*autre.getY(),getZ()*autre.getZ());
    }

    public ObjEmpVecteur3D divide(IObjEmp autre_) throws IncompatibleOperationException{
        ObjEmpVecteur3D autre=checkCompatibility(autre_);

        return new ObjEmpVecteur3D(getX()/autre.getX(),getY()/autre.getY(),getZ()/autre.getZ());
    }
}

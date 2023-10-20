import java.io.IOException;
import java.io.InputStream;
import java.io.PipedOutputStream;

public class CalcInputStreamThread extends Thread{
    private InputStream in;
    private PipedOutputStream pout;

    CalcInputStreamThread(InputStream pin,PipedOutputStream pout){
        this.in=pin;
        this.pout=pout;
        run();
    }

    public void start(){
        byte[] bytes=new byte[256];
        boolean quit=false;
        int len;

        while (quit!=true){
            try{
                len=in.read(bytes);
                System.out.println("read"+len+"bytes");
                pout.write(bytes,0,len);
            }catch(IOException e){
                quit=true;
            }
        }
    }
}

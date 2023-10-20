import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class CalcInputStream extends InputStream{

    ArrayList<Thread> threads;
    public PipedInputStream pin;
    public PipedOutputStream pout;

    CalcInputStream() throws IOException{
        this.pout=new PipedOutputStream();
        this.pin=new PipedInputStream(pout);
        threads=new ArrayList<>();
    }

    public void add(InputStream stream){
        Thread t=new Thread(){
            public void run(){
                byte[] bytes=new byte[8192];
                int len;

                try{
                    while ((len=stream.read(bytes))!=-1){
                        synchronized(pout){
                            pout.write(bytes,0,len);
                        }
                    }
                    synchronized(threads){
                        threads.remove(this);
                        if (threads.isEmpty()){
                            synchronized(pout){
                                pout.close();
                            }
                        }
                    }
                    
                    
                }catch(IOException e){
                    System.out.println("exception");
                }
                
            }
        };
        synchronized(threads){
            threads.add(t);
        }
        t.start();
    }
    
    @Override
    public int read() throws IOException {
        
        return pin.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return pin.read(b);
    }

    @Override
    public int read(byte[] b,int off,int len) throws IOException {
        int result=pin.read(b,off,len);
        return result;
    }
}

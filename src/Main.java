import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    static InputStream replay=null;
    static OutputStream log=null;
    static PileRPL shared=null;
    static ServerSocket remote=null;
    static Socket socket=null;
    public static void main(String [] argv){
        
        
        

        for (String arg:argv){
            switch(arg){
                case "replay":
                    try{
                        replay=new FileInputStream("log.txt");
                    }catch(FileNotFoundException e){
                        System.out.println("Replay error:"+e.getMessage());
                    }
                    break;
                case "log":
                    try{
                        log=new FileOutputStream("log.txt");
                    }catch(FileNotFoundException e){
                        System.out.println("Log error:"+e.getMessage());
                    }
                    break;
                case "shared":
                    shared=new PileRPL();
                    break;
                case "remote":
                    try{
                        remote=new ServerSocket(1111);
                    }catch(IOException e){
                        System.out.println("Remote error:"+e.getMessage());
                    }
                    break;   
            }
        }
        
        if (remote!=null){
            while (true){
                try{
                    socket=remote.accept();
                }catch(IOException e){
                    break;
                }
                
                new Thread(){
                    public void run(){
                        
                        try{
                            OutputStreamMultiplexer out=new OutputStreamMultiplexer();
                            out.add(socket.getOutputStream());

                            if (log!=null){
                                out.add(log);
                            }
                            if (shared!=null){
                                new CalcUI(shared, socket.getInputStream(), new PrintStream(out));
                            }else{
                                new CalcUI(socket.getInputStream(), new PrintStream(socket.getOutputStream()));
                            }
                            out.close();
                            socket.close();
                        }catch(IOException e){}
                        
                        
                    }
                }.start();


            }
            
        }else{
            new CalcUI();
        }

    }
}

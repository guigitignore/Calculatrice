import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {


    public static void main(String [] argv){
        
        Hashtable<String, Boolean> dict= new Hashtable<>();
        ServerSocket server=null;
        BufferedReaderRepeater in=null;
        

        for (String arg:argv){
            dict.put(arg,true);
        }

        if (dict.get("remote")!=null){
            try{
                server=new ServerSocket(1111);
                System.out.println("Listening on port "+server.getLocalPort());

                PileRPL shared=dict.get("shared")!=null ? new PileRPL() : null;

                while (true){
                    
                    Socket socket=server.accept();
                    
                    if (dict.get("replay")!=null){
                        in=new BufferedReaderRepeater(new InputStreamReader(new FileInputStream("log.txt")));
                        in.addRepeater(new PrintStream(socket.getOutputStream()));
                    }else{
                        in=new BufferedReaderRepeater(new InputStreamReader(socket.getInputStream()));
                    }

                    new RemoteThread(socket, shared==null? new PileRPL() : shared,in);
                }
                
            }catch(IOException e){
                System.err.println(e.getMessage());
                try{
                    server.close();
                }catch(IOException ex){}
                
            }
            
        }else{
            try{
                if (dict.get("log")!=null){
                    in=new BufferedReaderRepeater(new InputStreamReader(System.in));
                    in.addRepeater(new PrintStream("log.txt"));
                }else{
                    if (dict.get("replay")!=null){
                        in=new BufferedReaderRepeater(new InputStreamReader(new FileInputStream("log.txt")));
                        in.addRepeater(System.out);
                    }else{
                        in=new BufferedReaderRepeater(new InputStreamReader(System.in));
                    }
                }

                new CalcUI(in,System.out);
            }catch(FileNotFoundException e){
                System.err.println(e.getMessage());
            }
            
        }

    }
}

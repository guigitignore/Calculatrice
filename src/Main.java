import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String [] argv) throws IOException{
        CalcInputStream in=new CalcInputStream();
        CalcOutputStream out=new CalcOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        new Thread(){
            public void run(){
                new CalcUI();
            }
        }.start();

        ServerSocket server=new ServerSocket(1111);
        
        while (true){
            Socket socket=server.accept();
            in.add(socket.getInputStream());
            out.add(socket.getOutputStream());
        }

        

    }
}

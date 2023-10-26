import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class RemoteThread extends Thread{
    private Socket socket;
    private PileRPL pile;
    private BufferedReader in;

    RemoteThread(Socket socket,PileRPL pile,BufferedReader in) throws FileNotFoundException{
        this.pile=pile;
        this.socket=socket;

        this.in=in;
        
        start();
    }

    

    public void run(){
        try{
            new CalcUI(pile, in, new PrintStream(socket.getOutputStream()));
            socket.close();
        }catch(IOException e){}
    }
}

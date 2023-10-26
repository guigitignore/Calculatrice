import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;

public class BufferedReaderRepeater extends BufferedReader{

    public ArrayList<PrintStream> repeaters;

    public BufferedReaderRepeater(Reader in) {
        super(in);
        repeaters=new ArrayList<PrintStream>();
    }

    public void addRepeater(PrintStream out){
        repeaters.add(out);
    }

    @Override
    public String readLine() throws IOException{
        String result=super.readLine();
        if (result!=null){
            for (PrintStream repeater:repeaters) repeater.println(result);
        }
        return result;
    }
    
}

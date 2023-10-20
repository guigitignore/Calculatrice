import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class CalcOutputStream extends OutputStream{

    public List<OutputStream> streams;

    CalcOutputStream(){
        streams=new ArrayList<>();
    }

    public void add(OutputStream stream){
        streams.add(stream);
    }

    @Override
    public void write(int b) throws IOException {
        for (OutputStream stream:streams){
            stream.write(b);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (OutputStream stream:streams){
            stream.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (OutputStream stream:streams){
            stream.write(b,off,len);
        }
    }

    @Override
    public void flush() throws IOException {
        for (OutputStream stream:streams){
            stream.flush();
        }
    }
}
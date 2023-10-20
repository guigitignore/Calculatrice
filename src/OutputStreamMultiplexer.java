import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

class OutputStreamMultiplexer extends OutputStream{

    public ArrayList<OutputStream> streams;

    OutputStreamMultiplexer(){
        streams=new ArrayList<>();
    }

    public OutputStreamMultiplexer add(OutputStream stream){
        streams.add(stream);
        return this;
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

package mentalPoker.Bob;

import java.io.*;
import java.net.Socket;

public class Network {

    private Socket _alice;
    private ObjectOutputStream _write;
    private ObjectInputStream _read;

    public Network(String aliceIp, int port){

        try {
            _alice = new Socket(aliceIp, port);
            OutputStream os = _alice.getOutputStream();
            _write = new ObjectOutputStream(os);
            InputStream is = _alice.getInputStream();
            _read = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object o){
        try {
            _write.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read(){
        try {
            return _read.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(){
        try {
            _alice.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

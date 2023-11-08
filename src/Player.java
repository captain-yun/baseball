import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    Socket socket;
    private String id;
    private DataOutputStream dos;
    private DataInputStream dis;

    public Player(Socket socket) throws IOException {
        this.socket = socket;
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
//        new Thread(()-> {
//            interact();
//        }).start();
        this.id = read();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    void send(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    String read() {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

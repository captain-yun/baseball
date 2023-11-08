import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static Socket socket = null;
    static DataInputStream dis = null;
    static DataOutputStream dos = null;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 50001);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            System.out.print("사용자 ID를 입력해주세요 >> ");
            send(sc.nextLine());

            while (true) {
                System.out.println("게임 찾는중...");
                Thread.sleep(1000);

                String msg = read();
                if (msg.equals("START")) {
                    playGame();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void playGame() {
        String msg = "";
        while (true) {
            msg = read();
            if (msg.equals("DIGITS")) {
                System.out.print("자릿수를 설정해주세요 >> ");
                send(sc.nextLine());
            } else if (msg.equals("ALL")) {
                System.out.println(read());
            } else if (msg.equals("TURN")) {
                System.out.print("정답 >> ");
                send(sc.nextLine());
            } else if (msg.equals("END")) {
                System.out.println("게임을 다시 하시겠습니까?(y/n)");
                send(sc.nextLine());
            }
        }
    }

    static void send(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static String read() {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameServer {
    static ServerSocket serverSocket = null;
    static ArrayList<Player> players = new ArrayList<>();
    static boolean isPlay = false;

    public static void main(String[] args) {
        try {
            System.out.println("[서버] start");
            listenConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 사용자 입력 대기
    static void listenConnection() throws IOException {
        serverSocket = new ServerSocket(50001);
        while (true) {
            // 사용자 입력 대기
            Socket client = serverSocket.accept();
            players.add(new Player(client));

            // check if there are 2 players connected.
            if (players.size() == 2) {
                playGame();
            }
        }
    }

    void 게임대기방() {
        // 사용자가 ready 상태가 되면 ready중인 사용자 리스트에 넣기
        // 플레이어 2인 이상되면 게임 시작
    }

    static void playGame() {
        sendAll("START");

        int answer[] = null;

        // 플레이어 A, B
        // 방설정 만들기
        // 숫자 자리수 (3, 4)
        // 랜덤 숫자 만들기
        // "158"
        players.get(0).send("DIGITS");
        String digits = players.get(0).read();
        answer = Game.createRandomNumbers(Integer.parseInt(digits));
        System.out.println("정답 :" + Game.arrToStr(answer));

        sendAllForPrint("[서버] 게임을 시작합니다... 숫자의 자리수는 " + digits + "개 입니다.");

        int inning = 0;
        while (true) {
            Player player = nextPlayer(inning);

            player.send("TURN");
            String guess = player.read();
            int[] guessNums = Game.strToArr(guess);

            sendAllForPrint((inning + 1) + "회 [" + player.getId() + "] >> " + guess);
            sendAllForPrint(Game.getStrikeAndBall(guessNums, answer));

            if (Game.checkAnswer(guessNums, answer)) {
                sendAllForPrint((player.getId() + "님이 정답을 맞췄습니다!!!"));
                sendAll("END");
                boolean y = readAll().stream().allMatch((read) -> read.equals("y"));
                if (y == true) {
                    players.get(0).send("DIGITS");
                    digits = players.get(0).read();
                    answer = Game.createRandomNumbers(Integer.parseInt(digits));
                    inning = 0;
                    sendAllForPrint("[서버] 게임을 시작합니다... 숫자의 자리수는 " + digits + "개 입니다.");
                    continue;
                } else {
                    System.out.println("[서버] 게임종료");
                    return;
                }
            }
            inning++;
        }
    }

    static private Player nextPlayer(int inning) {
        return players.get(inning % 2);
    }

    static void sendAll(String msg) {
        players.stream().forEach(
            (player) -> {
                player.send(msg);
            }
        );
    }
    static void sendAllForPrint(String msg) {
        players.stream().forEach(
                (player) -> {
                    player.send("ALL");
                    player.send(msg);
                }
        );
    }

    static List<String> readAll() {
        int count = 0;
        List<String> read = new ArrayList<>();
        for (Player player : players) {
            read.add(player.read());
        }
        return read;
    }
}

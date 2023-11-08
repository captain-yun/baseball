import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        do {
            startGame();
            System.out.println("You want play the game, again?");
            System.out.print("(Y/N) >> ");
            String isAgain = sc.nextLine();
            if (!isAgain.toLowerCase().equals("y")) {
                System.out.println("finished...");
                break;
            }
        } while (true);


    }

    static void startGame() {
        System.out.print("Please enter the length of numbers >> ");
        int n = Integer.parseInt(sc.nextLine());
        System.out.print("Please enter how many innings to play? >> ");
        int inning = Integer.parseInt(sc.nextLine());

        int answer[] = createRandomNumbers(n);

        System.out.println("======= start a game ========");
        int i = 1;
        while (i <= inning) {
            System.out.print(i + " time >> " );
            int[] guess = userInput();
            if (checkAnswer(answer, guess)) {
                System.out.println("You win!!!");
                return;
            }
            i++;
        }
        System.out.println("You lose...");

        String strAnswer = "";
        for (int a : answer) {
            strAnswer += String.valueOf(a);
        }
        System.out.println("The answer was " +  strAnswer);
    }

    // 사용자로부터 입력을 받아 배열에 저장
    static int[] userInput() {
        String input = sc.nextLine();
        String[] split = input.split("");
        return Arrays.stream(split).mapToInt(str -> Integer.parseInt(str)).toArray();
    }

    // 랜덤으로 n자리 숫자를 생성
        // int answer = (int) (Math.random() * 10);
    static int[] createRandomNumbers(int n) {
        int[] randomNumbers = new int[n];
        int[] pickedNumbers = new int[n];
        // 초기화
        for (int i = 0; i < pickedNumbers.length; i++) {
            pickedNumbers[i] = 99;
        }

        return Arrays.stream(randomNumbers).map(
                num -> {
                    while (true) {
                        int randomNum = (int) (Math.random() * 10);
                        if (!isAlreadyPicked(pickedNumbers, randomNum)) {
                            insertNumber(pickedNumbers, randomNum);
                            return randomNum;
                        }
                        // 이미 뽑은거라면 반복
                    }
                }
        ).toArray();
    }

    private static void insertNumber(int[] pickedNumbers, int randomNum) {
        for (int i = 0; i < pickedNumbers.length; i++) {
            if (pickedNumbers[i] == 99) {
                pickedNumbers[i] = randomNum;
            }
        }
    }

    private static boolean isAlreadyPicked(int[] pickedNumbers, int randomNum) {
        for (int picked : pickedNumbers) {
            if (randomNum == picked) {
                return true;
            }
        }
        return false;
    }

    // 답 판별 기능
    static boolean checkAnswer(int[] guess, int[] answer) {
        boolean isGuessPerfect = true;
        int strike = 0;
        int ball = 0;

        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == answer[i]) {
                strike++;
            } else {
                isGuessPerfect = false;
                int finalI = i;
                if (Arrays.stream(answer).anyMatch(num -> num == guess[finalI])) {
                    ball++;
                }
            }
        }
        System.out.printf("%dS %dB\n", strike, ball);
        return isGuessPerfect;
    }
}
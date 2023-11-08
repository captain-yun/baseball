import java.util.Arrays;

public class Game {
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
    static String getStrikeAndBall(int[] guess, int[] answer) {
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
        return String.format("%dS %dB", strike, ball);
    }

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
        return isGuessPerfect;
    }

    static int[] strToArr(String input) {
        String[] split = input.split("");
        return Arrays.stream(split).mapToInt(str -> Integer.parseInt(str)).toArray();
    }

    static String arrToStr(int[] arr) {
        String str = "";
        for (int n : arr) {
            str += n;
        }
        return str;
    }
}

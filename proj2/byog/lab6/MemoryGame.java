package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Please enter a seed");
//            return;
//        }

//        int seed = Integer.parseInt(args[0]);
        int seed = 12345;
        MemoryGame game = new MemoryGame(60, 60);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random();

    }

    public String generateRandomString(int n) {
        char[] RandomChar = new char[n];
        for (int i = 0; i < n; i += 1) {
            RandomChar[i] = CHARACTERS[rand.nextInt(26)];
        }

        return new String(RandomChar);
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        String encouragement = ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
        String step = "Watch!";
        if (playerTurn) {
            step = "Now it's your turn!";
        }

        if (gameOver) {
            StdDraw.text(this.width * 0.5, this.height - 1, "Game Over! You made it to round: " + round);
            StdDraw.line(0, this.height - 2, this.width, this.height - 2);
            StdDraw.show();
        } else {
            // 画框架
            Font headFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(headFont);
            StdDraw.text(8, this.height - 1, "Round: " + round);
            StdDraw.text(this.width * 0.5, this.height - 1, step);
            if (playerTurn) {
                StdDraw.text(this.width - 10, this.height - 1, encouragement);
            }
            StdDraw.line(0, this.height - 2, this.width, this.height - 2);

            // 写字
            if (s != null) {
                Font font = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setFont(font);
                StdDraw.text(this.width * 0.5, this.height * 0.5, s);
            }
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) throws InterruptedException {
        char[] StringArray = letters.toCharArray();
        for (char c : StringArray) {
            drawFrame(String.valueOf(c));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            drawFrame(null);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String solicitNCharsInput(int n) {
        StringBuilder input= new StringBuilder();
        drawFrame(null);
        while (input.length() < n) {
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                input.append(c);
            }
        }
        return input.toString();
    }


    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        while (true) {
            playerTurn = false;
            String RandomString = generateRandomString(round);
            try {
                flashSequence(RandomString);
            } catch (InterruptedException e) {
                System.err.println("The sequence was interrupted: " + e.getMessage());
            }

            playerTurn = true;
            String UserString = solicitNCharsInput(round);
            if (! UserString.equals(RandomString)) {
                gameOver = true;
                drawFrame(null);
                break;
            }

            round += 1;
        }

    }


}

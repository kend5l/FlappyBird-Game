import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    private Menu menu;
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int height = pipeHeight;
        int width = pipeWidth;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }


    // game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();
    
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;
    double highScore = 0;
    private int difficulty;

    public FlappyBird(JFrame frame, int difficulty) {
        
        this.difficulty = difficulty;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // load images
        birdImg =  new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // load bird and pipes
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();
        menu = new Menu(frame);

        // place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();
        
        // game timer 30FPS
        gameLoop = new Timer(1000/30, this);
        gameLoop.start();
        
    }

    public void placePipes() {
        // determine difficulty and adjust pipe gaps accordingly
        int diffDistance = 0;
        if (difficulty == 1) {diffDistance = 4;}
        if (difficulty == 2) {diffDistance = 6;}
        if (difficulty == 3) {diffDistance = 7;}

       // pipes can be 1/4 of og height up to 3/4 of og height
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / diffDistance;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 30));

        if (gameOver) {
            if (score > highScore) {
                highScore = score;
            }
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
            g.drawString("High Score: " + String.valueOf((int) highScore), 10, 70);

        }    
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }    


    }

    public void move() {
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        // bird cannot move past 0
        bird.y = Math.max(bird.y, 0);
        // pipes
        for ( int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // .5 bc there are two pipes
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && 
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
       if (gameOver) {
        placePipesTimer.stop();
        gameLoop.stop();
       }
    }

       @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if(gameOver) {
                // restart game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}

    
}
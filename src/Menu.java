import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel {
    // main menu dimensions
    int menuWidth = 600;
    int menuHeight = 600;
    boolean isSelected = false;

    private JButton startButton;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    Graphics g;
    //Image menuBackgroundImg;
   

    public Menu(JFrame menuFrame) {
        // set layout to null so we can freely place labels and buttons
        setLayout(null);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
       // menuBackgroundImg = new ImageIcon(getClass().getResource("./menubg.jpg")).getImage();
        //g.drawImage(menuBackgroundImg, 0,0,menuWidth, menuHeight, null);

        addContents(menuFrame);
    }


    private void addContents(JFrame menuFrame) {

        JLabel welcomeLabel = new JLabel("Welcome to Flappy Bird!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setBounds(145,50, 300, 30);
        add(welcomeLabel);
        
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font. BOLD, 20));
        startButton.setBounds(220,180, 140, 70);
        add(startButton);

        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font. BOLD, 15));
        easyButton.setBounds(230, 300, 120, 40);
        add(easyButton);

        normalButton = new JButton("Normal");
        normalButton.setFont(new Font("Arial", Font. BOLD, 15));
        normalButton.setBounds(230, 370, 120, 40);
        add(normalButton);

        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font. BOLD, 15));
        hardButton.setBounds(230, 440, 120, 40);
        add(hardButton);

         // difficulty button listeners
        easyButton.addActionListener(e -> {selectDifficulty(easyButton);
        String difficulty = getDifficulty();
        isSelected = true;});
        
        normalButton.addActionListener(e -> {selectDifficulty(normalButton);
        String difficulty = getDifficulty();
        isSelected = true;});

        hardButton.addActionListener(e -> {selectDifficulty(hardButton);
        String difficulty = getDifficulty();
        isSelected = true;});

        // once start is pressed, the menu will be disposed and the game will start
          startButton.addActionListener(e -> {
            if (isSelected == false) {
                System.out.println("Please select a difficulty.");
                return;
            }else {
                // begin the game
                menuFrame.dispose(); 
                startFlappyBird();
            }
        });


    }
    // flappy bird frame
     private void startFlappyBird() {

        int boardWidth = 360;
        int boardHeight = 640;
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new  FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);

    }

    public void selectDifficulty(JButton selectedButton) {
        // reset all buttons
        easyButton.setBackground(null);
        normalButton.setBackground(null);
        hardButton.setBackground(null);
        // change color
        selectedButton.setBackground(Color.GREEN);
    }

    public String getDifficulty() {
       if (easyButton.getBackground() == Color.GREEN) return "Easy";
       else if (normalButton.getBackground() == Color.GREEN) return "Normal";
       else if (hardButton.getBackground() == Color.GREEN) return "Hard";
       else return "none";
    }
    


}

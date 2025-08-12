import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        // main menu frame
        JFrame menuFrame = new JFrame("Flappy Bird Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menuPanel = new Menu(menuFrame);

        menuFrame.add(menuPanel);
        menuFrame.pack();
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

    } 

}

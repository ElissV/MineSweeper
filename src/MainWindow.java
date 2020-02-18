import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game_components.Box;
import game_components.Coordinate;
import game_components.Game;
import game_components.Ranges;

public class MainWindow extends JFrame {

    private Game game;

    private JPanel jPanel;
    private JLabel label;

    private final int IMAGE_SIZE = 50;


    public static void main(String[] args) {
        new MainWindow();
    }

    private MainWindow() {
        game = new Game();
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.NORTH);
    }

    private void initPanel() {
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coordinate coord : Ranges.getAllCoordinates()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coord = new Coordinate(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                jPanel.repaint();
            }
        });

        jPanel.setPreferredSize(new Dimension(
                IMAGE_SIZE * Ranges.getSize().x,
                IMAGE_SIZE * Ranges.getSize().y));
        add(jPanel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAY:
                return "Think twice!";
            case BOMBED:
                return "YOU LOSE!";
            case WINNER:
                return "CONGRATULATIONS!!!";
            default:
                return "Welcome!";
        }
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String fileName) {
        String filePath = "img/" + fileName.toLowerCase() + ".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(filePath));
        return imageIcon.getImage();
    }

}
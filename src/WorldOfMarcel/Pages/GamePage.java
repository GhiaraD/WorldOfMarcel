package WorldOfMarcel.Pages;

import WorldOfMarcel.Characters.Character;
import WorldOfMarcel.Map.Cell;
import WorldOfMarcel.Map.Grid;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GamePage extends JFrame {

    public GamePage(Character currentCharacter, Map<Cell.CellEnum, List<String>> stories) {
        super("Choose Account and Character");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(150, 150));
        setLayout(new BorderLayout());

        ImageIcon unvisitedImage = resizeImage("res/helpCircle.png");
        ImageIcon visitedImage = resizeImage("res/forest.png");
        ImageIcon playerImage = resizeImage("res/account.png");
        ImageIcon shopImage = resizeImage("res/shop.png");
        ImageIcon enemyImage = resizeImage("res/enemy.png");
        ImageIcon finishImage = resizeImage("res/flag.png");

        Random rand = new Random();
        int length = rand.nextInt(6) + 3; // between 3 and 8 rows and columns
        int width = rand.nextInt(6) + 3;
        Grid map = Grid.generateGrid(length, width, currentCharacter);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(length, width));
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                JPanel labelPanel = new JPanel();
                Cell currentCell = map.get(i).get(j);
                labelPanel.setPreferredSize(new Dimension(70, 70));

                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(60, 60));
                label.setBackground(Color.CYAN);
                Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
                label.setBorder(border);
                if (currentCharacter.ox == i && currentCharacter.oy == j) {
                    label.setIcon(playerImage);
                } else if (!currentCell.visited) {
                    label.setIcon(unvisitedImage);
                } else if (currentCell.type == Cell.CellEnum.ENEMY) {
                    label.setIcon(enemyImage);
                } else if (currentCell.type == Cell.CellEnum.SHOP) {
                    label.setIcon(shopImage);
                } else if (currentCell.type == Cell.CellEnum.FINISH) {
                    label.setIcon(finishImage);
                } else {
                    label.setIcon(visitedImage);
                }
                labelPanel.add(label);
                panel.add(labelPanel, j + i * width);
            }
        }

        add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    ImageIcon resizeImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

}

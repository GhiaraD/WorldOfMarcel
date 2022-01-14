package WorldOfMarcel.Pages;

import WorldOfMarcel.Characters.Character;
import WorldOfMarcel.Game;
import WorldOfMarcel.Map.Cell;
import WorldOfMarcel.Map.Enemy;
import WorldOfMarcel.Map.Grid;
import WorldOfMarcel.Map.Shop;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePage extends JFrame {
    Grid map;
    JPanel panel;
    Character currentCharacter;

    public GamePage(Character currentCharacter) {
        super("Play the game!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(150, 150));
        setLayout(new BorderLayout());

        this.currentCharacter = currentCharacter;

        Random rand = new Random();
        int length = rand.nextInt(6) + 3; // between 3 and 8 rows and columns
        int width = rand.nextInt(6) + 3;
        this.map = Grid.generateGrid(length, width, currentCharacter);

        this.panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(length, width));
        printMap(map, panel);

        JButton startButton = new JButton("Go!");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharacter.currentHealth <= 0) {
                    setVisible(false);
                    dispose();
                    System.out.println("You are dead!");
                }
                gameLoop();
            }
        });

        add(startButton, BorderLayout.PAGE_START);
        add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void printMap(Grid map, JPanel panel) {
        ImageIcon unvisitedImage = resizeImage("res/helpCircle.png");
        ImageIcon visitedImage = resizeImage("res/forest.png");
        ImageIcon playerImage = resizeImage("res/account.png");
        ImageIcon shopImage = resizeImage("res/shop.png");
        ImageIcon enemyImage = resizeImage("res/enemy.png");
        ImageIcon finishImage = resizeImage("res/flag.png");

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.width; ++j) {
                JPanel labelPanel = new JPanel();
                Cell currentCell = map.get(i).get(j);
                labelPanel.setPreferredSize(new Dimension(70, 70));

                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(60, 60));
                label.setBackground(Color.CYAN);
                Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
                label.setBorder(border);
                if (map.currentCell.ox == i && map.currentCell.oy == j) {
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
                panel.add(labelPanel, j + i * map.width);
            }
        }
    }

    public void gameLoop() {
        Game game = Game.getInstance();
        if (!map.currentCell.visited) {
            game.printStory(map.currentCell);
            game.gainUnvisitedCellXP(currentCharacter);
            game.getMoneyMaybe(currentCharacter, false);
        }
        map.currentCell.visited = true;

        String command = game.optionsAndTakeNextCommand(currentCharacter, map.currentCell, false);
        if (map.currentCell.type == Cell.CellEnum.ENEMY && ((Enemy) map.currentCell.cellElement).currentHealth > 0) {
            Enemy enemy = (Enemy) map.currentCell.cellElement;
            // Fight
            while (enemy.currentHealth > 0 && currentCharacter.currentHealth > 0) {
                int commandInt = Integer.parseInt(command);
                if (commandInt == 1) {
                    enemy.receiveDamage(currentCharacter.getDamage());
                } else if (commandInt <= currentCharacter.spells.size() + 1) {
                    currentCharacter.useSpell(currentCharacter.spells.get(commandInt - 2), enemy);
                } else {
                    currentCharacter.inventory.potions.get(commandInt - currentCharacter.spells.size() - 2).usePotion(currentCharacter);
                    currentCharacter.inventory.potions.remove(commandInt - currentCharacter.spells.size() - 2);
                }

                if (enemy.currentHealth > 0)
                    enemy.makeAutomatedAttack(currentCharacter);
                System.out.println("Health: You-> " + currentCharacter.currentHealth + '/' + currentCharacter.maxHealth + "   Enemy-> " + enemy.currentHealth + '/' + enemy.maxHealth);
                System.out.println("Mana: You-> " + currentCharacter.currentMana + '/' + currentCharacter.maxMana + "   Enemy-> " + enemy.currentMana + '/' + enemy.maxMana + '\n');
                if (enemy.currentHealth >= 0)
                    command = game.optionsAndTakeNextCommand(currentCharacter, map.currentCell, false);
            }
            game.gainFightXP(currentCharacter);
            game.getMoneyMaybe(currentCharacter, true);
        } else if (map.currentCell.type == Cell.CellEnum.SHOP && ((Shop) map.currentCell.cellElement).potions.size() > 0) {
            Shop shop = (Shop) map.currentCell.cellElement;
            while (shop.potions.size() > 0) {
                int commandInt = Integer.parseInt(command);
                if (commandInt == 0) {
                    shop.potions.clear();
                } else if (commandInt <= shop.potions.size()) {
                    currentCharacter.inventory.potions.add(shop.selectPotion(commandInt - 1));
                    System.out.println("You bought a potion!");
                } else {
                    while (shop.potions.size() > 0) {
                        currentCharacter.inventory.potions.add(shop.selectPotion(0));
                    }
                    System.out.println("You bought all potions!");
                }
                if (shop.potions.size() > 0)
                    command = game.optionsAndTakeNextCommand(currentCharacter, map.currentCell, false);
            }
        } else if (map.currentCell.type == Cell.CellEnum.FINISH) {
            return;
        }

        // move
        if (command.equals("1")) {
            map.goNorth();
            updateGrid(panel);
        }
        if (command.equals("2")) {
            map.goEast();
            updateGrid(panel);
        }
        if (command.equals("3")) {
            map.goSouth();
            updateGrid(panel);
        }
        if (command.equals("4")) {
            map.goWest();
            updateGrid(panel);
        }
    }

    public void updateGrid(JPanel panel) {
        panel.removeAll();
        printMap(map, panel);
        panel.validate();
        panel.repaint();
    }

    ImageIcon resizeImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

}

package WorldOfMarcel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;

public class ChooseAccountPage extends JFrame {

    public ChooseAccountPage(ArrayList<Account> accounts){
        super("Choose Account and Character");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        DefaultListModel<Book> dlm = new DefaultListModel<>();

        // dlm.add(0, );


        JList<Book> list = new JList<>(dlm);
        list.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(list), BorderLayout.LINE_START);
        pack();
        setVisible(true);
    }

    private void chooseAccountAndCharacter(){
    }

    private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

}

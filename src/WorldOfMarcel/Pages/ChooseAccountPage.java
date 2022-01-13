package WorldOfMarcel.Pages;

import WorldOfMarcel.Account;
import WorldOfMarcel.Characters.Character;
import WorldOfMarcel.Map.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ChooseAccountPage extends JFrame {
    List<Character> characters = null;
    Account selectedAccount = null;
    Character selectedCharacter = null;

    public ChooseAccountPage(List<Account> accounts, Map<Cell.CellEnum, List<String>> stories) {
        super("Choose Account and Character");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));
        setLayout(new BorderLayout());

        DefaultListModel<Account> accountModel = new DefaultListModel<>();
        int index = 0;
        for (Account account : accounts) {
            accountModel.add(index, account);
            index++;
        }

        JTextField passwordField = new JTextField("Password");
        JList<Character> characterList = new JList<>();

        JList<Account> accountList = new JList<>(accountModel);
        accountList.setBorder(new EmptyBorder(10, 10, 10, 10));
        accountList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (accountList.getSelectedIndex() < 0) return;
                selectedAccount = accounts.get(accountList.getSelectedIndex());
                DefaultListModel<Character> characterModel = new DefaultListModel<>();
                characters = selectedAccount.accountCharacters;
                int characterIndex = 0;
                for (Character character : characters) {
                    characterModel.add(characterIndex, character);
                    characterIndex++;
                }
                characterList.setModel(characterModel);
            }
        });

        characterList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (characterList.getSelectedIndex() < 0) return;
                selectedCharacter = characters.get(characterList.getSelectedIndex());
            }
        });

        JButton button = new JButton("Confirm");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCharacter == null) return;
                if (passwordField.getText().equals(selectedAccount.info.credentials.getPassword())) {
                    System.out.println("It worked!");
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                    new GamePage(selectedCharacter, stories);
                }
            }
        });

        add(new JScrollPane(accountList), BorderLayout.LINE_START);
        add(new JScrollPane(passwordField), BorderLayout.LINE_END);
        add(new JScrollPane(characterList), BorderLayout.CENTER);
        add(new JScrollPane(button), BorderLayout.PAGE_END);
        pack();
        setVisible(true);
    }
}

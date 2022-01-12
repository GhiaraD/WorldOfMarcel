package WorldOfMarcel;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game{
    List<Account> accounts;
    Map<CellEnum, List<String>> stories;
    private static Game instance = null;

    private Game() {
        accounts = new ArrayList<>();
        stories = new HashMap<>();
        stories.put(CellEnum.EMPTY, new ArrayList<>());
        stories.put(CellEnum.ENEMY, new ArrayList<>());
        stories.put(CellEnum.SHOP, new ArrayList<>());
        stories.put(CellEnum.FINISH, new ArrayList<>());
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public void run() throws IOException {
        System.out.println("\nSelect your preferred game-mode by pressing:");
        System.out.println("'1' - for a text-only adventure in the command line");
        System.out.println("'2' - for a colorful GUI experience");
        Scanner input = new Scanner(System.in);
        int gameMode = input.nextInt();
        if (gameMode == 2){
            parseAccountsJSON();
        }

        // automated test
        else {
            parseAccountsJSON();
            parseStoriesJSON();
            Account currentAccount = chooseAccount();
            Character currentCharacter = chooseCharacter(currentAccount);

            // Start game
            System.out.println("Generating map...\n");
            Grid map = Grid.generateTestGrid(currentCharacter);
            int moves = 0; // the test has 8 moves on the map
            while (currentCharacter.currentHealth > 0) {
                if (!map.currentCell.visited) {
                    printStory(map.currentCell);
                    gainUnvisitedCellXP(currentCharacter);
                    getMoneyMaybe(currentCharacter, false);
                }
                map.currentCell.visited = true;
                map.printGrid();

                String command = optionsAndTakeNextCommand(currentCharacter, map.currentCell, input);
                if (map.currentCell.type == CellEnum.ENEMY && ((Enemy) map.currentCell.cellElement).currentHealth > 0) {
                    Enemy enemy = (Enemy) map.currentCell.cellElement;
                    // Fight
                    while (enemy.currentHealth > 0 && currentCharacter.currentHealth > 0) {
                        currentCharacter.makeAutomatedAttack(enemy);
                        if (enemy.currentHealth > 0)
                            enemy.makeAutomatedAttack(currentCharacter);
                        System.out.println("Health: You-> " + currentCharacter.currentHealth + '/' + currentCharacter.maxHealth + "   Enemy-> " + enemy.currentHealth + '/' + enemy.maxHealth);
                        System.out.println("Mana: You-> " + currentCharacter.currentMana + '/' + currentCharacter.maxMana + "   Enemy-> " + enemy.currentMana + '/' + enemy.maxMana + '\n');
                        if (enemy.currentHealth >= 0)
                            command = optionsAndTakeNextCommand(currentCharacter, map.currentCell, input);
                    }
                    gainFightXP(currentCharacter);
                    getMoneyMaybe(currentCharacter, true);
                } else if (map.currentCell.type == CellEnum.SHOP && ((Shop) map.currentCell.cellElement).potions.size() > 0) {
                    List<Potion> potions = ((Shop) map.currentCell.cellElement).potions;
                    // add first potion
                    currentCharacter.inventory.potions.add(potions.get(0));
                    potions.remove(0);
                    // add second potion
                    currentCharacter.inventory.potions.add(potions.get(0));
                    potions.remove(0);
                    System.out.println("You bought 2 potions!");
                } else if (map.currentCell.type == CellEnum.FINISH) {
                    return;
                }

                // 4 moves to the right, 4 down
                if (moves < 4) {
                    map.goEast();
                } else {
                    map.goSouth();
                }
                moves++;
            }
        }
    }

    public String optionsAndTakeNextCommand(Character currentCharacter, Cell currentCell, Scanner input) {
        String command = "";

        if (currentCell.type == CellEnum.ENEMY && ((Enemy) currentCell.cellElement).currentHealth > 0) {
            System.out.println("Your are in a fight! Choose:");
            System.out.println("1 - Attack");
            int index = 2;
            for (Spell spell : currentCharacter.spells) {
                System.out.println(index + " - " + spell);
                index++;
            }
            for (Potion potion : currentCharacter.inventory.potions) {
                System.out.println(index + " - " + potion);
                index++;
            }
        } else if (currentCell.type == CellEnum.SHOP && ((Shop) currentCell.cellElement).potions.size() > 0) {
            System.out.println("You have encountered a shop! Choose:");
            System.out.println("0 - Don't buy anything");
            int index = 1;
            for (Potion potion : ((Shop) currentCell.cellElement).potions) {
                System.out.println(index + " - " + potion);
                index++;
            }
            System.out.println(index + " - Buy everything");
        } else if (currentCell.type == CellEnum.FINISH) {
            System.out.println("Congratulations! You beat the map!");
        } else {
            System.out.println("1 - Go north");
            System.out.println("2 - Go east");
            System.out.println("3 - Go south");
            System.out.println("4 - Go west");
        }
        while (!command.equals("P"))
            try {
                command = readCommand(input);
            } catch (InvalidCommandException e) {
                System.out.println("Try 'P'");
            }
        return command;
    }

    private String readCommand(Scanner input) throws InvalidCommandException {
        String command = input.next();
        if (!command.equals("P"))
            throw new InvalidCommandException();
        return command;
    }

    private void printStory(Cell currentCell) {
        CellEnum cellType = currentCell.type;
        Random rand = new Random();
        int randomNum = rand.nextInt(stories.get(cellType).size());
        System.out.print('\n' + stories.get(cellType).get(randomNum) + '\n');
    }

    private void parseAccountsJSON() throws IOException {
        File file = new File("src/WorldOfMarcel/res/accounts.json");
        String accountsToString = FileUtils.readFileToString(file, "utf-8");
        JSONObject accountsToJson = new JSONObject(accountsToString);
        JSONArray jsonArray = (JSONArray) accountsToJson.get("accounts");

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject person = jsonArray.getJSONObject(i);

            JSONObject credentialsJSON = (JSONObject) person.get("credentials");
            String email = (String) credentialsJSON.get("email");
            String password = (String) credentialsJSON.get("password");
            Credentials credentials = new Credentials();
            credentials.setEmail(email);
            credentials.setPassword(password);

            String name = (String) person.get("name");
            String country = (String) person.get("country");

            JSONArray favoriteGamesJSON = (JSONArray) person.get("favorite_games");
            TreeSet<String> games = new TreeSet<>();
            for (int j = 0; j < favoriteGamesJSON.length(); ++j) {
                games.add(favoriteGamesJSON.get(j).toString());
            }

            int playedGames = Integer.parseInt((String) person.get("maps_completed"));

            JSONArray charactersJSON = (JSONArray) person.get("characters");
            List<Character> characters = new ArrayList<>();
            for (int j = 0; j < charactersJSON.length(); ++j) {
                JSONObject characterJSON = charactersJSON.getJSONObject(j);
                String characterName = (String) characterJSON.get("name");
                String profession = (String) characterJSON.get("profession");
                int level = Integer.parseInt((String) characterJSON.get("level"));
                int experience = (Integer) characterJSON.get("experience");
                characters.add(new CharacterFactory().buildCharacter(profession, characterName, experience, level));
            }

            Account.Information.InformationBuilder informationBuilder = null;
            try {
                informationBuilder = new Account.Information.InformationBuilder(credentials, name).setFavoriteGames(games).setCountry(country);
            } catch (InformationIncompleteException e) {
                System.out.println("Corrupted data! Closing...");
                return;
            }
            Account.Information information = informationBuilder.build();

            accounts.add(new Account(information, characters, playedGames));
        }
    }

    private void parseStoriesJSON() throws IOException {
        File file = new File("src/WorldOfMarcel/res/stories.json");
        String storiesToString = FileUtils.readFileToString(file, "utf-8");
        JSONObject storiesToJson = new JSONObject(storiesToString);
        JSONArray jsonArrayStories = (JSONArray) storiesToJson.get("stories");

        for (int i = 0; i < jsonArrayStories.length(); ++i) {
            JSONObject storyJSON = jsonArrayStories.getJSONObject(i);

            String type = (String) storyJSON.get("type");
            String value = (String) storyJSON.get("value");

            switch (type) {
                case "EMPTY" -> stories.get(CellEnum.EMPTY).add(value);
                case "ENEMY" -> stories.get(CellEnum.ENEMY).add(value);
                case "SHOP" -> stories.get(CellEnum.SHOP).add(value);
                default -> stories.get(CellEnum.FINISH).add(value);
            }
        }
    }

    private Account chooseAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Loading data...");
        int accountIndex = 1;
        for (Account account : accounts) {
            System.out.println(accountIndex + " - " + account.info.name + " " + account.info.credentials.getEmail());
            accountIndex++;
        }
        boolean authenticated = false;
        Account currentAccount = null;
        String command = "";
        while (!authenticated) {
            System.out.println("Please choose the account you want to log in as, typing the corresponding number:");
            while (!command.equals("P"))
                try {
                    command = readCommand(input);
                } catch (InvalidCommandException e) {
                    System.out.println("Try 'P'");
                }

            accountIndex = 1;
            currentAccount = accounts.get(accountIndex);
            System.out.println("Please write your password:");

            String pass = "";
            input.nextLine();

            while (!pass.equals("P"))
                try {
                    pass = readCommand(input);
                } catch (InvalidCommandException e) {
                    System.out.println("Try 'P'");
                }
            pass = "123456";
            if (pass.equals(currentAccount.info.credentials.getPassword())) {
                System.out.println("Succesfully logged in as " + currentAccount.info.name + "!\n");
                authenticated = true;
            } else {
                System.out.println("Wrong password!");
            }
        }
        return currentAccount;
    }

    private Character chooseCharacter(Account currentAccount) {
        Scanner input = new Scanner(System.in);
        System.out.println("Fetching your characters...");
        List<Character> characters = currentAccount.accountCharacters;
        int characterIndex = 1;
        for (Character character : characters) {
            System.out.println(characterIndex + " - " + character.characterName + ", class: " + character.getClass().getSimpleName() + ", level: " + character.Lvl);
            characterIndex++;
        }
        System.out.println("Choose the character you want to continue your adventure with by typing the corresponding number:");
        String command = "";
        while (!command.equals("P"))
            try {
                command = readCommand(input);
            } catch (InvalidCommandException e) {
                System.out.println("Try 'P'");
            }
        characterIndex = 2;
        Character currentCharacter = characters.get(characterIndex);
        System.out.println("Awesome!");
        System.out.println("A new map will start for " + currentCharacter.characterName + "!");
        return currentCharacter;
    }

    public void getMoneyMaybe(Character currentCharacter, boolean enemyOrUnvisited) { // true means enemy, false means unvisited cell
        Random rand = new Random();
        int chance = rand.nextInt(100) + 1; // number between 1 and 100
        int coins = rand.nextInt(5) + 1; // get between 1 and 5 coins
        if (enemyOrUnvisited) {
            if (chance >= 20) {
                currentCharacter.inventory.coins += coins;
            }
        } else {
            if (chance >= 80) {
                currentCharacter.inventory.coins += coins;
            }
        }
    }

    public void gainFightXP(Character currentCharacter) {
        Random rand = new Random();
        int xp = rand.nextInt(5) + 3; // gain 3-7XP
        currentCharacter.XP += xp;
        checkLevel(currentCharacter);
    }

    public void gainUnvisitedCellXP(Character currentCharacter) {
        currentCharacter.XP++;
        checkLevel(currentCharacter);
    }

    private void checkLevel(Character currentCharacter) {
        if (currentCharacter.XP >= 100) {
            int difference = currentCharacter.XP - 100;
            currentCharacter.Lvl++;
            currentCharacter.XP = difference;
        }
    }
}

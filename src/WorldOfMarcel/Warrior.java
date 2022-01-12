package WorldOfMarcel;

import java.util.ArrayList;
import java.util.Random;

public class Warrior extends Character {
    private static final int DMG = 400;

    public Warrior(String name, int XP, int Lvl) {
        super(name, XP, Lvl);
        maxHealth = 3000;
        currentHealth = maxHealth;
        maxMana = 200;
        currentMana = maxMana;
        cha = Lvl / 2 + 1;
        str = Lvl + 1;
        dex = Lvl / 2 + 1;
        inventory = new Inventory(20);
        fire = true;
        ice = false;
        earth = false;
        spells = new ArrayList<>();
        spells.add(new Fire());
    }

    @Override
    void receiveDamage(int value) {
        Random rand = new Random();
        // start with a 25% chance of getting half damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += dex + cha - 1;
        value = halfChance >= 75 ? value / 2 : value;

        int newHealth = currentHealth - value;
        currentHealth = Math.max(newHealth, 0);
    }

    @Override
    int getDamage() {
        Random rand = new Random();
        // start with a 25% chance of dealing double damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += str - 2;
        System.out.println("You hit with a normal attack!");
        return halfChance >= 75 ? DMG * 2 : DMG;
    }
}

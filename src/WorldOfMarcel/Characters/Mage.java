package WorldOfMarcel.Characters;

import WorldOfMarcel.Spells.Ice;

import java.util.ArrayList;
import java.util.Random;

public class Mage extends Character {
    private static final int DMG = 200;

    public Mage(String name, int XP, int Lvl) {
        super(name, XP, Lvl);
        maxHealth = 1500;
        currentHealth = maxHealth;
        maxMana = 600;
        currentMana = maxMana;
        cha = Lvl + 1;
        str = Lvl / 2 + 1;
        dex = Lvl / 2 + 1;
        inventory = new Inventory(8);
        fire = false;
        ice = true;
        earth = false;
        spells = new ArrayList<>();
        spells.add(new Ice());
    }

    @Override
    public void receiveDamage(int value) {
        Random rand = new Random();
        // start with a 25% chance of getting half damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += str + dex - 1;
        value = halfChance >= 75 ? value / 2 : value;

        int newHealth = currentHealth - value;
        currentHealth = Math.max(newHealth, 0);
    }

    @Override
    public int getDamage() {
        Random rand = new Random();
        // start with a 25% chance of dealing double damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += cha - 2;
        System.out.println("You hit with a normal attack!");
        return halfChance >= 75 ? DMG * 2 : DMG;
    }
}

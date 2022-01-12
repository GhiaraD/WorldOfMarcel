package WorldOfMarcel.Characters;

import WorldOfMarcel.Spells.Earth;

import java.util.ArrayList;
import java.util.Random;

public class Rogue extends Character {
    private static final int DMG = 300;

    public Rogue(String name, int XP, int Lvl) {
        super(name, XP, Lvl);
        maxHealth = 2000;
        currentHealth = maxHealth;
        maxMana = 400;
        currentMana = maxMana;
        cha = Lvl / 2 + 1;
        str = Lvl / 2 + 1;
        dex = Lvl + 2;
        inventory = new Inventory(14);
        fire = false;
        ice = false;
        earth = true;
        spells = new ArrayList<>();
        spells.add(new Earth());
    }

    @Override
    public void receiveDamage(int value) {
        Random rand = new Random();
        // start with a 25% chance of getting half damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += str + cha - 1;
        value = halfChance >= 75 ? value / 2 : value;

        int newHealth = currentHealth - value;
        currentHealth = Math.max(newHealth, 0);
    }

    @Override
    public int getDamage() {
        Random rand = new Random();
        // start with a 25% chance of dealing double damage that grows each level
        int halfChance = rand.nextInt(100) + 1;
        halfChance += dex - 2;
        System.out.println("You hit with a normal attack!");
        return halfChance >= 75 ? DMG * 2 : DMG;
    }
}

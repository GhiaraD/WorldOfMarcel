package WorldOfMarcel.Map;

import WorldOfMarcel.Entity;
import WorldOfMarcel.Spells.SpellFactory;
import WorldOfMarcel.Spells.Visitor;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements CellElement {
    private static final int MAX_HEALTH = 2500;
    private static final int MIN_HEALTH = 1500;
    private static final int MAX_MANA = 500;
    private static final int MIN_MANA = 300;
    private static final int MAX_DMG = 150;
    private static final int MIN_DMG = 100;

    public Enemy() {
        Random rand = new Random();
        int randomNum = rand.nextInt((MAX_HEALTH - MIN_HEALTH) + 1) + MIN_HEALTH;
        maxHealth = randomNum;
        currentHealth = randomNum;

        randomNum = rand.nextInt((MAX_MANA - MIN_MANA) + 1) + MIN_MANA;
        maxMana = randomNum;
        currentMana = randomNum;

        randomNum = rand.nextInt(2);
        fire = randomNum == 1;
        randomNum = rand.nextInt(2);
        ice = randomNum == 1;
        randomNum = rand.nextInt(2);
        earth = randomNum == 1;

        randomNum = rand.nextInt(3);
        String element;
        if (randomNum == 0)
            element = "Ice";
        else if (randomNum == 1)
            element = "Fire";
        else
            element = "Earth";
        // add 3 spells
        spells = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            spells.add(new SpellFactory().buildSpell(element));
        }
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    public void receiveDamage(int value) {
        // 50% chance to receive half damage
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        int newHealth;
        // take full damage
        if (randomNum == 0) {
            newHealth = currentHealth - value;
        }
        // take half damage
        else {
            newHealth = currentHealth - value / 2;
        }
        currentHealth = Math.max(newHealth, 0);
    }

    @Override
    public int getDamage() {
        Random rand = new Random();
        int randomNum = rand.nextInt((MAX_DMG - MIN_DMG) + 1) + MIN_DMG;
        int dmg = randomNum;

        randomNum = rand.nextInt(2);
        System.out.println("You were hit by a normal attack!");
        // deal normal or double dmg
        return randomNum == 0 ? dmg : dmg * 2;
    }

    @Override
    public void accept(Visitor<Entity> spell) {
        System.out.println("You casted a " + spell.getClass().getSimpleName() + " spell!");
        spell.visit(this);
    }
}

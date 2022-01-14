package WorldOfMarcel;

import WorldOfMarcel.Characters.Character;
import WorldOfMarcel.Potions.Potion;
import WorldOfMarcel.Spells.*;

import java.util.List;
import java.util.Random;

public abstract class Entity implements Element<Entity> {
    public List<Spell> spells;
    public int currentHealth;
    public int maxHealth;
    public int currentMana;
    public int maxMana;
    public boolean fire;
    public boolean ice;
    public boolean earth;

    public void regenHealth(int value) {
        currentHealth = Math.min(maxHealth, currentHealth + value);
    }

    public void regenMana(int value) {
        currentMana = Math.min(maxMana, currentMana + value);
    }

    public boolean useSpell(Spell spell, Entity enemy) {
        if (currentMana < spell.mana) {
            if (this instanceof Character)
                System.out.println("Not enough mana!");
            return false;
        }
        currentMana -= spell.mana;
        if ((spell instanceof Ice && enemy.ice) || (spell instanceof Earth && enemy.earth) || (spell instanceof Fire && enemy.fire)) {
            if (this instanceof Character)
                System.out.println("The enemy is immune to this kind of spell!");
            return true;
        }

        enemy.accept(spell);
        return true;
    }

    public void makeAutomatedAttack(Entity target) {
        List<Spell> spells = this.spells;
        boolean usedSpell;
        boolean usedPotion = false;
        Random random = new Random();

        int number = random.nextInt(spells.size());
        usedSpell = this.useSpell(spells.get(number), target);

        if (!usedSpell) {
            if (this instanceof Character) {
                List<Potion> potions = ((Character) this).inventory.potions;
                if (!potions.isEmpty()) {
                    potions.get(0).usePotion(this);
                    ((Character) this).inventory.potions.remove(0);
                    usedPotion = true;
                }
            }
            if (!usedPotion) {
                target.receiveDamage(this.getDamage());
            }
        }
    }

    public abstract void receiveDamage(int value);

    public abstract int getDamage();
}

package WorldOfMarcel;

import java.util.List;
import java.util.Random;

public abstract class Entity implements Element<Entity> {
    List<Spell> spells;
    int currentHealth;
    int maxHealth;
    int currentMana;
    int maxMana;
    boolean fire;
    boolean ice;
    boolean earth;

    void regenHealth(int value) {
        currentHealth = Math.min(maxHealth, currentHealth + value);
    }

    void regenMana(int value) {
        currentMana = Math.min(maxMana, currentMana + value);
    }

    boolean useSpell(Spell spell, Entity enemy) {
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

    void makeAutomatedAttack(Entity target) {
        List<Spell> spells = this.spells;
        boolean usedSpell = false;
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

    abstract void receiveDamage(int value);

    abstract int getDamage();
}

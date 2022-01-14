package WorldOfMarcel.Characters;

import WorldOfMarcel.Entity;
import WorldOfMarcel.Potions.Potion;
import WorldOfMarcel.Spells.Visitor;

public abstract class Character extends Entity {
    public String characterName;
    public int ox;
    public int oy;
    public Inventory inventory;
    public int XP;
    public int Lvl;
    public int str;
    public int cha;
    public int dex;

    public Character(String name, int XP, int Lvl) {
        characterName = name;
        this.XP = XP;
        this.Lvl = Lvl;
    }

    void buyPotion(Potion potion) {
        if (inventory.getRemainingWeight() < potion.getWeight()) {
            System.out.println("You don't have enough inventory space!");
            return;
        }
        if (inventory.coins < potion.getPrice()) {
            System.out.println("You don't have enough coins!");
            return;
        }
        inventory.addPotion(potion);
        inventory.coins -= potion.getPrice();
    }

    @Override
    public void accept(Visitor<Entity> spell) {
        System.out.println("You have been hit by a " + spell.getClass().getSimpleName() + " spell!");
        spell.visit(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", name: " + characterName + ", level: " + Lvl;
    }
}

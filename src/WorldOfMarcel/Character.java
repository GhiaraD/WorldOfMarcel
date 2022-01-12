package WorldOfMarcel;

public abstract class Character extends Entity {
    String characterName;
    int ox;
    int oy;
    Inventory inventory;
    int XP;
    int Lvl;
    int str;
    int cha;
    int dex;

    public Character(String name, int XP, int Lvl) {
        characterName = name;
        this.XP = XP;
        this.Lvl = Lvl;
    }

    void buyPotion(Potion potion) {
        if (inventory.getRemainingWeight() < potion.getWeight()) {
            System.out.println("nu ai suficient spatiu in inverntar");
            return;
        }
        if (inventory.coins < potion.getPrice()) {
            System.out.println("nu ai suficienti bani");
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
}

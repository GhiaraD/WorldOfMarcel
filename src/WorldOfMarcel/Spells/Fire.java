package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;

public class Fire extends Spell {
    public Fire() {
        damage = 300;
        mana = 100;
    }

    @Override
    public void visit(Entity entity) {
        if (!entity.fire)
            entity.receiveDamage(damage);
    }

    @Override
    public String toString() {
        return "Fire Spell -> " +
                "damage = " + damage +
                ",  mana = " + mana;
    }
}

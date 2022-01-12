package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;

public class Ice extends Spell {
    public Ice() {
        damage = 500;
        mana = 300;
    }

    @Override
    public void visit(Entity entity) {
        if (!entity.ice)
            entity.receiveDamage(damage);
    }

    @Override
    public String toString() {
        return "Ice Spell -> " +
                "damage = " + damage +
                ",  mana = " + mana;
    }
}

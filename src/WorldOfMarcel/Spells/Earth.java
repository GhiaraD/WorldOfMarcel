package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;

public class Earth extends Spell {
    public Earth() {
        damage = 400;
        mana = 200;
    }

    @Override
    public void visit(Entity entity) {
        if (!entity.earth)
            entity.receiveDamage(damage);
    }

    @Override
    public String toString() {
        return "Earth Spell -> " +
                "damage = " + damage +
                ",  mana = " + mana;
    }
}

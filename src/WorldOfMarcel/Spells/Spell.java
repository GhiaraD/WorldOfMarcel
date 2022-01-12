package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;

public abstract class Spell implements Visitor<Entity> {
    public int damage;
    public int mana;
}

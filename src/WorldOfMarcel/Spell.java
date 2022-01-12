package WorldOfMarcel;

public abstract class Spell implements Visitor<Entity> {
    int damage;
    int mana;
}

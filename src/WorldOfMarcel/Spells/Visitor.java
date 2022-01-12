package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;

public interface Visitor<T extends Entity> {
    void visit(Entity entity);
}

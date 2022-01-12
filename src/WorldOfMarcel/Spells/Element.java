package WorldOfMarcel.Spells;

import WorldOfMarcel.Entity;
import WorldOfMarcel.Spells.Visitor;

public interface Element<T extends Entity> {
    void accept(Visitor<T> visitor);
}

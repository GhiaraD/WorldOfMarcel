package WorldOfMarcel.Potions;

import WorldOfMarcel.Entity;

public interface Potion {
    void usePotion(Entity entity);

    int getPrice();

    int getRegen();

    int getWeight();

    String toString();
}

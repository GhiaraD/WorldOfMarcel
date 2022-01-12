package WorldOfMarcel.Characters;

import WorldOfMarcel.Potions.Potion;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public List<Potion> potions;
    public int maxWeight;
    public int coins; // 20% chance to find coin at a new cell
    // 80% chance to find coins when defeating an enemy

    public Inventory(int maxWeight){
        potions = new ArrayList<>();
        coins = 20;
        this.maxWeight = maxWeight;
    }

    void addPotion(Potion potion) {
        potions.add(potion);
    }

    void removePotion(Potion potion) {
        potions.remove(potion);
    }

    int getRemainingWeight() {
        int currentWeight = 0;
        for (Potion potion : potions) {
            currentWeight += potion.getWeight();
        }
        return maxWeight - currentWeight;
    }
}
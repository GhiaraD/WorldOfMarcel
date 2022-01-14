package WorldOfMarcel.Map;

import WorldOfMarcel.Potions.HealthPotion;
import WorldOfMarcel.Potions.ManaPotion;
import WorldOfMarcel.Potions.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement {
    public List<Potion> potions;

    public Shop(boolean test) {
        potions = new ArrayList<>();
        // generate random shop
        if (!test) {
            Random rand = new Random();
            int number = rand.nextInt(3) + 2; // 2-4 random potions
            for (int i = 0; i < number; ++i) {
                int type = rand.nextInt(2);
                if (type == 0) {
                    potions.add(new HealthPotion());
                } else {
                    potions.add(new ManaPotion());
                }
            }
        }
        // generate test shop
        else {
            potions.add(new HealthPotion());
            potions.add(new ManaPotion());
        }
    }

    public Potion selectPotion(int index) {
        if (index >= potions.size())
            return null;
        Potion potion = potions.get(index);
        potions.remove(index);
        return potion;
    }

    @Override
    public char toCharacter() {
        return 'S';
    }
}

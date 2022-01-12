package WorldOfMarcel;

import java.util.Objects;
import java.util.Random;

public class ManaPotion implements Potion {
    private final int price;
    private final int weight;
    private final int regen;

    public ManaPotion() {
        Random rand = new Random();
        price = rand.nextInt(5) + 6; // a potion will cost between 6 and 10 coins
        weight = 2;
        regen = price * 30; // regen between 180 and 300 mana
    }

    @Override
    public void usePotion(Entity entity) {
        System.out.println("You used a Mana Potion!");
        entity.regenMana(regen);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getRegen() {
        return regen;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManaPotion that = (ManaPotion) o;
        return price == that.price && weight == that.weight && regen == that.regen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, weight, regen);
    }

    @Override
    public String toString() {
        return "Mana Potion -> " +
                "price = " + price +
                ",  weight = " + weight +
                ",  regen = " + regen;
    }
}

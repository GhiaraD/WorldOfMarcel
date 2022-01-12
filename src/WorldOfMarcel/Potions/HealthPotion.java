package WorldOfMarcel.Potions;

import WorldOfMarcel.Entity;

import java.util.Objects;
import java.util.Random;

public class HealthPotion implements Potion {
    private final int price;
    private final int weight;
    private final int regen;

    public HealthPotion() {
        Random rand = new Random();
        price = rand.nextInt(5) + 5; // a potion will cost between 5 and 9 coins
        weight = 2;
        regen = price * 100; // regen between 500 and 900 health
    }

    @Override
    public void usePotion(Entity entity) {
        System.out.println("You used a Health Potion!");
        entity.regenHealth(regen);
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
        HealthPotion that = (HealthPotion) o;
        return price == that.price && weight == that.weight && regen == that.regen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, weight, regen);
    }

    @Override
    public String toString() {
        return "Health Potion -> " +
                "price = " + price +
                ",  weight = " + weight +
                ",  regen = " + regen;
    }
}

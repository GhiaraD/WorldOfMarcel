package WorldOfMarcel;

public class CharacterFactory {

    public Character buildCharacter(String profession, String name, int XP, int Lvl) {
        if (profession == null) {
            return null;
        }
        if (profession.equalsIgnoreCase("Warrior")) {
            return new Warrior(name, XP, Lvl);

        } else if (profession.equalsIgnoreCase("Mage")) {
            return new Mage(name, XP, Lvl);

        } else if (profession.equalsIgnoreCase("Rogue")) {
            return new Rogue(name, XP, Lvl);
        }

        return null;
    }

}

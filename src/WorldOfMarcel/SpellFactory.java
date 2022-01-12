package WorldOfMarcel;

public class SpellFactory {

    public Spell buildSpell(String element) {
        if (element == null) {
            return null;
        }
        if (element.equalsIgnoreCase("Ice")) {
            return new Ice();

        } else if (element.equalsIgnoreCase("Earth")) {
            return new Earth();

        } else if (element.equalsIgnoreCase("Fire")) {
            return new Fire();
        }

        return null;
    }
}

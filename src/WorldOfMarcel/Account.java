package WorldOfMarcel;

import WorldOfMarcel.Characters.Character;
import WorldOfMarcel.Exceptions.InformationIncompleteException;

import java.util.List;
import java.util.TreeSet;

public class Account {
    public Information info;
    public List<Character> accountCharacters;
    public int nrPlayedGames;

    public Account(Information info, List<Character> accountCharacters, int nrPlayedGames) {
        this.info = info;
        this.accountCharacters = accountCharacters;
        this.nrPlayedGames = nrPlayedGames;
    }

    public static class Information {
        public Credentials credentials;
        public TreeSet<String> favoriteGames;
        public String name;
        public String country;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        static class InformationBuilder {
            Credentials credentials;
            TreeSet<String> favoriteGames;
            String name;
            String country;

            public InformationBuilder(Credentials credentials, String name) throws InformationIncompleteException {
                if (credentials == null || name.isEmpty())
                    throw new InformationIncompleteException();
                this.name = name;
                this.credentials = credentials;
            }

            public InformationBuilder setFavoriteGames(TreeSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public InformationBuilder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }

    @Override
    public String toString() {
        return info.name + "  " + info.credentials.getEmail();
    }
}

package WorldOfMarcel;

import java.util.List;
import java.util.TreeSet;

public class Account {
    Information info;
    List<Character> accountCharacters;
    int nrPlayedGames;

    public Account(Information info, List<Character> accountCharacters, int nrPlayedGames) {
        this.info = info;
        this.accountCharacters = accountCharacters;
        this.nrPlayedGames = nrPlayedGames;
    }

    static class Information {
        Credentials credentials;
        TreeSet<String> favoriteGames;
        String name;
        String country;

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
}

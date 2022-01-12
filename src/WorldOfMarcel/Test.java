package WorldOfMarcel;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Game game = Game.getInstance();
        try{
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

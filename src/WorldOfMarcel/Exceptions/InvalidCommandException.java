package WorldOfMarcel.Exceptions;

public class InvalidCommandException extends Exception{

    public InvalidCommandException(){
        super("This is not an accepted command! :(");
    }

}

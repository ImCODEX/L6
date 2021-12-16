package customexceptions;

/**
 * CustomException Class for handling
 * exceptions related to the logic of
 * the program
 */
public class CustomExceptions extends Exception{
    public CustomExceptions(String errorMessage){
        super(errorMessage);
    }
}

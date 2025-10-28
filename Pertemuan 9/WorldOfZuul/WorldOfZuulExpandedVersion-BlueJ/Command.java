
/**
 * Write a description of class Command here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Command
{
    private String commandWord;
    private String secondWord;
    public Command(String first, String second) {
        commandWord = first;
        secondWord  = second;
    }
    public String getCommandWord() { return commandWord; }
    public String getSecondWord()  { return secondWord; }
    public boolean isUnknown()     { return commandWord == null; }
    public boolean hasSecondWord() { return secondWord != null; }
}

class CommandWords {
    private static final String[] valid = {
        "go","quit","help","take","use","talk","inventory"
    };
    public boolean isCommand(String s) {
        return Arrays.asList(valid).contains(s);
    }
    public void showAll() {
        System.out.println("Your commands are:");
        for (String c : valid) System.out.print(c + " ");
        System.out.println();
    }
}

/**
 * Write a description of class Parser here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class Parser
{
    private CommandWords cmds = new CommandWords();
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public Command getCommand() {
        String line = "";
        try { System.out.print("> "); line = in.readLine(); }
        catch (Exception e) { System.out.println("Input error."); }
        StringTokenizer tk = new StringTokenizer(line);
        String w1 = null, w2 = null;
        if (tk.hasMoreTokens()) w1 = tk.nextToken();
        if (tk.hasMoreTokens()) w2 = tk.nextToken();
        if (w1 != null && cmds.isCommand(w1)) return new Command(w1, w2);
        return new Command(null, w2);
    }
}
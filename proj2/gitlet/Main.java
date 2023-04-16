package gitlet;

import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                if(inputChecker(1,args)){
                    Repository.init();
                }
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if(inputChecker(2,args))
                {
                    Repository.add(Utils.join(Repository.CWD,args[1]));
                }
                break;
            // TODO: FILL THE REST IN
        }
    }
    static boolean inputChecker(int length, String... args) {
        if (args.length == length) {
            return true;
        }
        System.out.println("Incorrect Operands");
        return false;
    }
}

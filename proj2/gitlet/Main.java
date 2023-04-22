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
        if(args.length==0)
        {
              Utils.error("please enter a command.");
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                handleInit(args);
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                handleAdd(args);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                handleCommit(args);
        }
    }
    private static void handleInit(String[] args) throws IOException {
        inputChecker(1,args);
        Repository.init();
    }
    private static  void handleAdd(String[] args) throws IOException {
        inputChecker(2,args);
        ensureInitialized();
        Repository.add(Utils.join(Repository.CWD,args[1]));
    }
    private static void handleCommit(String[] args) throws IOException {
        inputChecker(2,args);
        Repository.Commit(args[1]);
    }
    private static boolean inputChecker(int length, String... args) {
        if (args.length == length) {
            return true;
        }
        Utils.error("incorrect Operands");
        return false;
    }
    private static void ensureInitialized()
    {
        if(!Repository.GITLET_DIR.exists()||!Blob.BLOB_DIR.exists()||!Commit.COMMIT_DIR.exists()||!AddStage.STAGE_DIR.exists()||RemoveStage.STAGE_DIR.exists())
        {
            Utils.error("Not in an initialized gitlet directory.");
        }
    }

}

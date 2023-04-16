package gitlet;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.join;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static  String HEAD;
    public static HashMap<String,String> branchHash;
    private static void makeDir(){
        GITLET_DIR.mkdir();
        Commit.makeCommitDir();
        Branch.makedir();
        Blob.makedir();
        AddStage.STAGE_DIR.mkdir();
        RemoveStage.STAGE_DIR.mkdir();
    }
    /* TODO: fill in the rest of this class. */
    public static void init() throws IOException {
        if(GITLET_DIR.exists())
        {
            throw new RuntimeException("A Gitlet version-control system already exists in the current directory.");
        }
        makeDir();
        Commit initCommit=new Commit();
        initCommit.createCommitFile();
        HEAD= initCommit.getID();
        Branch branch=new Branch("master",HEAD,true);
        branchHash.put("master",initCommit.getID());
    }
    public static void add(List<File> files) throws IOException {
        for(File file:files)
        {
            if(!file.exists())
            {
                throw new RuntimeException("File does not exist");
            }
            Blob blob=new Blob(file);
            if(AddStage.isContain(blob.getRefs())){
                continue;
            }
            AddStage.addblob(blob.getRefs());
        }
    }
    private static Commit generateCommit(String message) throws IOException {
        HashSet<String> blobIDs=new HashSet<>();
        blobIDs.addAll(Helper.getCommitByID(HEAD).getBlobCodes());
        blobIDs.addAll(AddStage.getFile_IDMap().keySet());
        AddStage.clear();
        HashSet<String> parentID=new HashSet<>();
        parentID.add(HEAD);
        Commit commit=new Commit(message,parentID,blobIDs);
        commit.createCommitFile();
        branchHash.put(HEAD,commit.getID());
        HEAD=commit.getID();

    }
    public static void  rm(List<File> files)
    {
        for(File file:files)
        {
            if(!file.exists())
            {
                throw new RuntimeException("File does not exist");
            }
            Blob blob=new Blob(file);
            if(AddStage.isContain(blob.getRefs())){
                continue;
            }
            RemoveStage.addblob(blob.getRefs());
        }

    }
    public static void find(String message){}

    public static void log(){}
    public static void global_log(){}

    public static void status()
    {
    }
    public static void checkout()
    {}
    public static  void reset(){}
    public static void merge(){}
}

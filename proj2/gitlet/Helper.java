package gitlet;

import java.io.File;
import java.util.*;
import static gitlet.Utils.*;

public class Helper {
    //Blob
    public static void cutAddBlobsToBlobDir()
    {
        File[] files=AddStage.STAGE_DIR.listFiles();
        for(File file:files)
        {
            file.renameTo(Blob.BLOB_DIR);
        }
    }





    //Commit
    public static Commit getCommitByID(String ID)
    {
        File file=join(Commit.COMMIT_DIR,ID);
        if(!file.exists())
        {
            return null;
        }
        return readObject(file, Commit.class);
    }
    public static LinkedList<Commit> getAllCommit()
    {
        File[] files=Commit.COMMIT_DIR.listFiles();
        LinkedList<Commit> commits=new LinkedList<>();
        for(File file:files)
        {
            commits.add(readObject(file,Commit.class));
        }
        return  commits;
    }



    //branch
    public List<String> findBranchByCommit(String commitHash, HashMap<String, String> branchMap) {
        List<String> branchHash=new ArrayList<>();
        for (Map.Entry<String, String> entry : branchMap.entrySet()) {
            if (entry.getValue().equals(commitHash)) {
                branchHash.add(entry.getKey());
            }
        }
        return branchHash;
    }


}

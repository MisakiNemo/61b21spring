package gitlet;

import java.io.File;
import java.util.*;
import static gitlet.Utils.*;

public class Helper {
    //Blob





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

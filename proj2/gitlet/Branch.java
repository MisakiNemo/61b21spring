package gitlet;

import java.io.File;
import static gitlet.Utils.*;
public class Branch {
         private String name;
         private String latestCommitHash;
         private boolean active;
         public static final File BRANCH_DIR=join(Repository.GITLET_DIR,"Branch");
         public static void makedir()
         {
             BRANCH_DIR.mkdir();
         }
    public Branch(String name, String latestCommitHash, boolean active) {
        this.name = name;
        this.latestCommitHash = latestCommitHash;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public String getLatestCommitHash() {
        return latestCommitHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatestCommitHash(String latestCommitHash) {
        this.latestCommitHash = latestCommitHash;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

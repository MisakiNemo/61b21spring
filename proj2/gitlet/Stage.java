package gitlet;

import java.io.File;
import java.util.List;
import static gitlet.Utils.*;
public class Stage {
    List<Blob> blobs;
    public static  final File Staging=join(Repository.GITLET_DIR,"stage");

}

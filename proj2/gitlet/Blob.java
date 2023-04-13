package gitlet;
import java.io.File;
import static gitlet.Utils.*;

public class Blob {
    public static final  File BLOBDIR=join(Repository.GITLET_DIR,"Blob");
    public static  void makeBlobDir()
    {
        BLOBDIR.mkdir();
    }
}

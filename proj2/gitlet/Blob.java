package gitlet;
import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Blob {
    public static final  File BLOBDIR=join(Repository.GITLET_DIR,"Blob");
    private  String refs;
    private byte[] content;
    private String ID;
    public static  void makeBlobDir()
    {
        BLOBDIR.mkdir();
    }
    public Blob(File file)
    {
        refs=file.getPath();
        content=readContents(file);
        ID=gennerateID();
    }

    public void makeBlobFile(File dir) throws IOException {
        File file=join(dir,ID);
        if(file.exists()){
            return;
        }
        writeContents(file,content);
        file.createNewFile();

    }
    private  String gennerateID()
    {
        return sha1(refs,content);
    }
}

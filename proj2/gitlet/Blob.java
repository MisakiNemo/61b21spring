package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Blob implements Serializable {
    private  String refs;
    private byte[] content;
    private String ID;
    public static final File BLOB_DIR=join(Repository.GITLET_DIR,"Blob");
    public  static void makedir()
    {
        BLOB_DIR.mkdir();
    }

    public String getRefs() {
        return refs;
    }

    public byte[] getContent() {
        return content;
    }

    public String getID() {
        return ID;
    }

    public Blob(File file)
    {
        refs=generaterefs(file);
        content=readContents(file);
        ID=gennerateID();
    }

    private  String gennerateID()
    {
        return sha1(refs,content);
    }
    private String generaterefs(File file)
    {
        return Repository.CWD.toPath().relativize(file.toPath()).toString();
    }
    public void  createBlobFile()
    {

    }
}

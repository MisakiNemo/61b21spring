package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static gitlet.Utils.join;

public class AddStage {
    public static HashMap<String,String> file_IDMap  = new HashMap<>();// 存储要添加的 Blob 对象的链表
    public static final File STAGE_DIR = join(Repository.GITLET_DIR, "AddStage"); // 存储要添加的 Blob 对象的文件夹
    public static void addblob(String refs) throws IOException {
        File file=join(Repository.CWD,refs);
        Blob blob=new Blob(file);
        createBlobFile(blob);
        file_IDMap.put(blob.getID(),file.getName());
    }
    public static  Boolean isContain(Blob blob)
    {
        return file_IDMap.containsKey(blob.getID());
    }
    public static  Boolean isContain(String refs )
    {
            File file=join(Repository.CWD,refs);
            return isContain(new Blob(file));
    }
    public static void removeFile(String refs){
        if(!isContain(refs))
        {
            return;
        }
        File file=join(Repository.CWD,refs);
        Blob blob=new Blob(file);
        File blobFile=join(STAGE_DIR,blob.getID());
        if(blobFile.exists())
        {
            blobFile.delete();
        }
        if(file_IDMap.containsKey(blob.getID()))
        {

            file_IDMap.remove(blob.getID());
        }
        return ;


}
    public static  void clear()
    {
        File[] files=STAGE_DIR.listFiles();
        for(File file:files)
        {
            file.delete();
        }
        file_IDMap=new HashMap<>();
    }
    public static void createBlobFile(Blob blob) throws IOException {
        File file=join(STAGE_DIR,blob.getID());
        file.createNewFile();
              return ;
    }
    public static  void removeBlobFile(Blob blob)
    {
        String blobID=blob.getID();
        if(file_IDMap.containsKey(blobID))
        {
            file_IDMap.remove(blobID);
        }
        File file=join(STAGE_DIR,blobID);
        if(file.exists())
        {
            file.delete();
        }

    }
}

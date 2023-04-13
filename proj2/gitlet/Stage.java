package gitlet;

import java.io.File;
import java.util.LinkedList;

import static gitlet.Utils.join;
public class Stage {
    public  static LinkedList<Blob> blobs=new LinkedList<>();
    public static  final File STAGE_DIR=join(Repository.GITLET_DIR,"Stage");
    public static void  makeStagingDir(){
             STAGE_DIR.mkdir();
    }
    public static void  addblob(Blob blob){
        blobs.add(blob);
    }
    public static void clear()
    {
         for(Blob blob:blobs)
         {

         }
        LinkedList<Blob> newblobs=new LinkedList<>();
        blobs=newblobs;
    }

}

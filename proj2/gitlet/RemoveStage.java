package gitlet;

import java.io.File;
import java.util.HashSet;

import static gitlet.Utils.join;

public class RemoveStage {

    public static HashSet<String> blobs = new HashSet<>();// 存储要添加的 Blob 对象的链表
    public static final File STAGE_DIR = join(Repository.GITLET_DIR, "AddStage"); // 存储要添加的 Blob 对象的文件夹

}

package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.join;
import static gitlet.Utils.readObject;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static  String HEAD;
    public static Map<String,String> branchHash=new HashMap<>();
    public static  HashSet<Branch> branch=new HashSet<>();
    /* TODO: fill in the rest of this class. */
    public static void init() throws IOException {
        if(GITLET_DIR.exists())
        {
            throw new RuntimeException("A Gitlet version-control system already exists in the current directory.");
        }
        makeDir();
        Commit initCommit=new Commit();
        HEAD= initCommit.getID();
        branch.add(new Branch("master",initCommit.getID(),true));
        branchHash.put(initCommit.getID(),"master");
    }
    private static void makeDir(){
        GITLET_DIR.mkdir();
        Commit.makeCommitDir();
        Branch.makedir();
        Blob.makedir();
        AddStage.STAGE_DIR.mkdir();
        RemoveStage.STAGE_DIR.mkdir();
    }
    public static void add(File  file) throws IOException {
            if(!file.exists())
            {
                throw new RuntimeException("File does not exist");
            }
            HashSet<String> strings=Helper.getCommitByID(HEAD).getBlobCodes();
            Blob blob=new Blob(file);
            if(strings.contains(blob.getID()))
            {
                return;
            }
            AddStage.addblob(blob.getRefs());
    }

    public static void Commit(String message) throws IOException {
        HashSet<String> CommitBlobIDs=getCommitBlobIDs();
        Commit commit=new Commit(message,HEAD,CommitBlobIDs);
        commit.createCommitFile();
        branchHash.put(commit.getID(),branchHash.get(HEAD));
        for(Branch brancht:branch) {
            if (brancht.getLatestCommitHash() == HEAD&&brancht.isActive()) {
                brancht.setLatestCommitHash(commit.getID());
            }
        }
        HEAD=commit.getID();
    }
    private  static HashSet<String> getCommitBlobIDs()
    {
        HashSet<String> CommitBlobIDs=new HashSet<>();
        CommitBlobIDs.addAll(Helper.getCommitByID(HEAD).getBlobCodes());
        CommitBlobIDs.addAll(AddStage.getFile_IDMap().keySet());
        Helper.cutAddBlobsToBlobDir();
        CommitBlobIDs.removeAll(RemoveStage.getFile_IDMap().keySet());
        clearStage();
        return CommitBlobIDs;
    }
    private static void clearStage()
    {
        AddStage.clear();
        RemoveStage.clear();
    }
    public static void  rm(File file) throws IOException {
        Blob blob=new Blob(file);
        rmInCWD(file);
        rmInAddStage(blob);
        rmInRemoveStage(blob);
        return;
    }
    public static void rmInCWD(File file)
    {
        if(file.exists())
        {
            file.delete();
        }
    }
    public static void rmInAddStage(Blob blob)
    {
        if(AddStage.isContain(blob))
        {
            AddStage.removeBlobFile(blob);
        }
    }
    public static void rmInRemoveStage(Blob blob)
    {

        if(RemoveStage.isContain(blob))
        {
            RemoveStage.removeBlobFile(blob);
        }
    }
    public static void find(String message){
        LinkedList<Commit> commits=Helper.getAllCommit();
        for(Commit commit:commits)
        {
            if(commit.getMessage().contains(message))
            {
                System.out.println(commit.getID());
            }
        }
       return;
    }


    public static void log(){
        Commit curCcommit=Helper.getCommitByID(HEAD);
        while(!curCcommit.getParentHashCodes().isEmpty())
        {
            System.out.println("===");
            System.out.println("commit "+curCcommit.getID());
            String parentIDs="";
            for(String parentID:curCcommit.getParentHashCodes())
            {
                parentIDs+=" "+parentID;
            }
            System.out.println("Merge:"+parentIDs);
            System.out.println("Date: "+curCcommit.getTimeStamp());
            if(curCcommit.getParentHashCodes().size()>1)
            {
                System.out.println("Merged development into "+branchHash.get(HEAD)+".");
            }
            curCcommit=Helper.getCommitByID(Helper.getHashSetFirstItem(curCcommit.getParentHashCodes()));
        }
    }
    public static void global_log(){
        File[] files=Commit.COMMIT_DIR.listFiles();
        for(File file:files)
        {
           Commit commit=readObject(file,Commit.class);
            System.out.println("===");
            System.out.println("commit "+commit.getID());
            String parentIDs="";
            for(String parentID:commit.getParentHashCodes())
            {
                parentIDs+=" "+parentID;
            }
            System.out.println("Merge:"+parentIDs);
            System.out.println("Date: "+commit.getTimeStamp());
            if(commit.getParentHashCodes().size()>1)
            {
                System.out.println("Merged development into "+branchHash.get(HEAD)+".");
            }
        }
    }


    public static void status()
    {
        System.out.println("=== Branches ===");
        for(Branch branch1:branch)
        {
            if(branch1.isActive())
            {
                System.out.println("*"+branch1.getName());
            }
            else{
                System.out.println(branch1);
            }
        }
        System.out.println("=== Staged Files ===");
        for(String filename:AddStage.file_IDMap.values()){
            System.out.println(filename);
        }
        System.out.println("=== Removed Files ===");
        for(String filename:RemoveStage.file_IDMap.values()){
            System.out.println(filename);
        }
    }
    public static void branch(String name)
    {
        Branch newBranch=new Branch(name,Helper.getCommitByID(HEAD).getID(),false);
        branch.add(newBranch);
        return ;
    }
    public static void rm_branch(String name)
    {
         for(Branch branch1:branch)
         {
             if(branch1.getName()==name)
             {
                 branch.remove(branch1);
                 return;
             }
         }
    }
    public static void checkout(String name)
    {


    }
    public static  void reset(){
    }
    public static void merge(){

    }
}

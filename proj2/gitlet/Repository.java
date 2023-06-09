package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import static gitlet.Utils.*;

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
              Commit commit=Helper.getCommitByID(HEAD);
              while(!commit.getParentHashCodes().equals(""))
              {
                  printCommit(commit);
                  Helper.getCommitByID(commit.getParentHashCodes());
              }
              printCommit(commit);
    }
    public static void printCommit(Commit commit){
            System.out.println("===");
            System.out.println("commit "+commit.getID());
            System.out.println("Date: "+commit.getTimeStamp());
            System.out.println(commit.getMessage());
            System.out.println("");
    }
    public static void global_log(){
        File[] files=Commit.COMMIT_DIR.listFiles();
        for(File file:files)
        {
            Commit commit=readObject(file,Commit.class);
            printCommit(commit);
        }
    }
    public static void status()
    {
        printBranch();
        printStagedFile();
        printRemoveStagedFile();
    }
    private static void printBranch()
    {
        System.out.println("=== Branches ===");
        for(Branch curbranch:branch)
        {
            if(curbranch.isActive())
            {
                System.out.println("*"+curbranch.getName());
            }
            else{
                System.out.println(curbranch.getName());
            }
        }
        System.out.println("");
    }
    private  static  void printStagedFile()
    {
        System.out.println("=== Staged Files ===");
        for(String filename:AddStage.getFile_IDMap().values())
        {
            System.out.println(filename);
        }
        System.out.println("");
    }
    private static  void printRemoveStagedFile()
    {
        System.out.println("=== Removed Files ===");
        for(String filename:RemoveStage.getFile_IDMap().values())
        {
            System.out.println(filename);
        }
        System.out.println("");
    }
    /*private static void printModificationsFiles()
    {

    }
    private static void printUntrackedFiles()
    {

    }*/
    public static void branch(String name)
    {
        if(checkBranch(name))
        {
            throw new RuntimeException("A branch with that name already exists.");
        }
        Branch newBranch=new Branch(name,Helper.getCommitByID(HEAD).getID(),false);
        branch.add(newBranch);
        return ;
    }
    private static Boolean checkBranch(String name)
    {
        for(Branch curbranch:branch)
        {
            if(curbranch.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    public static void rm_branch(String name)
    {
            for(Branch curbranch:branch)
            {
                if(curbranch.getName().equals(name))
                {
                    branch.remove(curbranch);
                    return;
                }
            }
            throw new RuntimeException("A branch with that name does not exist.");
    }
    public static void Firstcheckout(String name)
    {
        checkout(HEAD,name);
    }
    public static void checkout(String CommitID,String name)
    {

        Commit commit=Helper.getCommitByID(CommitID);
        commit.getBlobCodes();
        File[] files=Blob.BLOB_DIR.listFiles();
        for(File file:files)
        {
            String refs=readObject(file,Blob.class).getRefs();
            Blob blob=new Blob(join(CWD,name));
            if(refs.equals(blob.getRefs()))
            {
                writeContents(file,blob.getContent());
                return;
            }
        }
    }
    public static void  CheckoutBranch(String branchName)
    {
        if(branchName.equals(HEAD))
        {
            System.out.println("No need to checkout the current branch.");
        }
        for(Branch curbranch:branch)
        {
            if (curbranch.getName().equals(branchName)) {

                  }
              }
        System.out.println("No such branch exists.");
    }
    public static  void reset(){
    }
    public static void merge() {
    }

}

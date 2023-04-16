package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String ID;
    /* TODO: fill in the rest of this class. */
    private Date curTime;
    public static final File COMMIT_DIR=join(Repository.GITLET_DIR,"Commit");
    private HashSet<String> parentHashCodes;
    private HashSet<String> blobCodes;
    private String timeStamp;
    public static void makeCommitDir()
    {
        COMMIT_DIR.mkdir();
    }
    public  Commit(){
        message="init commit";
        curTime=new Date(0);
        parentHashCodes=new HashSet<>();
        blobCodes=new HashSet<>();
        ID=sha1(message,curTime,parentHashCodes,blobCodes);
        timeStamp=timeToStamp(curTime);
    }
    public Commit(String message,HashSet<String> parentHashCodes,HashSet<String> blobCodes)
    {
        this.curTime=new Date();
        this.message=message;
        this.parentHashCodes=parentHashCodes;
        this.blobCodes=blobCodes;
        this.ID=generateID();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getID() {
        return ID;
    }

    public HashSet<String> getParentHashCodes() {
        return parentHashCodes;
    }

    public HashSet<String> getBlobCodes() {
        return blobCodes;
    }

    public Date getCurTime() {
        return curTime;
    }


    public void  createCommitFile() throws IOException {
        File commit =join(COMMIT_DIR,getID());
        writeObject(commit,this);
        commit.createNewFile();
    }
    private String timeToStamp(Date date){
        DateFormat dateFormat =new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }
    private String generateID(){
        return sha1(message,timeToStamp(curTime),parentHashCodes,blobCodes);
    }


}

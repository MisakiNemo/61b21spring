package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    public static  final File COMMIT_DIR=join(Repository.GITLET_DIR,"Commit");
    private String message;
    private String HashCode;
    /* TODO: fill in the rest of this class. */
    private Date curTime;
    private List<String> parentHashCodes;
    private List<String> blobCodes;
    public static void makeCommitDir()
    {
        COMMIT_DIR.mkdir();
    }
    public  Commit(){
        message="init commit";
        curTime=new Date(0);
        parentHashCodes=new LinkedList<>();
        blobCodes=new LinkedList<>();
        HashCode=sha1(message,curTime,parentHashCodes,blobCodes);
    }
    public Commit(String message,List<String> parentHashCodes,List<String> blobCodes)
    {
        this.message=message;
        this.parentHashCodes=parentHashCodes;
        this.blobCodes=blobCodes;
        this.HashCode=generateID();
    }

    public String getMessage() {
        return message;
    }

    public String getHashCode() {
        return HashCode;
    }

    public Date getCurTime() {
        return curTime;
    }

    public List<String> getParentHashCodes() {
        return parentHashCodes;
    }

    public List<String> getBlobCodes() {
        return blobCodes;
    }
    public void  createCommitFile() throws IOException {
        File commit =join(COMMIT_DIR,getHashCode());
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

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Janek Taras on 4/7/2017.
 */
public class Folder {

    public ArrayList<Folder> getSubfolders() {
        return subfolders;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    private ArrayList<Folder> subfolders;
    private ArrayList<File> files;
    private String path;
    private int emb_index;

    public Folder(File rootFile)
    {
        emb_index =0;
        subfolders = new ArrayList<Folder>();
        files = new ArrayList<File>();
        path = rootFile.getName();
        lister(rootFile,this);

    }
    public Folder(File rootFile,Folder predecessor)
    {
        emb_index = predecessor.getEmb()+1;
        subfolders = new ArrayList<Folder>();
        files = new ArrayList<File>();
        path = predecessor.getPath() + File.separator + rootFile.getName();
        lister(rootFile,this);
    }


    private  void lister(File f, Folder folder) {
        File directory = f;

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                folder.addFile(file);
            } else if (file.isDirectory()) {
                Folder ef  = new Folder(file,folder);
                lister(file, ef);
                folder.addFolder(ef);
            }
        }
    }


    public void addFile(File f){files.add(f);}

    public void addFolder(Folder f){subfolders.add(f);}

    public void display()
    {
        for(int i=0;i<emb_index;i++)System.out.print(">");
        System.out.println(path);
        for (Folder f : subfolders)f.display();
        for (File f : files){
            for(int i=0;i<emb_index+1;i++)System.out.print(">");
            System.out.println(f.getName());
        }
    }

    public String getPath() {
        return path;
    }

    public int getEmb() {
        return emb_index;
    }
}

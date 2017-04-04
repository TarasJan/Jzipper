/**
 * Created by Janek Taras on 4/4/2017.
 */

import java.io.IOException;
import java.util.zip.*;
import java.io.*;

public class Unzipper {



    private static void parseEntry(ZipInputStream zin,ZipEntry entry,String destPath,int buffer) {
        System.out.println("Extracting: " + entry);

        String absPath = destPath + entry.getName();
        // checking if not writing a directory
        if (entry.isDirectory()) {
            File f = new File(absPath);
            if (!f.exists()) f.mkdirs();
            return;
        }
        int count;
        byte data[] = new byte[buffer];
        BufferedOutputStream stream = null;

        try {

            stream = new BufferedOutputStream(new FileOutputStream(absPath));
            while ((count = zin.read(data, 0, buffer)) != -1) {
                stream.write(data, 0, count);

                stream.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static boolean unzip(String filePath, String destPath) {

/** Finding the file setting up streams*/
        ZipInputStream zin=null;
        try

        {
            /** Opening Zip input stream*/
        zin = new ZipInputStream(new FileInputStream(filePath));
// Setting up entries and buffer size
        ZipEntry entry = null;
        int buffer =2048;
        // looping through all entries
        while((entry = zin.getNextEntry()) != null)
        {
           parseEntry(zin,entry,destPath,buffer);
        }
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }
    finally {
            if (zin != null)
            {
                try {
                    zin.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                return true;
            }
            else return false;
        }

}

}

/**
 * Created by Janek Taras on 4/4/2017.
 */

import java.io.IOException;
import java.util.zip.*;
import java.io.*;

public class Zipper {

    // buffer 2kb for reading writing zips
    private static final int buffer = 2048;

    private static void writeFile(ZipOutputStream zout,File file,String destPath)
    {
        System.out.println("Writing: " + file.getName());

        FileInputStream fis =null;
        try {
            fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zout.putNextEntry(zipEntry);

            byte data[] = new byte[buffer];

            int length;
            while ((length = fis.read(data)) >= 0) {
                zout.write(data, 0, length);
            }

        }
         catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    private static void parseEntry(ZipInputStream zin,ZipEntry entry,String destPath) {
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

     public static boolean zip(File[] files, String destFile)
     {
         /** Finding the file setting up streams*/
         ZipOutputStream zout=null;
         File f = new File(destFile);
         try {
             if(!f.exists())f.createNewFile();
         }
         catch(IOException e)
         {
             e.printStackTrace();
         }

         try

         {
             /** Opening Zip input stream*/
             zout = new ZipOutputStream(new FileOutputStream(f));

             // looping through all entries
             for(File ef : files)
             {
                 writeFile(zout,ef,destFile);
             }
         }
         catch(IOException e)
         {
             e.printStackTrace();
         }
         finally {
             if (zout != null)
             {
                 try {
                     zout.close();
                 }
                 catch (IOException e){
                     e.printStackTrace();
                 }
                 return true;
             }
             else return false;
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
        // looping through all entries
        while((entry = zin.getNextEntry()) != null)
        {
           parseEntry(zin,entry,destPath);
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

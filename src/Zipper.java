/**
 * Created by Janek Taras on 4/4/2017.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.*;
import java.io.*;

public class Zipper {

    // buffer 2kb for reading writing zips
    private static final int buffer = 2048;

private static void writeFolder(ZipOutputStream zout,Folder folder)throws  IOException
{
    System.out.println("Writing: " + folder.getPath());

    if(folder.getFiles().isEmpty()) {
        try {
            ZipEntry zipEntry = new ZipEntry(folder.getPath()+File.separator+"placeholder");
            zout.putNextEntry(zipEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    for (Folder fold : folder.getSubfolders())
    {
        writeFolder(zout,fold);
    }

    for (File file : folder.getFiles())
    {
        if(!file.isDirectory())writeFile(zout,file,folder.getPath());
    }

    System.out.println("Successfully written " + folder.getPath());

}


    private static void writeFile(ZipOutputStream zout,File file,String folderPath)
    {
        System.out.println("Writing: " + folderPath + File.separator + file.getName());

        FileInputStream fis =null;
        try {
            fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(folderPath + File.separator + file.getName());
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

    private static void writeFile(ZipOutputStream zout,File file)
    {
        System.out.println(file.getName());

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

        // Creepy solution for gzip format



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
             ArrayList<Folder> folders = new ArrayList<Folder>();
             ArrayList<File> filez = new ArrayList<File>();
             for(File ef : files)
             {
                 System.out.println(ef.getAbsolutePath());
                 if(ef.isDirectory())
                 {
                    folders.add(new Folder(ef));
                 }
                 else
                 {
                    filez.add(ef);
                 }
                 //if(!ef.isDirectory())writeFile(zout,ef,destFile);
             }

             for (Folder fol : folders)
             {
                 System.out.println("Writing " + fol.getPath());
                 writeFolder(zout,fol);

             }

             for(File ef : filez)
             {
                 System.out.println(ef.getAbsolutePath());

                 writeFile(zout,ef);
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
                     System.out.println("Writing finished");
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


    public static boolean unzip(String filePath, String destPath,String format) {



    if (format == "Gzip"){


        GZIPInputStream gin = null;
        try {
            gin = new GZIPInputStream(new FileInputStream(filePath));

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destPath + "gzip.output"));
            byte[] buf = new byte[1024];
            int len;
            while ((len = gin.read(buf)) > 0) {
                stream.write(buf, 0, len);
                stream.flush();
            }
            stream.close();
            gin.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



        return true;
    }

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

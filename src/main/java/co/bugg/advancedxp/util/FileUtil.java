package co.bugg.advancedxp.util;

import co.bugg.advancedxp.themes.Theme;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;

public final class FileUtil {

    private FileUtil() { throw new AssertionError(); }

    /**
     * Create a directory with the path passed
     * @param dir Path of the directory
     * @return boolean whether or not the directory now exists
     */
    public static boolean createDir(File dir) {

        if(!dir.isDirectory()) {
            boolean createSuccessful = dir.mkdir();

            if(createSuccessful) {
                System.out.println("Directory " + dir.getPath() + " created.");
            } else {
                System.out.println("Failed to create directory " + dir.getPath() + "!");
            }
        }

        // Return whether or not the directory exists now, after trying to create it
        return dir.isDirectory();
    }

    /**
     * Create a file with the path passed
     * @param file Path to the file
     * @return boolean whether or not the file now exists
     */
    public static boolean createFile(File file) {

        if(!file.exists()) {
            try {
                boolean createSuccessful = file.createNewFile();

                if(createSuccessful) {
                    System.out.println("File " + file.getPath() + " created");
                } else {
                    System.out.println("Failed to create file " + file.getPath() + "!");
                }
            } catch (IOException e) {
                System.out.println("Failed to create file " + file.getPath() + "!");
                e.printStackTrace();
            }
        }

        // Return whether or not the file exists now, after trying to create it
        return file.exists();
    }

    /**
     * Serialize provided theme into the provided file
     * @param file File to write to
     * @param theme Theme to write into file
     * @throws IOException When file doesn't exist or any other I/O error occurs
     * @return boolean whether the theme was written successfully
     */
    public static boolean serializeTheme(File file, Theme theme) throws IOException {
        Gson gson = new Gson();

        String themeJson = gson.toJson(theme);

        if (file.canWrite()) {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(themeJson.getBytes());
            stream.close();

            return true;
        } else
            return file.setWritable(true) && serializeTheme(file, theme);
    }

    /**
     * Deserialize them from the provided file
     * @param file File to write to
     * @throws IOException When file doesn't exist or any other I/O error occurs
     * @return Theme that was deserialized
     */
    public static Theme deserializeTheme(File file) throws IOException {
        Gson gson = new Gson();

        String json;

        if (file.canRead()) {
            json = FileUtils.readFileToString(file, Charsets.UTF_8);

        } else if(file.setReadable(true)) {
            return deserializeTheme(file);
        } else {
            return null;
        }

        return gson.fromJson(json, Theme.class);
    }


}

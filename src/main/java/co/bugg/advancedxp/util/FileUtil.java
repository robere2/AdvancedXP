package co.bugg.advancedxp.util;

import co.bugg.advancedxp.exception.DirectoryCreationFailedException;
import co.bugg.advancedxp.themes.Theme;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.*;

public final class FileUtil {

    private FileUtil() { throw new AssertionError(); }

    /**
     * Create a directory with the path passed
     * @param dir Path of the directory
     * @throws DirectoryCreationFailedException when the directory could not be created
     */
    public static void createDir(File dir) throws DirectoryCreationFailedException {

        if(!dir.isDirectory()) {
            boolean createSuccessful = dir.mkdir();

            if(createSuccessful) {
                System.out.println("Directory " + dir.getPath() + " created.");
            } else {
                System.out.println("Failed to create directory " + dir.getPath() + "!");
                throw new DirectoryCreationFailedException();
            }
        }

        // Return whether or not the directory exists now, after trying to create it
        if(!dir.isDirectory()) {
            throw new DirectoryCreationFailedException();
        }
    }

    /**
     * Create all directories in the path provided
     * @param path Path of the directories
     * @throws DirectoryCreationFailedException when the directory could not be created
     */
    public static void createDirRecursive(String path) throws DirectoryCreationFailedException {

        if(path.contains("/")) {
            String[] dirs = path.split("/");

            for (String dir : dirs) {
                File dirFile = new File(dir);
                createDir(dirFile);
            }
        } else {
            createDir(new File(path));
        }
    }

    /**
     * Create a file with the path passed
     * @param file Path to the file
     */
    public static void createFile(File file) throws IOException {

        if(!file.exists()) {
            boolean createSuccessful = file.createNewFile();

            if(createSuccessful) {
                System.out.println("File " + file.getPath() + " created");
            } else {
                System.out.println("Failed to create file " + file.getPath() + "!");
            }
        }

        // Return whether or not the file exists now, after trying to create it
        if(!file.exists()) {
            throw new FileNotFoundException("File could not be created");
        }
    }

    /**
     * Serialize provided theme into the provided file
     * @param file File to write to
     * @param theme Theme to write into file
     * @throws IOException When file doesn't exist or any other I/O error occurs
     * @return boolean whether the theme was written successfully
     */
    public static boolean serializeTheme(File file, Theme theme) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

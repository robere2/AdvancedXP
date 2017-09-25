package co.bugg.advancedxp.util;

import co.bugg.advancedxp.AdvancedXP;
import co.bugg.advancedxp.exception.DuplicateThemeException;
import co.bugg.advancedxp.themes.Theme;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;

public final class ThemeUtil {
    private ThemeUtil() { throw new AssertionError(); }

    /**
     * Load all themes within the provided directory.
     * This should only be called once per mod instance
     * @param dir Directory to search
     * @throws IOException Input or output exception
     * @throws DuplicateThemeException Multiple themes with the same name or set to default
     */
    public static void loadThemes(@NotNull File dir) throws IOException, DuplicateThemeException {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    loadThemes(file);
                } else {
                    Theme theme = FileUtil.deserializeTheme(file);
                    if(theme != null) {
                        if(getTheme(theme.name) == null) {
                            theme.fileName = file.getName();
                            AdvancedXP.instance.themes.add(theme);
                            if(theme.enabled) {
                                if(AdvancedXP.instance.theme == null) {
                                    AdvancedXP.instance.theme = theme;
                                } else {
                                    throw new DuplicateThemeException("Multiple themes are set to be enabled.");
                                }
                            }
                        } else {
                            throw new DuplicateThemeException("Theme " + theme.name + " has already been registered.");
                        }
                    } else {
                        System.out.println("Failed to read theme from " + file.getName() + ".");
                    }
                }
            }
        }

        // Check to make sure at least one theme is enabled, or else enable default
        if(AdvancedXP.instance.theme == null) {
            AdvancedXP.instance.theme = getTheme("Default");
        }
    }

    public static Theme getTheme(String name) {
        for(Theme theme : AdvancedXP.instance.themes) {
            if(theme.name.equals(name)) {
                return theme;
            }
        }
        return null;
    }
}

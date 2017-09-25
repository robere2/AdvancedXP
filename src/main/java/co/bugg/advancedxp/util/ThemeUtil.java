package co.bugg.advancedxp.util;

import co.bugg.advancedxp.AdvancedXP;
import co.bugg.advancedxp.exception.DuplicateThemeException;
import co.bugg.advancedxp.themes.Theme;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;

public final class ThemeUtil {
    private ThemeUtil() { throw new AssertionError(); }

    public static void loadThemes(@NotNull File dir) throws IOException, DuplicateThemeException {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    loadThemes(file);
                } else {
                    Theme theme = FileUtil.deserializeTheme(file);
                    if(theme != null) {
                        if(getTheme(theme.name) == null) {
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

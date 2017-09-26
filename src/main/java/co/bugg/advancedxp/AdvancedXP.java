package co.bugg.advancedxp;

import co.bugg.advancedxp.commands.BaseCommand;
import co.bugg.advancedxp.exception.DirectoryCreationFailedException;
import co.bugg.advancedxp.exception.DuplicateThemeException;
import co.bugg.advancedxp.render.RenderFactory;
import co.bugg.advancedxp.themes.Theme;
import co.bugg.advancedxp.util.FileUtil;
import co.bugg.advancedxp.util.ThemeUtil;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
        clientSideOnly = true,
        updateJSON = "https://github.com/bugfroggy/AdvancedXP/blob/master/versions.json"
)
public class AdvancedXP {

    public float color;
    public Theme theme = null;
    public LinkedList<Theme> themes = new LinkedList<>();

    public String configPath = "config/" + Reference.MOD_ID + "/";
    public String themesPath = configPath + "themes/";

    @Mod.Instance
    public static AdvancedXP instance = new AdvancedXP();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Register the new renderer for XP orbs
        RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, new RenderFactory());

        MinecraftForge.EVENT_BUS.register(new AdvancedXPEventHandler());

        // Try to create the themes directory if it doesn't exist already
        try {
            FileUtil.createDirRecursive(themesPath);
        } catch (DirectoryCreationFailedException e) {
            System.out.println("FAILED TO CREATE THEMES FOLDER");
            e.printStackTrace();
            return;
        }

        // Try to save the default theme to the themes folder
        // (This is overwritten every restart & can't be changed)
        Theme defaultTheme = new Theme();
        File defaultThemeFile = new File(themesPath + defaultTheme.fileName);
        try {
            FileUtil.createFile(defaultThemeFile);
            FileUtil.serializeTheme(defaultThemeFile, new Theme());
        } catch (IOException e) {
            System.out.println("DEFAULT THEME COULD NOT BE SAVED.");
            e.printStackTrace();
        }

        // Try to load all registered themes into the LinkedList of themes
        try {
            ThemeUtil.loadThemes(new File(themesPath));
        } catch (IOException | DuplicateThemeException | IllegalArgumentException e) {
            System.out.println("Failed to load themes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new BaseCommand());
    }
}

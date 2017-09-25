package co.bugg.advancedxp;

import co.bugg.advancedxp.exception.DirectoryCreationFailedException;
import co.bugg.advancedxp.exception.DuplicateThemeException;
import co.bugg.advancedxp.gui.MainGui;
import co.bugg.advancedxp.themes.Rainbow;
import co.bugg.advancedxp.themes.Theme;
import co.bugg.advancedxp.render.RenderFactory;
import co.bugg.advancedxp.util.FileUtil;
import co.bugg.advancedxp.util.ThemeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AdvancedXP {

    public float color;
    public Theme theme = null;
    public LinkedList<Theme> themes = new LinkedList<>();

    public String configPath = "mods/config/" + Reference.MOD_ID + "/";

    @Mod.Instance
    public static AdvancedXP instance = new AdvancedXP();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, new RenderFactory());
        MinecraftForge.EVENT_BUS.register(instance);

        try {
            FileUtil.createDirRecursive(configPath);
        } catch (DirectoryCreationFailedException e) {
            System.out.println("FAILED TO CREATE THEMES FOLDER");
            e.printStackTrace();
            return;
        }

        File defaultTheme = new File(configPath + "default.json");
        try {
            FileUtil.createFile(defaultTheme);
            FileUtil.serializeTheme(defaultTheme, new Theme());
        } catch (IOException e) {
            System.out.println("DEFAULT THEME COULD NOT BE SAVED.");
            e.printStackTrace();
        }

        try {
            ThemeUtil.loadThemes(new File(configPath));
        } catch (IOException | DuplicateThemeException e) {
            System.out.println("Failed to load themes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean skipTick = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(!skipTick) {
            color++;
        }
        skipTick = !skipTick;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        Minecraft.getMinecraft().displayGuiScreen(new MainGui());
    }
}

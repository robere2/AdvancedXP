package co.bugg.advancedxp;

import co.bugg.advancedxp.gui.MainGui;
import co.bugg.advancedxp.util.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;

public class AdvancedXPEventHandler {

    private int tickCount = 0;

    @SubscribeEvent
    public void incrementTickCount(TickEvent.ClientTickEvent event) {
        tickCount++;
    }

    @SubscribeEvent
    public void incrementColor(TickEvent.ClientTickEvent event) {
        // Only perform this action every other tick
        if(tickCount % 2 == 0) {
            AdvancedXP.instance.color++;
        }
    }


    @SubscribeEvent
    public void reloadTheme(TickEvent.ClientTickEvent event) {
        // Only perform this action every 40 ticks
        if(tickCount % 40 == 0) {
            try {
                AdvancedXP.instance.theme = FileUtil.deserializeTheme(new File(AdvancedXP.instance.themesPath + AdvancedXP.instance.theme.fileName));
            } catch (IOException e) {
                System.out.println("Failed to reload theme.");
                e.printStackTrace();
            }
        }
    }
}

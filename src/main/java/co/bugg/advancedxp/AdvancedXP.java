package co.bugg.advancedxp;

import co.bugg.advancedxp.gui.ExampleGui;
import co.bugg.advancedxp.themes.Rainbow;
import co.bugg.advancedxp.themes.Theme;
import co.bugg.advancedxp.render.RenderFactory;
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

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AdvancedXP {

    public float color;
    public Theme theme;

    @Mod.Instance
    public static AdvancedXP instance = new AdvancedXP();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, new RenderFactory());
        MinecraftForge.EVENT_BUS.register(instance);

        theme = new Rainbow();
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
        Minecraft.getMinecraft().displayGuiScreen(new ExampleGui());
    }
}

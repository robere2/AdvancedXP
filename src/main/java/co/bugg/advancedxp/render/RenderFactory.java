package co.bugg.advancedxp.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactory implements IRenderFactory<EntityXPOrb> {
    @Override
    public Render<? super EntityXPOrb> createRenderFor(RenderManager manager) {
        return new RenderCustomXPOrb(manager);
    }
}

package co.bugg.advancedxp.render;

import co.bugg.advancedxp.AdvancedXP;
import co.bugg.advancedxp.themes.Theme;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;

public class RenderCustomXPOrb extends RenderXPOrb {

    public RenderCustomXPOrb(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!this.renderOutlines) {

            Theme theme = AdvancedXP.instance.theme;

            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y, (float)z);

            this.bindEntityTexture(entity);
            RenderHelper.enableStandardItemLighting();

            // Figure out the position/width/height of texture
            // and then its scale (how much it should be stretched)
            int orbTexture = entity.getTextureByXP();
            float xPos = (float)(orbTexture % 4 * 16) / 64.0F;
            float xScale = (float)(orbTexture % 4 * 16 + 16) / 64.0F;
            float yPos = (float)(orbTexture / 4 * 16) / 64.0F;
            float yScale = (float)(orbTexture / 4 * 16 + 16) / 64.0F;

            int brightnessForRender = entity.getBrightnessForRender(partialTicks);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)brightnessForRender % 65536, (float)brightnessForRender / 65536);

            // Reset color modifiers
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            // Determining whether the global static color
            // should be used, or color should be determined
            // based off of the XP orbs lifetime
            float color = theme.globalColor ? AdvancedXP.instance.color : entity.xpColor;

            // How fast the color should change
            float redModifier = (color + partialTicks) * theme.redSpeed;
            float blueModifier = (color + partialTicks) * theme.blueSpeed;
            float greenModifier = (color + partialTicks) * theme.greenSpeed;

            int red;
            int green;
            int blue;

            // Color modifiers
            if(theme.redStatic) {
                red = (int) (theme.redStaticValue * theme.redMultiplier);
            } else {
                 red = (int)((MathHelper.sin(redModifier + theme.redWaveOffset + ((theme.redSquareWave ? Math.abs(MathHelper.cos(redModifier + theme.redWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.redMultiplier * 255.0F);
            }
            if(theme.greenStatic) {
                green = (int) (theme.greenStaticValue * theme.greenMultiplier);
            } else {
                green = (int)((MathHelper.sin(greenModifier + theme.greenWaveOffset + ((theme.greenSquareWave ? Math.abs(MathHelper.cos(greenModifier + theme.greenWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.greenMultiplier * 255.0F);
            }
            if(theme.blueStatic) {
                blue = (int) (theme.blueStaticValue * theme.blueMultiplier);
            } else {
                blue = (int)((MathHelper.sin(blueModifier + theme.blueWaveOffset + ((theme.blueSquareWave ? Math.abs(MathHelper.cos(blueModifier + theme.blueWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.blueMultiplier * 255.0F);
            }

            // How big the orb should be
            GlStateManager.translate(theme.translateX, theme.translateY, theme.translateZ);
            GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(theme.scale, theme.scale, theme.scale);

            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

            bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double)xPos, (double)yScale).color(red, green, blue, 128).normal(-0.75F, -1F, 0.25F).endVertex();
            bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double)xScale, (double)yScale).color(red, green, blue, 128).normal(0.25F, -1F, 0F).endVertex();
            bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double)xScale, (double)yPos).color(red, green, blue, 128).normal(0.25F, 2F, 0F).endVertex();
            bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double)xPos, (double)yPos).color(red, green, blue, 128).normal(-0.75F, 2F, 0.25F).endVertex();

            tessellator.draw();

            GlStateManager.disableBlend();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }
}

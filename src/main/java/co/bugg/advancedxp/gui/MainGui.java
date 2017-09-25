package co.bugg.advancedxp.gui;

import co.bugg.advancedxp.AdvancedXP;
import co.bugg.advancedxp.Reference;
import co.bugg.advancedxp.themes.Theme;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class MainGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Theme theme = AdvancedXP.instance.theme;

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawDefaultBackground();

        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        Color color = getColor(partialTicks);

        GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);

        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/sample.png"));

        int orbWidth = 154;
        int orbHeight = 16;
        int sampleScale = 3;
        float finalScale = theme.scale * sampleScale;
        GlStateManager.scale(finalScale, finalScale, finalScale);
        drawTexturedModalRect((int) (((width / 2) - (orbWidth * finalScale / 2)) / finalScale), (int) (((height * 0.05) - (orbHeight * finalScale / 2)) / finalScale), 0, 0, orbWidth, orbHeight);

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }

    @Override
    public void initGui() {
        super.initGui();
    }

    /**
     * Get the color of the sample orb at
     * any given point in time.
     * @param partialTicks How much of a tick has gone by
     * @return Color for current moment in time
     */
    public Color getColor(float partialTicks) {

        Theme theme = AdvancedXP.instance.theme;

        // Determining whether the global static color
        // should be used, or color should be determined
        // based off of the XP orbs lifetime
        float color = AdvancedXP.instance.color;

        // How fast the color should change
        float redModifier = (color + partialTicks) * theme.redSpeed;
        float blueModifier = (color + partialTicks) * theme.blueSpeed;
        float greenModifier = (color + partialTicks) * theme.greenSpeed;

        float red;
        float green;
        float blue;

        // Color modifiers
        if(theme.redStatic) {
            red = (theme.redStaticValue * theme.redMultiplier) / 255;
        } else {
            red = ((MathHelper.sin(redModifier + theme.redWaveOffset + ((theme.redSquareWave ? Math.abs(MathHelper.cos(redModifier + theme.redWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.redMultiplier);
        }
        if(theme.greenStatic) {
            green = (theme.greenStaticValue * theme.greenMultiplier) / 255;
        } else {
            green = ((MathHelper.sin(greenModifier + theme.greenWaveOffset + ((theme.greenSquareWave ? Math.abs(MathHelper.cos(greenModifier + theme.greenWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.greenMultiplier);
        }
        if(theme.blueStatic) {
            blue = (theme.blueStaticValue * theme.blueMultiplier) / 255;
        } else {
            blue = ((MathHelper.sin(blueModifier + theme.blueWaveOffset + ((theme.blueSquareWave ? Math.abs(MathHelper.cos(blueModifier + theme.blueWaveOffset)) : 0))) + 1.0F) * 0.5F * theme.blueMultiplier);
        }

        return new Color(red, green, blue);
    }
}

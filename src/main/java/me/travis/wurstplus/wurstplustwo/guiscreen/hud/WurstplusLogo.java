package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusLogo
extends WurstplusPinnable {
    ResourceLocation r = new ResourceLocation("custom/wurst.png");

    public WurstplusLogo() {
        super("Logo", "Logo", 1.0f, 0, 0);
    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.get_x(), (float)this.get_y(), (float)0.0f);
        WurstplusTextureHelper.drawTexture((ResourceLocation)this.r, (float)this.get_x(), (float)this.get_y(), (float)100.0f, (float)25.0f);
        GL11.glPopMatrix();
        this.set_width(100);
        this.set_height(25);
    }
}

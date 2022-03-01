package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import me.travis.wurstplus.wurstplustwo.util.*;

public class WurstplusLogo extends WurstplusPinnable
{
    ResourceLocation r;
    
    public WurstplusLogo() {
        super("Logo", "Logo", 1.0f, 0, 0);
        this.r = new ResourceLocation("custom/wurst.png");
    }
    
    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.get_x(), (float)this.get_y(), 0.0f);
        WurstplusTextureHelper.drawTexture(this.r, (float)this.get_x(), (float)this.get_y(), 100.0f, 25.0f);
        GL11.glPopMatrix();
        this.set_width(100);
        this.set_height(25);
    }
}

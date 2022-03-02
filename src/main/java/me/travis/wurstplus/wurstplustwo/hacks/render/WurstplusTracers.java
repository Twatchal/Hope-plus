package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class WurstplusTracers extends WurstplusHack
{
    WurstplusSetting friends;
    WurstplusSetting range;
    WurstplusSetting width;
    WurstplusSetting offset;
    
    public WurstplusTracers() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.friends = this.create("Friends", "TracerFriends", false);
        this.range = this.create("Range", "TracerRange", 50, 0, 250);
        this.width = this.create("Width", "TracerWidth", 1.0, 0.0, 5.0);
        this.offset = this.create("Offset", "TracerOffset", 0.0, -4.0, 4.0);
        this.name = "Tracers";
        this.tag = "Tracers";
        this.description = "DRAWS LINES";
    }
    
    public void render(final WurstplusEventRender event) {
        if (WurstplusTracers.mc.field_71441_e == null) {
            return;
        }
        GlStateManager.func_179094_E();
        final float[][] colour = new float[1][1];
        EntityPlayer player;
        final Object o;
        WurstplusTracers.mc.field_71441_e.field_72996_f.forEach(entity -> {
            if (!(entity instanceof EntityPlayer) || entity == WurstplusTracers.mc.field_71439_g) {
                return;
            }
            else {
                player = entity;
                if (WurstplusTracers.mc.field_71439_g.func_70032_d((Entity)player) > this.range.get_value(1)) {
                    return;
                }
                else if (WurstplusFriendUtil.isFriend(player.func_70005_c_()) && !this.friends.get_value(true)) {
                    return;
                }
                else {
                    o[0] = this.getColorByDistance((Entity)player);
                    this.drawLineToEntity((Entity)player, o[0][0], o[0][1], o[0][2], o[0][3]);
                    return;
                }
            }
        });
        GlStateManager.func_179121_F();
    }
    
    public double interpolate(final double now, final double then) {
        return then + (now - then) * WurstplusTracers.mc.func_184121_ak();
    }
    
    public double[] interpolate(final Entity entity) {
        final double posX = this.interpolate(entity.field_70165_t, entity.field_70142_S) - WurstplusTracers.mc.func_175598_ae().field_78725_b;
        final double posY = this.interpolate(entity.field_70163_u, entity.field_70137_T) - WurstplusTracers.mc.func_175598_ae().field_78726_c;
        final double posZ = this.interpolate(entity.field_70161_v, entity.field_70136_U) - WurstplusTracers.mc.func_175598_ae().field_78723_d;
        return new double[] { posX, posY, posZ };
    }
    
    public void drawLineToEntity(final Entity e, final float red, final float green, final float blue, final float opacity) {
        final double[] xyz = this.interpolate(e);
        this.drawLine(xyz[0], xyz[1], xyz[2], e.field_70131_O, red, green, blue, opacity);
    }
    
    public void drawLine(final double posx, final double posy, final double posz, final double up, final float red, final float green, final float blue, final float opacity) {
        final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).func_178789_a(-(float)Math.toRadians(WurstplusTracers.mc.field_71439_g.field_70125_A)).func_178785_b(-(float)Math.toRadians(WurstplusTracers.mc.field_71439_g.field_70177_z));
        this.drawLineFromPosToPos(eyes.field_72450_a, eyes.field_72448_b + WurstplusTracers.mc.field_71439_g.func_70047_e() + (float)this.offset.get_value(1), eyes.field_72449_c, posx, posy, posz, up, red, green, blue, opacity);
    }
    
    public void drawLineFromPosToPos(final double posx, final double posy, final double posz, final double posx2, final double posy2, final double posz2, final double up, final float red, final float green, final float blue, final float opacity) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth((float)this.width.get_value(1));
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GlStateManager.func_179140_f();
        GL11.glLoadIdentity();
        WurstplusTracers.mc.field_71460_t.func_78467_g(WurstplusTracers.mc.func_184121_ak());
        GL11.glBegin(1);
        GL11.glVertex3d(posx, posy, posz);
        GL11.glVertex3d(posx2, posy2, posz2);
        GL11.glVertex3d(posx2, posy2, posz2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GlStateManager.func_179145_e();
    }
    
    public float[] getColorByDistance(final Entity entity) {
        if (entity instanceof EntityPlayer && WurstplusFriendUtil.isFriend(entity.func_70005_c_())) {
            return new float[] { 0.0f, 0.5f, 1.0f, 1.0f };
        }
        final Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0, Math.min(WurstplusTracers.mc.field_71439_g.func_70068_e(entity), 2500.0) / 2500.0) / 3.0), 1.0f, 0.8f) | 0xFF000000);
        return new float[] { col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, 1.0f };
    }
}

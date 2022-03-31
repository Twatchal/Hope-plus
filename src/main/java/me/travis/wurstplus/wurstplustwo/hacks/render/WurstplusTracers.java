package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WurstplusTracers
extends WurstplusHack {
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

    public void render(WurstplusEventRender event) {
        if (WurstplusTracers.mc.field_71441_e == null) {
            return;
        }
        GlStateManager.func_179094_E();
        float[][] colour = new float[1][1];
        WurstplusTracers.mc.field_71441_e.field_72996_f.forEach(entity -> {
            if (!(entity instanceof EntityPlayer) || entity == WurstplusTracers.mc.field_71439_g) {
                return;
            }
            EntityPlayer player = (EntityPlayer)entity;
            if (WurstplusTracers.mc.field_71439_g.func_70032_d((Entity)player) > (float)this.range.get_value(1)) {
                return;
            }
            if (WurstplusFriendUtil.isFriend((String)player.func_70005_c_()) && !this.friends.get_value(true)) {
                return;
            }
            colour[0] = this.getColorByDistance((Entity)player);
            this.drawLineToEntity((Entity)player, colour[0][0], colour[0][1], colour[0][2], colour[0][3]);
        }
        );
        GlStateManager.func_179121_F();
    }

    public double interpolate(double now, double then) {
        return then + (now - then) * (double)mc.func_184121_ak();
    }

    public double[] interpolate(Entity entity) {
        double posX = this.interpolate(entity.field_70165_t, entity.field_70142_S) - WurstplusTracers.mc.func_175598_ae().field_78725_b;
        double posY = this.interpolate(entity.field_70163_u, entity.field_70137_T) - WurstplusTracers.mc.func_175598_ae().field_78726_c;
        double posZ = this.interpolate(entity.field_70161_v, entity.field_70136_U) - WurstplusTracers.mc.func_175598_ae().field_78723_d;
        return new double[]{posX, posY, posZ};
    }

    public void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
        double[] xyz = this.interpolate(e);
        this.drawLine(xyz[0], xyz[1], xyz[2], e.field_70131_O, red, green, blue, opacity);
    }

    public void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).func_178789_a(- (float)Math.toRadians((double)WurstplusTracers.mc.field_71439_g.field_70125_A)).func_178785_b(- (float)Math.toRadians((double)WurstplusTracers.mc.field_71439_g.field_70177_z));
        this.drawLineFromPosToPos(eyes.field_72450_a, eyes.field_72448_b + (double)WurstplusTracers.mc.field_71439_g.func_70047_e() + (double)this.offset.get_value(1), eyes.field_72449_c, posx, posy, posz, up, red, green, blue, opacity);
    }

    public void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)this.width.get_value(1));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.func_179140_f();
        GL11.glLoadIdentity();
        WurstplusTracers.mc.field_71460_t.func_78467_g(mc.func_184121_ak());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.func_179145_e();
    }

    public float[] getColorByDistance(Entity entity) {
        if (entity instanceof EntityPlayer && WurstplusFriendUtil.isFriend((String)entity.func_70005_c_())) {
            return new float[]{0.0f, 0.5f, 1.0f, 1.0f};
        }
        Color col = new Color(Color.HSBtoRGB((float)((float)(Math.max((double)0.0, (double)(Math.min((double)WurstplusTracers.mc.field_71439_g.func_70068_e(entity), (double)2500.0) / 2500.0)) / 3.0)), (float)1.0f, (float)0.8f) | -16777216);
        return new float[]{(float)col.getRed() / 255.0f, (float)col.getGreen() / 255.0f, (float)col.getBlue() / 255.0f, 1.0f};
    }
}

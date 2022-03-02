package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.awt.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;

public class WurstplusChams extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting players;
    WurstplusSetting mobs;
    WurstplusSetting self;
    WurstplusSetting items;
    WurstplusSetting xporbs;
    WurstplusSetting xpbottles;
    WurstplusSetting pearl;
    WurstplusSetting top;
    WurstplusSetting scale;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting box_a;
    WurstplusSetting width;
    WurstplusSetting rainbow_mode;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    
    public WurstplusChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "ChamsMode", "Outline", this.combobox(new String[] { "Outline", "Wireframe" }));
        this.players = this.create("Players", "ChamsPlayers", true);
        this.mobs = this.create("Mobs", "ChamsMobs", true);
        this.self = this.create("Self", "ChamsSelf", true);
        this.items = this.create("Items", "ChamsItems", true);
        this.xporbs = this.create("Xp Orbs", "ChamsXPO", true);
        this.xpbottles = this.create("Xp Bottles", "ChamsBottles", true);
        this.pearl = this.create("Pearls", "ChamsPearls", true);
        this.top = this.create("Top", "ChamsTop", true);
        this.scale = this.create("Factor", "ChamsFactor", 0.0, -1.0, 1.0);
        this.r = this.create("R", "ChamsR", 255, 0, 255);
        this.g = this.create("G", "ChamsG", 255, 0, 255);
        this.b = this.create("B", "ChamsB", 255, 0, 255);
        this.a = this.create("A", "ChamsA", 100, 0, 255);
        this.box_a = this.create("Box A", "ChamsABox", 100, 0, 255);
        this.width = this.create("Width", "ChamsWdith", 2.0, 0.5, 5.0);
        this.rainbow_mode = this.create("Rainbow", "ChamsRainbow", false);
        this.sat = this.create("Satiation", "ChamsSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "ChamsBrightness", 0.8, 0.0, 1.0);
        this.name = "Chams";
        this.tag = "Chams";
        this.description = "see even less (now with epic colours)";
    }
    
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
    
    public void render(final WurstplusEventRender event) {
        if (this.items.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityItem && WurstplusChams.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xporbs.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityXPOrb && WurstplusChams.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.pearl.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityEnderPearl && WurstplusChams.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xpbottles.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityExpBottle && WurstplusChams.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    public void on_render_model(final WurstplusEventRenderEntityModel event) {
        if (event.stage != 0 || event.entity == null || (!this.self.get_value(true) && event.entity.equals((Object)WurstplusChams.mc.field_71439_g)) || (!this.players.get_value(true) && event.entity instanceof EntityPlayer) || (!this.mobs.get_value(true) && event.entity instanceof EntityMob)) {
            return;
        }
        final Color color = new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1));
        final boolean fancyGraphics = WurstplusChams.mc.field_71474_y.field_74347_j;
        WurstplusChams.mc.field_71474_y.field_74347_j = false;
        final float gamma = WurstplusChams.mc.field_71474_y.field_74333_Y;
        WurstplusChams.mc.field_71474_y.field_74333_Y = 10000.0f;
        if (this.top.get_value(true)) {
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (this.mode.in("outline")) {
            WurstplusRenderUtil.renderOne((float)this.width.get_value(1));
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderFive();
        }
        else {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1028, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179131_c((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!this.top.get_value(true)) {
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            WurstplusChams.mc.field_71474_y.field_74347_j = fancyGraphics;
            WurstplusChams.mc.field_71474_y.field_74333_Y = gamma;
        }
        catch (Exception ex) {}
        event.cancel();
    }
}

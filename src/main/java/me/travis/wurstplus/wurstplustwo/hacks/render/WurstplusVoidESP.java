package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class WurstplusVoidESP
extends WurstplusHack {
    WurstplusSetting void_radius;
    public final List<BlockPos> void_blocks;
    private final ICamera camera;

    public WurstplusVoidESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.void_radius = this.create("Range", "VoidESPRange", 6, 1, 10);
        this.void_blocks = new ArrayList();
        this.camera = new Frustum();
        this.name = "Void ESP";
        this.tag = "VoidESP";
        this.description = "OH FUCK A DEEP HOLE";
    }

    public void update() {
        if (WurstplusVoidESP.mc.field_71439_g == null) {
            return;
        }
        this.void_blocks.clear();
        Vec3i player_pos = new Vec3i(WurstplusVoidESP.mc.field_71439_g.field_70165_t, WurstplusVoidESP.mc.field_71439_g.field_70163_u, WurstplusVoidESP.mc.field_71439_g.field_70161_v);
        for (int x = player_pos.func_177958_n() - this.void_radius.get_value((int)1); x < player_pos.func_177958_n() + this.void_radius.get_value(1); ++x) {
            for (int z = player_pos.func_177952_p() - this.void_radius.get_value((int)1); z < player_pos.func_177952_p() + this.void_radius.get_value(1); ++z) {
                for (int y = player_pos.func_177956_o() + this.void_radius.get_value((int)1); y > player_pos.func_177956_o() - this.void_radius.get_value(1); --y) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (!this.is_void_hole(blockPos)) continue;
                    this.void_blocks.add((Object)blockPos);
                }
            }
        }
    }

    public boolean is_void_hole(BlockPos blockPos) {
        if (blockPos.func_177956_o() != 0) {
            return false;
        }
        if (WurstplusVoidESP.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        return true;
    }

    public void render(WurstplusEventRender event) {
        int r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        new ArrayList(this.void_blocks).forEach(pos -> {
            AxisAlignedBB bb = new AxisAlignedBB((double)pos.func_177958_n() - WurstplusVoidESP.mc.func_175598_ae().field_78730_l, (double)pos.func_177956_o() - WurstplusVoidESP.mc.func_175598_ae().field_78731_m, (double)pos.func_177952_p() - WurstplusVoidESP.mc.func_175598_ae().field_78728_n, (double)(pos.func_177958_n() + 1) - WurstplusVoidESP.mc.func_175598_ae().field_78730_l, (double)(pos.func_177956_o() + 1) - WurstplusVoidESP.mc.func_175598_ae().field_78731_m, (double)(pos.func_177952_p() + 1) - WurstplusVoidESP.mc.func_175598_ae().field_78728_n);
            this.camera.func_78547_a(WurstplusVoidESP.mc.func_175606_aa().field_70165_t, WurstplusVoidESP.mc.func_175606_aa().field_70163_u, WurstplusVoidESP.mc.func_175606_aa().field_70161_v);
            if (this.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + WurstplusVoidESP.mc.func_175598_ae().field_78730_l, bb.field_72338_b + WurstplusVoidESP.mc.func_175598_ae().field_78731_m, bb.field_72339_c + WurstplusVoidESP.mc.func_175598_ae().field_78728_n, bb.field_72336_d + WurstplusVoidESP.mc.func_175598_ae().field_78730_l, bb.field_72337_e + WurstplusVoidESP.mc.func_175598_ae().field_78731_m, bb.field_72334_f + WurstplusVoidESP.mc.func_175598_ae().field_78728_n))) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179147_l();
                GlStateManager.func_179097_i();
                GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
                GlStateManager.func_179090_x();
                GlStateManager.func_179132_a((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.5f);
                RenderGlobal.func_189694_a((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c, (double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f, (float)255.0f, (float)20.0f, (float)30.0f, (float)0.5f);
                RenderGlobal.func_189695_b((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c, (double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f, (float)255.0f, (float)20.0f, (float)30.0f, (float)0.22f);
                GL11.glDisable((int)2848);
                GlStateManager.func_179132_a((boolean)true);
                GlStateManager.func_179126_j();
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                GlStateManager.func_179121_F();
            }
        }
        );
    }
}

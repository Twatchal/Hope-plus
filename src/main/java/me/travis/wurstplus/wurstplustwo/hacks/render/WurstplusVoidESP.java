package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.init.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.wurstplus.*;
import java.util.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class WurstplusVoidESP extends WurstplusHack
{
    WurstplusSetting void_radius;
    public final List<BlockPos> void_blocks;
    private final ICamera camera;
    
    public WurstplusVoidESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.void_radius = this.create("Range", "VoidESPRange", 6, 1, 10);
        this.void_blocks = new ArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
        this.name = "Void ESP";
        this.tag = "VoidESP";
        this.description = "OH FUCK A DEEP HOLE";
    }
    
    public void update() {
        if (WurstplusVoidESP.mc.field_71439_g == null) {
            return;
        }
        this.void_blocks.clear();
        final Vec3i player_pos = new Vec3i(WurstplusVoidESP.mc.field_71439_g.field_70165_t, WurstplusVoidESP.mc.field_71439_g.field_70163_u, WurstplusVoidESP.mc.field_71439_g.field_70161_v);
        for (int x = player_pos.func_177958_n() - this.void_radius.get_value(1); x < player_pos.func_177958_n() + this.void_radius.get_value(1); ++x) {
            for (int z = player_pos.func_177952_p() - this.void_radius.get_value(1); z < player_pos.func_177952_p() + this.void_radius.get_value(1); ++z) {
                for (int y = player_pos.func_177956_o() + this.void_radius.get_value(1); y > player_pos.func_177956_o() - this.void_radius.get_value(1); --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    if (this.is_void_hole(blockPos)) {
                        this.void_blocks.add(blockPos);
                    }
                }
            }
        }
    }
    
    public boolean is_void_hole(final BlockPos blockPos) {
        return blockPos.func_177956_o() == 0 && WurstplusVoidESP.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150350_a;
    }
    
    public void render(final WurstplusEventRender event) {
        final int r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final AxisAlignedBB bb;
        new ArrayList(this.void_blocks).forEach(pos -> {
            bb = new AxisAlignedBB(pos.func_177958_n() - WurstplusVoidESP.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - WurstplusVoidESP.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - WurstplusVoidESP.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - WurstplusVoidESP.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - WurstplusVoidESP.mc.func_175598_ae().field_78731_m, pos.func_177952_p() + 1 - WurstplusVoidESP.mc.func_175598_ae().field_78728_n);
            this.camera.func_78547_a(WurstplusVoidESP.mc.func_175606_aa().field_70165_t, WurstplusVoidESP.mc.func_175606_aa().field_70163_u, WurstplusVoidESP.mc.func_175606_aa().field_70161_v);
            if (this.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + WurstplusVoidESP.mc.func_175598_ae().field_78730_l, bb.field_72338_b + WurstplusVoidESP.mc.func_175598_ae().field_78731_m, bb.field_72339_c + WurstplusVoidESP.mc.func_175598_ae().field_78728_n, bb.field_72336_d + WurstplusVoidESP.mc.func_175598_ae().field_78730_l, bb.field_72337_e + WurstplusVoidESP.mc.func_175598_ae().field_78731_m, bb.field_72334_f + WurstplusVoidESP.mc.func_175598_ae().field_78728_n))) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179147_l();
                GlStateManager.func_179097_i();
                GlStateManager.func_179120_a(770, 771, 0, 1);
                GlStateManager.func_179090_x();
                GlStateManager.func_179132_a(false);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                GL11.glLineWidth(1.5f);
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, 255.0f, 20.0f, 30.0f, 0.5f);
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, 255.0f, 20.0f, 30.0f, 0.22f);
                GL11.glDisable(2848);
                GlStateManager.func_179132_a(true);
                GlStateManager.func_179126_j();
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                GlStateManager.func_179121_F();
            }
        });
    }
}

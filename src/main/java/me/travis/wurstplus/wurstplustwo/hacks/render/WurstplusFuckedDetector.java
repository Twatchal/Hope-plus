package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.init.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.turok.draw.*;

public class WurstplusFuckedDetector extends WurstplusHack
{
    WurstplusSetting draw_own;
    WurstplusSetting draw_friends;
    WurstplusSetting render_mode;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    private boolean solid;
    private boolean outline;
    public Set<BlockPos> fucked_players;
    
    public WurstplusFuckedDetector() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.draw_own = this.create("Draw Own", "FuckedDrawOwn", false);
        this.draw_friends = this.create("Draw Friends", "FuckedDrawFriends", false);
        this.render_mode = this.create("Render Mode", "FuckedRenderMode", "Pretty", this.combobox(new String[] { "Pretty", "Solid", "Outline" }));
        this.r = this.create("R", "FuckedR", 255, 0, 255);
        this.g = this.create("G", "FuckedG", 255, 0, 255);
        this.b = this.create("B", "FuckedB", 255, 0, 255);
        this.a = this.create("A", "FuckedA", 100, 0, 255);
        this.fucked_players = new HashSet<BlockPos>();
        this.name = "Fucked Detector";
        this.tag = "FuckedDetector";
        this.description = "see if people are hecked";
    }
    
    protected void enable() {
        this.fucked_players.clear();
    }
    
    public void update() {
        if (WurstplusFuckedDetector.mc.field_71441_e == null) {
            return;
        }
        this.set_fucked_players();
    }
    
    public void set_fucked_players() {
        this.fucked_players.clear();
        for (final EntityPlayer player : WurstplusFuckedDetector.mc.field_71441_e.field_73010_i) {
            if (WurstplusEntityUtil.isLiving((Entity)player)) {
                if (player.func_110143_aJ() <= 0.0f) {
                    continue;
                }
                if (!this.is_fucked(player)) {
                    continue;
                }
                if (WurstplusFriendUtil.isFriend(player.func_70005_c_()) && !this.draw_friends.get_value(true)) {
                    continue;
                }
                if (player == WurstplusFuckedDetector.mc.field_71439_g && !this.draw_own.get_value(true)) {
                    continue;
                }
                this.fucked_players.add(new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v));
            }
        }
    }
    
    public boolean is_fucked(final EntityPlayer player) {
        final BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u - 1.0, player.field_70161_v);
        return WurstplusCrystalUtil.canPlaceCrystal(pos.func_177968_d()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177968_d().func_177968_d()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 1)).func_177230_c() == Blocks.field_150350_a) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177974_f()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177974_f().func_177974_f()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 1, 0)).func_177230_c() == Blocks.field_150350_a)) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177976_e()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177976_e().func_177976_e()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 1, 0)).func_177230_c() == Blocks.field_150350_a)) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177978_c()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177978_c().func_177978_c()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, -1)).func_177230_c() == Blocks.field_150350_a));
    }
    
    public void render(final WurstplusEventRender event) {
        if (this.render_mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
        }
        if (this.render_mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
        }
        if (this.render_mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
        }
        for (final BlockPos render_block : this.fucked_players) {
            if (render_block == null) {
                return;
            }
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (!this.outline) {
                continue;
            }
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
        }
    }
}

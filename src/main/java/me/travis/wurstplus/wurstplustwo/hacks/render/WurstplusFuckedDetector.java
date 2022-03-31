package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WurstplusFuckedDetector
extends WurstplusHack {
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
        this.render_mode = this.create("Render Mode", "FuckedRenderMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
        this.r = this.create("R", "FuckedR", 255, 0, 255);
        this.g = this.create("G", "FuckedG", 255, 0, 255);
        this.b = this.create("B", "FuckedB", 255, 0, 255);
        this.a = this.create("A", "FuckedA", 100, 0, 255);
        this.fucked_players = new HashSet();
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
        for (EntityPlayer player : WurstplusFuckedDetector.mc.field_71441_e.field_73010_i) {
            if (!WurstplusEntityUtil.isLiving((Entity)player) || player.func_110143_aJ() <= 0.0f || !this.is_fucked(player) || WurstplusFriendUtil.isFriend((String)player.func_70005_c_()) && !this.draw_friends.get_value(true) || player == WurstplusFuckedDetector.mc.field_71439_g && !this.draw_own.get_value(true)) continue;
            this.fucked_players.add((Object)new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v));
        }
    }

    public boolean is_fucked(EntityPlayer player) {
        BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u - 1.0, player.field_70161_v);
        if (WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177968_d()) || WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177968_d().func_177968_d()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 1)).func_177230_c() == Blocks.field_150350_a) {
            return true;
        }
        if (WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177974_f()) || WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177974_f().func_177974_f()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
            return true;
        }
        if (WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177976_e()) || WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177976_e().func_177976_e()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
            return true;
        }
        if (WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177978_c()) || WurstplusCrystalUtil.canPlaceCrystal((BlockPos)pos.func_177978_c().func_177978_c()) && WurstplusFuckedDetector.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, -1)).func_177230_c() == Blocks.field_150350_a) {
            return true;
        }
        return false;
    }

    public void render(WurstplusEventRender event) {
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
        for (BlockPos render_block : this.fucked_players) {
            if (render_block == null) {
                return;
            }
            if (this.solid) {
                RenderHelp.prepare((String)"quads");
                RenderHelp.draw_cube((BufferBuilder)RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), (float)1.0f, (float)1.0f, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a.get_value(1), (String)"all");
                RenderHelp.release();
            }
            if (!this.outline) continue;
            RenderHelp.prepare((String)"lines");
            RenderHelp.draw_cube_line((BufferBuilder)RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), (float)1.0f, (float)1.0f, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a.get_value(1), (String)"all");
            RenderHelp.release();
        }
    }
}

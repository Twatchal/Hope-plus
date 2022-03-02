package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.turok.draw.*;

public class WurstplusBedAura extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting range;
    WurstplusSetting hard;
    WurstplusSetting swing;
    private BlockPos render_pos;
    private int counter;
    private WurstplusBedAura.spoof_face spoof_looking;
    
    public WurstplusBedAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "BedAuraDelay", 6, 0, 20);
        this.range = this.create("Range", "BedAuraRange", 5, 0, 6);
        this.hard = this.create("Hard Rotate", "BedAuraRotate", false);
        this.swing = this.create("Swing", "BedAuraSwing", "Mainhand", this.combobox(new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.name = "Bed Aura";
        this.tag = "BedAura";
        this.description = "fucking endcrystal.me";
    }
    
    protected void enable() {
        this.render_pos = null;
        this.counter = 0;
    }
    
    protected void disable() {
        this.render_pos = null;
    }
    
    public void update() {
        if (WurstplusBedAura.mc.field_71439_g == null) {
            return;
        }
        if (this.counter > this.delay.get_value(1)) {
            this.counter = 0;
            this.place_bed();
            this.break_bed();
            this.refill_bed();
        }
        ++this.counter;
    }
    
    public void refill_bed() {
        if (!(WurstplusBedAura.mc.field_71462_r instanceof GuiContainer) && this.is_space()) {
            for (int i = 9; i < 35; ++i) {
                if (WurstplusBedAura.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
                    WurstplusBedAura.mc.field_71442_b.func_187098_a(WurstplusBedAura.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusBedAura.mc.field_71439_g);
                    break;
                }
            }
        }
    }
    
    private boolean is_space() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusBedAura.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
                return true;
            }
        }
        return false;
    }
    
    public void place_bed() {
        if (this.find_bed() == -1) {
            return;
        }
        final int bed_slot = this.find_bed();
        BlockPos best_pos = null;
        EntityPlayer best_target = null;
        float best_distance = (float)this.range.get_value(1);
        for (final EntityPlayer player : (List)WurstplusBedAura.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_())).collect(Collectors.toList())) {
            if (player == WurstplusBedAura.mc.field_71439_g) {
                continue;
            }
            if (best_distance < WurstplusBedAura.mc.field_71439_g.func_70032_d((Entity)player)) {
                continue;
            }
            boolean face_place = true;
            final BlockPos pos = get_pos_floor(player).func_177977_b();
            final BlockPos pos2 = this.check_side_block(pos);
            if (pos2 != null) {
                best_pos = pos2.func_177984_a();
                best_target = player;
                best_distance = WurstplusBedAura.mc.field_71439_g.func_70032_d((Entity)player);
                face_place = false;
            }
            if (!face_place) {
                continue;
            }
            final BlockPos upos = get_pos_floor(player);
            final BlockPos upos2 = this.check_side_block(upos);
            if (upos2 == null) {
                continue;
            }
            best_pos = upos2.func_177984_a();
            best_target = player;
            best_distance = WurstplusBedAura.mc.field_71439_g.func_70032_d((Entity)player);
        }
        if (best_target == null) {
            return;
        }
        this.render_pos = best_pos;
        if (this.spoof_looking == WurstplusBedAura.spoof_face.NORTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.field_71439_g.field_70177_z = 180.0f;
            }
            WurstplusBedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, WurstplusBedAura.mc.field_71439_g.field_70122_E));
        }
        else if (this.spoof_looking == WurstplusBedAura.spoof_face.SOUTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.field_71439_g.field_70177_z = 0.0f;
            }
            WurstplusBedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, WurstplusBedAura.mc.field_71439_g.field_70122_E));
        }
        else if (this.spoof_looking == WurstplusBedAura.spoof_face.WEST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.field_71439_g.field_70177_z = 90.0f;
            }
            WurstplusBedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, WurstplusBedAura.mc.field_71439_g.field_70122_E));
        }
        else if (this.spoof_looking == WurstplusBedAura.spoof_face.EAST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.field_71439_g.field_70177_z = -90.0f;
            }
            WurstplusBedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, WurstplusBedAura.mc.field_71439_g.field_70122_E));
        }
        WurstplusBlockUtil.placeBlock(best_pos, bed_slot, false, false, this.swing);
    }
    
    public void break_bed() {
        for (final BlockPos pos : (List)WurstplusBlockInteractHelper.getSphere(get_pos_floor((EntityPlayer)WurstplusBedAura.mc.field_71439_g), (float)this.range.get_value(1), this.range.get_value(1), false, true, 0).stream().filter(WurstplusBedAura::is_bed).collect(Collectors.toList())) {
            if (WurstplusBedAura.mc.field_71439_g.func_70093_af()) {
                WurstplusBedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusBedAura.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            WurstplusBlockUtil.openBlock(pos);
        }
    }
    
    public int find_bed() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusBedAura.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
                return i;
            }
        }
        return -1;
    }
    
    public BlockPos check_side_block(final BlockPos pos) {
        if (WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() != Blocks.field_150350_a && WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
            this.spoof_looking = WurstplusBedAura.spoof_face.WEST;
            return pos.func_177974_f();
        }
        if (WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() != Blocks.field_150350_a && WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177978_c().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
            this.spoof_looking = WurstplusBedAura.spoof_face.SOUTH;
            return pos.func_177978_c();
        }
        if (WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() != Blocks.field_150350_a && WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177976_e().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
            this.spoof_looking = WurstplusBedAura.spoof_face.EAST;
            return pos.func_177976_e();
        }
        if (WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() != Blocks.field_150350_a && WurstplusBedAura.mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
            this.spoof_looking = WurstplusBedAura.spoof_face.NORTH;
            return pos.func_177968_d();
        }
        return null;
    }
    
    public static BlockPos get_pos_floor(final EntityPlayer player) {
        return new BlockPos(Math.floor(player.field_70165_t), Math.floor(player.field_70163_u), Math.floor(player.field_70161_v));
    }
    
    public static boolean is_bed(final BlockPos pos) {
        final Block block = WurstplusBedAura.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        return block == Blocks.field_150324_C;
    }
    
    public void render(final WurstplusEventRender event) {
        if (this.render_pos == null) {
            return;
        }
        RenderHelp.prepare("lines");
        RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)this.render_pos.func_177958_n(), (float)this.render_pos.func_177956_o(), (float)this.render_pos.func_177952_p(), 1.0f, 0.2f, 1.0f, 255, 20, 20, 180, "all");
        RenderHelp.release();
    }
}

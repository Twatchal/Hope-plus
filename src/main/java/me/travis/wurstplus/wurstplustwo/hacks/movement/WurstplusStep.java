package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

public class WurstplusStep extends WurstplusHack
{
    WurstplusSetting mode;
    
    public WurstplusStep() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.mode = this.create("Mode", "StepMode", "Normal", this.combobox(new String[] { "Normal", "Reverse" }));
        this.name = "Step";
        this.tag = "Step";
        this.description = "Move up / down block big";
    }
    
    public void update() {
        if (!WurstplusStep.mc.field_71439_g.field_70123_F && this.mode.in("Normal")) {
            return;
        }
        if (!WurstplusStep.mc.field_71439_g.field_70122_E || WurstplusStep.mc.field_71439_g.func_70617_f_() || WurstplusStep.mc.field_71439_g.func_70090_H() || WurstplusStep.mc.field_71439_g.func_180799_ab() || WurstplusStep.mc.field_71439_g.field_71158_b.field_78901_c || WurstplusStep.mc.field_71439_g.field_70145_X) {
            return;
        }
        if (WurstplusStep.mc.field_71439_g.field_191988_bg == 0.0f && WurstplusStep.mc.field_71439_g.field_70702_br == 0.0f) {
            return;
        }
        final double n = this.get_n_normal();
        if (this.mode.in("Normal")) {
            if (n < 0.0 || n > 2.0) {
                return;
            }
            if (n == 2.0) {
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.42, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.78, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.63, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.51, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.9, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.21, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.45, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.43, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.func_70107_b(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 2.0, WurstplusStep.mc.field_71439_g.field_70161_v);
            }
            if (n == 1.5) {
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.41999998688698, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.7531999805212, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.00133597911214, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.16610926093821, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.24918707874468, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.1707870772188, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.func_70107_b(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.0, WurstplusStep.mc.field_71439_g.field_70161_v);
            }
            if (n == 1.0) {
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.41999998688698, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 0.7531999805212, WurstplusStep.mc.field_71439_g.field_70161_v, WurstplusStep.mc.field_71439_g.field_70122_E));
                WurstplusStep.mc.field_71439_g.func_70107_b(WurstplusStep.mc.field_71439_g.field_70165_t, WurstplusStep.mc.field_71439_g.field_70163_u + 1.0, WurstplusStep.mc.field_71439_g.field_70161_v);
            }
        }
        if (this.mode.in("Reverse")) {
            WurstplusStep.mc.field_71439_g.field_70181_x = -1.0;
        }
    }
    
    public double get_n_normal() {
        WurstplusStep.mc.field_71439_g.field_70138_W = 0.5f;
        double max_y = -1.0;
        final AxisAlignedBB grow = WurstplusStep.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, 0.05, 0.0).func_186662_g(0.05);
        if (!WurstplusStep.mc.field_71441_e.func_184144_a((Entity)WurstplusStep.mc.field_71439_g, grow.func_72317_d(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (final AxisAlignedBB aabb : WurstplusStep.mc.field_71441_e.func_184144_a((Entity)WurstplusStep.mc.field_71439_g, grow)) {
            if (aabb.field_72337_e > max_y) {
                max_y = aabb.field_72337_e;
            }
        }
        return max_y - WurstplusStep.mc.field_71439_g.field_70163_u;
    }
}

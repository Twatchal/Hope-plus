package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;

public class WurstplusStrafe extends WurstplusHack
{
    WurstplusSetting speed_mode;
    WurstplusSetting auto_sprint;
    WurstplusSetting on_water;
    WurstplusSetting auto_jump;
    WurstplusSetting backward;
    WurstplusSetting bypass;
    @EventHandler
    private Listener<WurstplusEventPlayerJump> on_jump;
    @EventHandler
    private Listener<WurstplusEventMove> player_move;
    
    public WurstplusStrafe() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.speed_mode = this.create("Mode", "StrafeMode", "Strafe", this.combobox(new String[] { "Strafe", "On Ground" }));
        this.auto_sprint = this.create("Auto Sprint", "StrafeSprint", true);
        this.on_water = this.create("On Water", "StrafeOnWater", true);
        this.auto_jump = this.create("Auto Jump", "StrafeAutoJump", true);
        this.backward = this.create("Backwards", "StrafeBackwards", true);
        this.bypass = this.create("Bypass", "StrafeBypass", false);
        this.on_jump = (Listener<WurstplusEventPlayerJump>)new Listener(event -> {
            if (this.speed_mode.in("Strafe")) {
                event.cancel();
            }
        }, new Predicate[0]);
        this.player_move = (Listener<WurstplusEventMove>)new Listener(event -> {
            if (this.speed_mode.in("On Ground")) {
                return;
            }
            if ((WurstplusStrafe.mc.field_71439_g.func_70090_H() || WurstplusStrafe.mc.field_71439_g.func_180799_ab()) && !this.speed_mode.get_value(true)) {
                return;
            }
            if (WurstplusStrafe.mc.field_71439_g.func_70093_af() || WurstplusStrafe.mc.field_71439_g.func_70617_f_() || WurstplusStrafe.mc.field_71439_g.field_70134_J || WurstplusStrafe.mc.field_71439_g.func_180799_ab() || WurstplusStrafe.mc.field_71439_g.func_70090_H() || WurstplusStrafe.mc.field_71439_g.field_71075_bZ.field_75100_b) {
                return;
            }
            float player_speed = 0.2873f;
            float move_forward = WurstplusStrafe.mc.field_71439_g.field_71158_b.field_192832_b;
            float move_strafe = WurstplusStrafe.mc.field_71439_g.field_71158_b.field_78902_a;
            float rotation_yaw = WurstplusStrafe.mc.field_71439_g.field_70177_z;
            if (WurstplusStrafe.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
                final int amp = WurstplusStrafe.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c).func_76458_c();
                player_speed *= 1.2f * (amp + 1);
            }
            if (!this.bypass.get_value(true)) {
                player_speed *= 1.0064f;
            }
            if (move_forward == 0.0f && move_strafe == 0.0f) {
                event.set_x(0.0);
                event.set_z(0.0);
            }
            else {
                if (move_forward != 0.0f) {
                    if (move_strafe > 0.0f) {
                        rotation_yaw += ((move_forward > 0.0f) ? -45 : 45);
                    }
                    else if (move_strafe < 0.0f) {
                        rotation_yaw += ((move_forward > 0.0f) ? 45 : -45);
                    }
                    move_strafe = 0.0f;
                    if (move_forward > 0.0f) {
                        move_forward = 1.0f;
                    }
                    else if (move_forward < 0.0f) {
                        move_forward = -1.0f;
                    }
                }
                event.set_x(move_forward * player_speed * Math.cos(Math.toRadians(rotation_yaw + 90.0f)) + move_strafe * player_speed * Math.sin(Math.toRadians(rotation_yaw + 90.0f)));
                event.set_z(move_forward * player_speed * Math.sin(Math.toRadians(rotation_yaw + 90.0f)) - move_strafe * player_speed * Math.cos(Math.toRadians(rotation_yaw + 90.0f)));
            }
            event.cancel();
        }, new Predicate[0]);
        this.name = "Strafe";
        this.tag = "Strafe";
        this.description = "its like running, but faster";
    }
    
    public void update() {
        if (WurstplusStrafe.mc.field_71439_g.func_184218_aH()) {
            return;
        }
        if ((WurstplusStrafe.mc.field_71439_g.func_70090_H() || WurstplusStrafe.mc.field_71439_g.func_180799_ab()) && !this.on_water.get_value(true)) {
            return;
        }
        if (WurstplusStrafe.mc.field_71439_g.field_191988_bg != 0.0f || WurstplusStrafe.mc.field_71439_g.field_70702_br != 0.0f) {
            if (WurstplusStrafe.mc.field_71439_g.field_191988_bg < 0.0f && !this.backward.get_value(true)) {
                return;
            }
            if (this.auto_sprint.get_value(true)) {
                WurstplusStrafe.mc.field_71439_g.func_70031_b(true);
            }
            if (WurstplusStrafe.mc.field_71439_g.field_70122_E && this.speed_mode.in("Strafe")) {
                if (this.auto_jump.get_value(true)) {
                    WurstplusStrafe.mc.field_71439_g.field_70181_x = 0.4050000011920929;
                }
                final float yaw = this.get_rotation_yaw() * 0.017453292f;
                final EntityPlayerSP field_71439_g = WurstplusStrafe.mc.field_71439_g;
                field_71439_g.field_70159_w -= MathHelper.func_76126_a(yaw) * 0.2f;
                final EntityPlayerSP field_71439_g2 = WurstplusStrafe.mc.field_71439_g;
                field_71439_g2.field_70179_y += MathHelper.func_76134_b(yaw) * 0.2f;
            }
            else if (WurstplusStrafe.mc.field_71439_g.field_70122_E && this.speed_mode.in("On Ground")) {
                final float yaw = this.get_rotation_yaw();
                final EntityPlayerSP field_71439_g3 = WurstplusStrafe.mc.field_71439_g;
                field_71439_g3.field_70159_w -= MathHelper.func_76126_a(yaw) * 0.2f;
                final EntityPlayerSP field_71439_g4 = WurstplusStrafe.mc.field_71439_g;
                field_71439_g4.field_70179_y += MathHelper.func_76134_b(yaw) * 0.2f;
                WurstplusStrafe.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusStrafe.mc.field_71439_g.field_70165_t, WurstplusStrafe.mc.field_71439_g.field_70163_u + 0.4, WurstplusStrafe.mc.field_71439_g.field_70161_v, false));
            }
        }
        if (WurstplusStrafe.mc.field_71474_y.field_74314_A.func_151470_d() && WurstplusStrafe.mc.field_71439_g.field_70122_E) {
            WurstplusStrafe.mc.field_71439_g.field_70181_x = 0.4050000011920929;
        }
    }
    
    private float get_rotation_yaw() {
        float rotation_yaw = WurstplusStrafe.mc.field_71439_g.field_70177_z;
        if (WurstplusStrafe.mc.field_71439_g.field_191988_bg < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (WurstplusStrafe.mc.field_71439_g.field_191988_bg < 0.0f) {
            n = -0.5f;
        }
        else if (WurstplusStrafe.mc.field_71439_g.field_191988_bg > 0.0f) {
            n = 0.5f;
        }
        if (WurstplusStrafe.mc.field_71439_g.field_70702_br > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (WurstplusStrafe.mc.field_71439_g.field_70702_br < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }
}

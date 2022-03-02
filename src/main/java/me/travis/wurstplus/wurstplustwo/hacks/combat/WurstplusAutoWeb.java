package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class WurstplusAutoWeb extends WurstplusHack
{
    WurstplusSetting always_on;
    WurstplusSetting rotate;
    WurstplusSetting range;
    int new_slot;
    boolean sneak;
    
    public WurstplusAutoWeb() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.always_on = this.create("Always On", "AlwaysOn", true);
        this.rotate = this.create("Rotate", "AutoWebRotate", true);
        this.range = this.create("Enemy Range", "AutoWebRange", 4, 0, 8);
        this.new_slot = -1;
        this.sneak = false;
        this.name = "Auto Self Web";
        this.tag = "AutoSelfWeb";
        this.description = "places fuckin webs at ur feet";
    }
    
    public void enable() {
        if (WurstplusAutoWeb.mc.field_71439_g != null) {
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                WurstplusMessageUtil.send_client_error_message("cannot find webs in hotbar");
                this.set_active(false);
            }
        }
    }
    
    public void disable() {
        if (WurstplusAutoWeb.mc.field_71439_g != null && this.sneak) {
            WurstplusAutoWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusAutoWeb.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneak = false;
        }
    }
    
    public void update() {
        if (WurstplusAutoWeb.mc.field_71439_g == null) {
            return;
        }
        if (this.always_on.get_value(true)) {
            final EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)target) < this.range.get_value(1) && this.is_surround()) {
                final int last_slot = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c;
                WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
                WurstplusAutoWeb.mc.field_71442_b.func_78765_e();
                this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
                WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            }
        }
        else {
            final int last_slot2 = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c;
            WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
            WurstplusAutoWeb.mc.field_71442_b.func_78765_e();
            this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
            WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = last_slot2;
            this.set_disable();
        }
    }
    
    public EntityPlayer find_closest_target() {
        if (WurstplusAutoWeb.mc.field_71441_e.field_73010_i.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : WurstplusAutoWeb.mc.field_71441_e.field_73010_i) {
            if (target == WurstplusAutoWeb.mc.field_71439_g) {
                continue;
            }
            if (WurstplusFriendUtil.isFriend(target.func_70005_c_())) {
                continue;
            }
            if (!WurstplusEntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)target) > WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == Item.func_150899_d(30)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block).func_177230_c() == Blocks.field_150350_a;
    }
    
    private void place_blocks(final BlockPos pos) {
        if (!WurstplusAutoWeb.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                if (WurstplusBlockInteractHelper.blackList.contains(WurstplusAutoWeb.mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                    WurstplusAutoWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusAutoWeb.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (this.rotate.get_value(true)) {
                    WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                }
                WurstplusAutoWeb.mc.field_71442_b.func_187099_a(WurstplusAutoWeb.mc.field_71439_g, WurstplusAutoWeb.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                WurstplusAutoWeb.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                return;
            }
        }
    }
}

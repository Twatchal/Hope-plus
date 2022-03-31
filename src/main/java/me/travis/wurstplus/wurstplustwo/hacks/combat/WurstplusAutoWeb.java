package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class WurstplusAutoWeb
extends WurstplusHack {
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
                WurstplusMessageUtil.send_client_error_message((String)"cannot find webs in hotbar");
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
            EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)target) < (float)this.range.get_value(1) && this.is_surround()) {
                int last_slot = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c;
                WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
                WurstplusAutoWeb.mc.field_71442_b.func_78765_e();
                this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
                WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            }
        } else {
            int last_slot = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c;
            WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
            WurstplusAutoWeb.mc.field_71442_b.func_78765_e();
            this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
            WurstplusAutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            this.set_disable();
        }
    }

    public EntityPlayer find_closest_target() {
        if (WurstplusAutoWeb.mc.field_71441_e.field_73010_i.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (EntityPlayer target : WurstplusAutoWeb.mc.field_71441_e.field_73010_i) {
            if (target == WurstplusAutoWeb.mc.field_71439_g || WurstplusFriendUtil.isFriend((String)target.func_70005_c_()) || !WurstplusEntityUtil.isLiving((Entity)target) || target.func_110143_aJ() <= 0.0f || closestTarget != null && WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)target) > WurstplusAutoWeb.mc.field_71439_g.func_70032_d((Entity)closestTarget)) continue;
            closestTarget = target;
        }
        return closestTarget;
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusAutoWeb.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() != Item.func_150899_d((int)30)) continue;
            return i;
        }
        return -1;
    }

    private boolean is_surround() {
        BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a && WurstplusAutoWeb.mc.field_71441_e.func_180495_p(player_block).func_177230_c() == Blocks.field_150350_a;
    }

    private void place_blocks(BlockPos pos) {
        if (!WurstplusAutoWeb.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours((BlockPos)pos)) {
            return;
        }
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.func_176734_d();
            if (!WurstplusBlockInteractHelper.canBeClicked((BlockPos)neighbor)) continue;
            if (WurstplusBlockInteractHelper.blackList.contains((Object)WurstplusAutoWeb.mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                WurstplusAutoWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusAutoWeb.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                this.sneak = true;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
            if (this.rotate.get_value(true)) {
                WurstplusBlockInteractHelper.faceVectorPacketInstant((Vec3d)hitVec);
            }
            WurstplusAutoWeb.mc.field_71442_b.func_187099_a(WurstplusAutoWeb.mc.field_71439_g, WurstplusAutoWeb.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            WurstplusAutoWeb.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            return;
        }
    }
}

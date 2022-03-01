package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class WurstplusAutoNomadHut extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting triggerable;
    WurstplusSetting tick_for_place;
    Vec3d[] targets;
    int new_slot;
    int old_slot;
    int y_level;
    int tick_runs;
    int blocks_placed;
    int offset_step;
    boolean sneak;
    
    public WurstplusAutoNomadHut() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.rotate = this.create("Rotate", "NomadSmoth", true);
        this.triggerable = this.create("Toggle", "NomadToggle", true);
        this.tick_for_place = this.create("Blocks per tick", "NomadTickToPlace", 2, 1, 8);
        this.targets = new Vec3d[] { new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 1.0), new Vec3d(2.0, 0.0, -1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(1.0, 0.0, 2.0), new Vec3d(-1.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(-1.0, 0.0, -2.0), new Vec3d(1.0, 0.0, -2.0), new Vec3d(2.0, 1.0, -1.0), new Vec3d(2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, 0.0), new Vec3d(-2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(-1.0, 1.0, 2.0), new Vec3d(0.0, 1.0, -2.0), new Vec3d(1.0, 1.0, -2.0), new Vec3d(-1.0, 1.0, -2.0), new Vec3d(2.0, 2.0, -1.0), new Vec3d(2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 2.0), new Vec3d(-1.0, 2.0, 2.0), new Vec3d(1.0, 2.0, -2.0), new Vec3d(-1.0, 2.0, -2.0), new Vec3d(2.0, 3.0, 0.0), new Vec3d(2.0, 3.0, -1.0), new Vec3d(2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, 0.0), new Vec3d(-2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 2.0), new Vec3d(1.0, 3.0, 2.0), new Vec3d(-1.0, 3.0, 2.0), new Vec3d(0.0, 3.0, -2.0), new Vec3d(1.0, 3.0, -2.0), new Vec3d(-1.0, 3.0, -2.0), new Vec3d(0.0, 4.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(-1.0, 4.0, 0.0), new Vec3d(0.0, 4.0, 1.0), new Vec3d(0.0, 4.0, -1.0), new Vec3d(1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, -1.0), new Vec3d(1.0, 4.0, -1.0), new Vec3d(2.0, 4.0, 0.0), new Vec3d(2.0, 4.0, 1.0), new Vec3d(2.0, 4.0, -1.0) };
        this.new_slot = 0;
        this.old_slot = 0;
        this.y_level = 0;
        this.tick_runs = 0;
        this.blocks_placed = 0;
        this.offset_step = 0;
        this.sneak = false;
        this.name = "Auto Fitfag House";
        this.tag = "AutoNomadHut";
        this.description = "i fucking hate fit";
    }
    
    public void enable() {
        if (WurstplusAutoNomadHut.mc.field_71439_g != null) {
            this.old_slot = WurstplusAutoNomadHut.mc.field_71439_g.field_71071_by.field_70461_c;
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                WurstplusMessageUtil.send_client_error_message("cannot find obi in hotbar");
                this.set_active(false);
            }
            this.y_level = (int)Math.round(WurstplusAutoNomadHut.mc.field_71439_g.field_70163_u);
        }
    }
    
    public void disable() {
        if (WurstplusAutoNomadHut.mc.field_71439_g != null) {
            if (this.new_slot != this.old_slot && this.old_slot != -1) {
                WurstplusAutoNomadHut.mc.field_71439_g.field_71071_by.field_70461_c = this.old_slot;
            }
            if (this.sneak) {
                WurstplusAutoNomadHut.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusAutoNomadHut.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneak = false;
            }
            this.old_slot = -1;
            this.new_slot = -1;
        }
    }
    
    public void update() {
        if (WurstplusAutoNomadHut.mc.field_71439_g != null) {
            this.blocks_placed = 0;
            while (this.blocks_placed < this.tick_for_place.get_value(1)) {
                if (this.offset_step >= this.targets.length) {
                    this.offset_step = 0;
                    break;
                }
                final BlockPos offsetPos = new BlockPos(this.targets[this.offset_step]);
                final BlockPos targetPos = new BlockPos(WurstplusAutoNomadHut.mc.field_71439_g.func_174791_d()).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p()).func_177977_b();
                boolean try_to_place = true;
                if (!WurstplusAutoNomadHut.mc.field_71441_e.func_180495_p(targetPos).func_185904_a().func_76222_j()) {
                    try_to_place = false;
                }
                for (final Entity entity : WurstplusAutoNomadHut.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos))) {
                    if (!(entity instanceof EntityItem)) {
                        if (entity instanceof EntityXPOrb) {
                            continue;
                        }
                        try_to_place = false;
                        break;
                    }
                }
                if (try_to_place && this.place_blocks(targetPos)) {
                    ++this.blocks_placed;
                }
                ++this.offset_step;
            }
            if (this.blocks_placed > 0 && this.new_slot != this.old_slot) {
                WurstplusAutoNomadHut.mc.field_71439_g.field_71071_by.field_70461_c = this.old_slot;
            }
            ++this.tick_runs;
        }
    }
    
    private boolean place_blocks(final BlockPos pos) {
        if (!WurstplusAutoNomadHut.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                WurstplusAutoNomadHut.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
                final Block neighborPos;
                if (WurstplusBlockInteractHelper.blackList.contains(neighborPos = WurstplusAutoNomadHut.mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                    WurstplusAutoNomadHut.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusAutoNomadHut.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (this.rotate.get_value(true)) {
                    WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                }
                WurstplusAutoNomadHut.mc.field_71442_b.func_187099_a(WurstplusAutoNomadHut.mc.field_71439_g, WurstplusAutoNomadHut.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                WurstplusAutoNomadHut.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                return true;
            }
        }
        return false;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoNomadHut.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
}

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class WurstplusWebfill extends WurstplusHack
{
    WurstplusSetting web_toggle;
    WurstplusSetting web_rotate;
    WurstplusSetting web_range;
    private final ArrayList<BlockPos> holes;
    private boolean sneak;
    
    public WurstplusWebfill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.web_toggle = this.create("Toggle", "WebFillToggle", true);
        this.web_rotate = this.create("Rotate", "WebFillRotate", true);
        this.web_range = this.create("Range", "WebFillRange", 4, 1, 6);
        this.holes = new ArrayList<BlockPos>();
        this.name = "Web Fill";
        this.tag = "WebFill";
        this.description = "its like hole fill, but more annoying";
    }
    
    public void enable() {
        this.find_new_holes();
    }
    
    public void disable() {
        this.holes.clear();
    }
    
    public void update() {
        if (this.holes.isEmpty()) {
            if (!this.web_toggle.get_value(true)) {
                this.set_disable();
                WurstplusMessageUtil.toggle_message((WurstplusHack)this);
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) {
                continue;
            }
            final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result == WurstplusBlockInteractHelper.ValidResult.Ok) {
                pos_to_fill = pos;
                break;
            }
            this.holes.remove(pos);
        }
        final int obi_slot = this.find_in_hotbar();
        if (pos_to_fill != null && obi_slot != -1) {
            final int last_slot = WurstplusWebfill.mc.field_71439_g.field_71071_by.field_70461_c;
            WurstplusWebfill.mc.field_71439_g.field_71071_by.field_70461_c = obi_slot;
            WurstplusWebfill.mc.field_71442_b.func_78765_e();
            if (this.place_blocks(pos_to_fill)) {
                this.holes.remove(pos_to_fill);
            }
            WurstplusWebfill.mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
        }
    }
    
    public void find_new_holes() {
        this.holes.clear();
        for (final BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), (float)this.web_range.get_value(1), this.web_range.get_value(1), false, true, 0)) {
            if (!WurstplusWebfill.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!WurstplusWebfill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!WurstplusWebfill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            boolean possible = true;
            for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                final Block block = WurstplusWebfill.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                continue;
            }
            this.holes.add(pos);
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusWebfill.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == Item.func_150899_d(30)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean place_blocks(final BlockPos pos) {
        if (!WurstplusWebfill.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                final Block neighborPos;
                if (WurstplusBlockInteractHelper.blackList.contains(neighborPos = WurstplusWebfill.mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                    WurstplusWebfill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WurstplusWebfill.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (this.web_rotate.get_value(true)) {
                    WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                }
                WurstplusWebfill.mc.field_71442_b.func_187099_a(WurstplusWebfill.mc.field_71439_g, WurstplusWebfill.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                WurstplusWebfill.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                return true;
            }
        }
        return false;
    }
}

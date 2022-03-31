package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class WurstplusHoleFill
extends WurstplusHack {
    WurstplusSetting hole_toggle;
    WurstplusSetting hole_rotate;
    WurstplusSetting hole_range;
    WurstplusSetting swing;
    private final ArrayList<BlockPos> holes;

    public WurstplusHoleFill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.hole_toggle = this.create("Toggle", "HoleFillToggle", true);
        this.hole_rotate = this.create("Rotate", "HoleFillRotate", true);
        this.hole_range = this.create("Range", "HoleFillRange", 4, 1, 6);
        this.swing = this.create("Swing", "HoleFillSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.holes = new ArrayList();
        this.name = "Hole Fill";
        this.tag = "HoleFill";
        this.description = "Turn holes into floors";
    }

    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
        this.find_new_holes();
    }

    public void disable() {
        this.holes.clear();
    }

    public void update() {
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (this.holes.isEmpty()) {
            if (!this.hole_toggle.get_value(true)) {
                this.set_disable();
                WurstplusMessageUtil.toggle_message((WurstplusHack)this);
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (BlockPos pos : new ArrayList(this.holes)) {
            if (pos == null) continue;
            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid((BlockPos)pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok) {
                this.holes.remove((Object)pos);
                continue;
            }
            pos_to_fill = pos;
            break;
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (pos_to_fill != null && WurstplusBlockUtil.placeBlock((BlockPos)pos_to_fill, (int)this.find_in_hotbar(), (boolean)this.hole_rotate.get_value(true), (boolean)this.hole_rotate.get_value(true), (WurstplusSetting)this.swing)) {
            this.holes.remove((Object)pos_to_fill);
        }
    }

    public void find_new_holes() {
        this.holes.clear();
        for (BlockPos pos : WurstplusBlockInteractHelper.getSphere((BlockPos)WurstplusPlayerUtil.GetLocalPlayerPosFloored(), (float)this.hole_range.get_value(1), (int)this.hole_range.get_value(1), (boolean)false, (boolean)true, (int)0)) {
            if (!WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals((Object)Blocks.field_150350_a) || !WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals((Object)Blocks.field_150350_a) || !WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals((Object)Blocks.field_150350_a)) continue;
            boolean possible = true;
            for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                Block block = WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                if (block == Blocks.field_150357_h || block == Blocks.field_150343_Z || block == Blocks.field_150477_bB || block == Blocks.field_150467_bQ) continue;
                possible = false;
                break;
            }
            if (!possible) continue;
            this.holes.add((Object)pos);
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusHoleFill.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockEnderChest) {
                return i;
            }
            if (!(block instanceof BlockObsidian)) continue;
            return i;
        }
        return -1;
    }
}

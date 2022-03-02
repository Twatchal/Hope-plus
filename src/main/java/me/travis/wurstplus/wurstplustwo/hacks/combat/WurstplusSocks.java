package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class WurstplusSocks extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting swing;
    
    public WurstplusSocks() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "SocksRotate", false);
        this.swing = this.create("Swing", "SocksSwing", "Mainhand", this.combobox(new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.name = "Socks";
        this.tag = "Socks";
        this.description = "Protects your feet";
    }
    
    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
    }
    
    public void update() {
        final int slot = this.find_in_hotbar();
        if (slot == -1) {
            return;
        }
        final BlockPos center_pos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        final ArrayList<BlockPos> blocks_to_fill = new ArrayList<BlockPos>();
        switch (WurstplusSocks.WurstplusSocks$1.$SwitchMap$me$travis$wurstplus$wurstplustwo$util$WurstplusPlayerUtil$FacingDirection[WurstplusPlayerUtil.GetFacing().ordinal()]) {
            case 1: {
                blocks_to_fill.add(center_pos.func_177974_f().func_177974_f());
                blocks_to_fill.add(center_pos.func_177974_f().func_177974_f().func_177984_a());
                blocks_to_fill.add(center_pos.func_177974_f().func_177974_f().func_177974_f());
                blocks_to_fill.add(center_pos.func_177974_f().func_177974_f().func_177974_f().func_177984_a());
                break;
            }
            case 2: {
                blocks_to_fill.add(center_pos.func_177978_c().func_177978_c());
                blocks_to_fill.add(center_pos.func_177978_c().func_177978_c().func_177984_a());
                blocks_to_fill.add(center_pos.func_177978_c().func_177978_c().func_177978_c());
                blocks_to_fill.add(center_pos.func_177978_c().func_177978_c().func_177978_c().func_177984_a());
                break;
            }
            case 3: {
                blocks_to_fill.add(center_pos.func_177968_d().func_177968_d());
                blocks_to_fill.add(center_pos.func_177968_d().func_177968_d().func_177984_a());
                blocks_to_fill.add(center_pos.func_177968_d().func_177968_d().func_177968_d());
                blocks_to_fill.add(center_pos.func_177968_d().func_177968_d().func_177968_d().func_177984_a());
                break;
            }
            case 4: {
                blocks_to_fill.add(center_pos.func_177976_e().func_177976_e());
                blocks_to_fill.add(center_pos.func_177976_e().func_177976_e().func_177984_a());
                blocks_to_fill.add(center_pos.func_177976_e().func_177976_e().func_177976_e());
                blocks_to_fill.add(center_pos.func_177976_e().func_177976_e().func_177976_e().func_177984_a());
                break;
            }
        }
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : blocks_to_fill) {
            final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok) {
                continue;
            }
            if (pos == null) {
                continue;
            }
            pos_to_fill = pos;
            break;
        }
        WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSocks.mc.field_71439_g.field_71071_by.func_70301_a(i);
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

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSocks;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class WurstplusSocks
extends WurstplusHack {
    WurstplusSetting rotate;
    WurstplusSetting swing;

    public WurstplusSocks() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "SocksRotate", false);
        this.swing = this.create("Swing", "SocksSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.name = "Socks";
        this.tag = "Socks";
        this.description = "Protects your feet";
    }

    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
    }

    public void update() {
        int slot = this.find_in_hotbar();
        if (slot == -1) {
            return;
        }
        BlockPos center_pos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        ArrayList blocks_to_fill = new ArrayList();
        switch (.$SwitchMap$me$travis$wurstplus$wurstplustwo$util$WurstplusPlayerUtil$FacingDirection[WurstplusPlayerUtil.GetFacing().ordinal()]) {
            case 1: {
                blocks_to_fill.add((Object)center_pos.func_177974_f().func_177974_f());
                blocks_to_fill.add((Object)center_pos.func_177974_f().func_177974_f().func_177984_a());
                blocks_to_fill.add((Object)center_pos.func_177974_f().func_177974_f().func_177974_f());
                blocks_to_fill.add((Object)center_pos.func_177974_f().func_177974_f().func_177974_f().func_177984_a());
                break;
            }
            case 2: {
                blocks_to_fill.add((Object)center_pos.func_177978_c().func_177978_c());
                blocks_to_fill.add((Object)center_pos.func_177978_c().func_177978_c().func_177984_a());
                blocks_to_fill.add((Object)center_pos.func_177978_c().func_177978_c().func_177978_c());
                blocks_to_fill.add((Object)center_pos.func_177978_c().func_177978_c().func_177978_c().func_177984_a());
                break;
            }
            case 3: {
                blocks_to_fill.add((Object)center_pos.func_177968_d().func_177968_d());
                blocks_to_fill.add((Object)center_pos.func_177968_d().func_177968_d().func_177984_a());
                blocks_to_fill.add((Object)center_pos.func_177968_d().func_177968_d().func_177968_d());
                blocks_to_fill.add((Object)center_pos.func_177968_d().func_177968_d().func_177968_d().func_177984_a());
                break;
            }
            case 4: {
                blocks_to_fill.add((Object)center_pos.func_177976_e().func_177976_e());
                blocks_to_fill.add((Object)center_pos.func_177976_e().func_177976_e().func_177984_a());
                blocks_to_fill.add((Object)center_pos.func_177976_e().func_177976_e().func_177976_e());
                blocks_to_fill.add((Object)center_pos.func_177976_e().func_177976_e().func_177976_e().func_177984_a());
                break;
            }
        }
        BlockPos pos_to_fill = null;
        for (BlockPos pos : blocks_to_fill) {
            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid((BlockPos)pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok || pos == null) continue;
            pos_to_fill = pos;
            break;
        }
        WurstplusBlockUtil.placeBlock((BlockPos)pos_to_fill, (int)this.find_in_hotbar(), (boolean)this.rotate.get_value(true), (boolean)this.rotate.get_value(true), (WurstplusSetting)this.swing);
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSocks.mc.field_71439_g.field_71071_by.func_70301_a(i);
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

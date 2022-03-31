package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;

public class WurstplusFastUtil
extends WurstplusHack {
    WurstplusSetting fast_place;
    WurstplusSetting fast_break;
    WurstplusSetting crystal;
    WurstplusSetting exp;

    public WurstplusFastUtil() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.fast_place = this.create("Place", "WurstplusFastPlace", false);
        this.fast_break = this.create("Break", "WurstplusFastBreak", false);
        this.crystal = this.create("Crystal", "WurstplusFastCrystal", false);
        this.exp = this.create("EXP", "WurstplusFastExp", true);
        this.name = "Fast Use";
        this.tag = "FastUtil";
        this.description = "use things faster";
    }

    public void update() {
        Item main = WurstplusFastUtil.mc.field_71439_g.func_184614_ca().func_77973_b();
        Item off = WurstplusFastUtil.mc.field_71439_g.func_184592_cb().func_77973_b();
        boolean main_exp = main instanceof ItemExpBottle;
        boolean off_exp = off instanceof ItemExpBottle;
        boolean main_cry = main instanceof ItemEndCrystal;
        boolean off_cry = off instanceof ItemEndCrystal;
        if (main_exp | off_exp && this.exp.get_value(true)) {
            WurstplusFastUtil.mc.field_71467_ac = 0;
        }
        if (main_cry | off_cry && this.crystal.get_value(true)) {
            WurstplusFastUtil.mc.field_71467_ac = 0;
        }
        if (!(main_exp | off_exp | main_cry | off_cry) && this.fast_place.get_value(true)) {
            WurstplusFastUtil.mc.field_71467_ac = 0;
        }
        if (this.fast_break.get_value(true)) {
            WurstplusFastUtil.mc.field_71442_b.field_78781_i = 0;
        }
    }
}

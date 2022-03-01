package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.item.*;

public class WurstplusFastUtil extends WurstplusHack
{
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
        final Item main = WurstplusFastUtil.mc.field_71439_g.func_184614_ca().func_77973_b();
        final Item off = WurstplusFastUtil.mc.field_71439_g.func_184592_cb().func_77973_b();
        final boolean main_exp = main instanceof ItemExpBottle;
        final boolean off_exp = off instanceof ItemExpBottle;
        final boolean main_cry = main instanceof ItemEndCrystal;
        final boolean off_cry = off instanceof ItemEndCrystal;
        if ((main_exp | off_exp) && this.exp.get_value(true)) {
            WurstplusFastUtil.mc.field_71467_ac = 0;
        }
        if ((main_cry | off_cry) && this.crystal.get_value(true)) {
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

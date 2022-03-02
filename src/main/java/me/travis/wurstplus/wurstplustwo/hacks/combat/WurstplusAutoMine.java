package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.util.math.*;
import java.util.*;

public class WurstplusAutoMine extends WurstplusHack
{
    WurstplusSetting end_crystal;
    WurstplusSetting range;
    
    public WurstplusAutoMine() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.end_crystal = this.create("End Crystal", "MineEndCrystal", false);
        this.range = this.create("Range", "MineRange", 4, 0, 6);
        this.name = "Auto Mine";
        this.tag = "AutoMine";
        this.description = "jumpy is now never going to use the client again";
    }
    
    protected void enable() {
        BlockPos target_block = null;
        for (final EntityPlayer player : WurstplusAutoMine.mc.field_71441_e.field_73010_i) {
            if (WurstplusAutoMine.mc.field_71439_g.func_70032_d((Entity)player) > this.range.get_value(1)) {
                continue;
            }
            final BlockPos p = WurstplusEntityUtil.is_cityable(player, this.end_crystal.get_value(true));
            if (p == null) {
                continue;
            }
            target_block = p;
        }
        if (target_block == null) {
            WurstplusMessageUtil.send_client_message("cannot find block");
            this.disable();
        }
        WurstplusBreakUtil.set_current_block(target_block);
    }
    
    protected void disable() {
        WurstplusBreakUtil.set_current_block((BlockPos)null);
    }
}

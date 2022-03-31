package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBreakUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusAutoMine
extends WurstplusHack {
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
        for (EntityPlayer player : WurstplusAutoMine.mc.field_71441_e.field_73010_i) {
            BlockPos p;
            if (WurstplusAutoMine.mc.field_71439_g.func_70032_d((Entity)player) > (float)this.range.get_value(1) || (p = WurstplusEntityUtil.is_cityable((EntityPlayer)player, (boolean)this.end_crystal.get_value(true))) == null) continue;
            target_block = p;
        }
        if (target_block == null) {
            WurstplusMessageUtil.send_client_message((String)"cannot find block");
            this.disable();
        }
        WurstplusBreakUtil.set_current_block((BlockPos)target_block);
    }

    protected void disable() {
        WurstplusBreakUtil.set_current_block((BlockPos)null);
    }
}

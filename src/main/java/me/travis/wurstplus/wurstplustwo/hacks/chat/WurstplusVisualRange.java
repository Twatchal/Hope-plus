package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class WurstplusVisualRange extends WurstplusHack
{
    private List<String> people;
    
    public WurstplusVisualRange() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Visual Range";
        this.tag = "VisualRange";
        this.description = "bc using ur eyes is overrated";
    }
    
    public void enable() {
        this.people = new ArrayList<String>();
    }
    
    public void update() {
        if (WurstplusVisualRange.mc.field_71441_e == null | WurstplusVisualRange.mc.field_71439_g == null) {
            return;
        }
        final List<String> peoplenew = new ArrayList<String>();
        final List<EntityPlayer> playerEntities = (List<EntityPlayer>)WurstplusVisualRange.mc.field_71441_e.field_73010_i;
        for (final Entity e : playerEntities) {
            if (e.func_70005_c_().equals(WurstplusVisualRange.mc.field_71439_g.func_70005_c_())) {
                continue;
            }
            peoplenew.add(e.func_70005_c_());
        }
        if (peoplenew.size() > 0) {
            for (final String name : peoplenew) {
                if (!this.people.contains(name)) {
                    if (WurstplusFriendUtil.isFriend(name)) {
                        WurstplusMessageUtil.send_client_message("I see an epic dude called " + ChatFormatting.RESET + ChatFormatting.GREEN + name + ChatFormatting.RESET + " :D");
                    }
                    else {
                        WurstplusMessageUtil.send_client_message("I see a dude called " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". Yuk");
                    }
                    this.people.add(name);
                }
            }
        }
    }
}

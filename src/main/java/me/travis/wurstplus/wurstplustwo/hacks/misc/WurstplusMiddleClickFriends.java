package me.travis.wurstplus.wurstplustwo.hacks.misc;

import com.mojang.realmsclient.gui.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.entity.*;

public class WurstplusMiddleClickFriends extends WurstplusHack
{
    private boolean clicked;
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    
    public WurstplusMiddleClickFriends() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.clicked = false;
        this.name = "Middleclick Gang";
        this.tag = "MiddleclickFriends";
        this.description = "you press button and the world becomes a better place :D";
    }
    
    public void update() {
        if (WurstplusMiddleClickFriends.mc.field_71462_r != null) {
            return;
        }
        if (!Mouse.isButtonDown(2)) {
            this.clicked = false;
            return;
        }
        if (!this.clicked) {
            this.clicked = true;
            final RayTraceResult result = WurstplusMiddleClickFriends.mc.field_71476_x;
            if (result == null || result.field_72313_a != RayTraceResult.Type.ENTITY) {
                return;
            }
            if (!(result.field_72308_g instanceof EntityPlayer)) {
                return;
            }
            final Entity player = result.field_72308_g;
            if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
                final WurstplusFriendUtil.Friend f = (WurstplusFriendUtil.Friend)WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(player.func_70005_c_())).findFirst().get();
                WurstplusFriendUtil.friends.remove(f);
                WurstplusMessageUtil.send_client_message("Player " + WurstplusMiddleClickFriends.red + WurstplusMiddleClickFriends.bold + player.func_70005_c_() + WurstplusMiddleClickFriends.reset + " is now not your friend :(");
            }
            else {
                final WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object(player.func_70005_c_());
                WurstplusFriendUtil.friends.add(f);
                WurstplusMessageUtil.send_client_message("Player " + WurstplusMiddleClickFriends.green + WurstplusMiddleClickFriends.bold + player.func_70005_c_() + WurstplusMiddleClickFriends.reset + " is now your friend :D");
            }
        }
    }
    
    static {
        WurstplusMiddleClickFriends.red = ChatFormatting.RED;
        WurstplusMiddleClickFriends.green = ChatFormatting.GREEN;
        WurstplusMiddleClickFriends.bold = ChatFormatting.BOLD;
        WurstplusMiddleClickFriends.reset = ChatFormatting.RESET;
    }
}

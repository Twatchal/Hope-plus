package me.travis.wurstplus.wurstplustwo.hacks.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class WurstplusMiddleClickFriends
extends WurstplusHack {
    private boolean clicked = false;
    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    public WurstplusMiddleClickFriends() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Middleclick Gang";
        this.tag = "MiddleclickFriends";
        this.description = "you press button and the world becomes a better place :D";
    }

    public void update() {
        if (WurstplusMiddleClickFriends.mc.field_71462_r != null) {
            return;
        }
        if (!Mouse.isButtonDown((int)2)) {
            this.clicked = false;
            return;
        }
        if (!this.clicked) {
            this.clicked = true;
            RayTraceResult result = WurstplusMiddleClickFriends.mc.field_71476_x;
            if (result == null || result.field_72313_a != RayTraceResult.Type.ENTITY) {
                return;
            }
            if (!(result.field_72308_g instanceof EntityPlayer)) {
                return;
            }
            Entity player = result.field_72308_g;
            if (WurstplusFriendUtil.isFriend((String)player.func_70005_c_())) {
                WurstplusFriendUtil.Friend f = (WurstplusFriendUtil.Friend)WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(player.func_70005_c_())).findFirst().get();
                WurstplusFriendUtil.friends.remove((Object)f);
                WurstplusMessageUtil.send_client_message((String)("Player " + (Object)red + (Object)bold + player.func_70005_c_() + (Object)reset + " is now not your friend :("));
            } else {
                WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object((String)player.func_70005_c_());
                WurstplusFriendUtil.friends.add((Object)f);
                WurstplusMessageUtil.send_client_message((String)("Player " + (Object)green + (Object)bold + player.func_70005_c_() + (Object)reset + " is now your friend :D"));
            }
        }
    }
}

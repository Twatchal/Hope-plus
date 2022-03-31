package me.travis.wurstplus.wurstplustwo.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

public class WurstplusMessageUtil {
    public static final Minecraft mc = Minecraft.func_71410_x();
    public static ChatFormatting g = ChatFormatting.GOLD;
    public static ChatFormatting b = ChatFormatting.BLUE;
    public static ChatFormatting a = ChatFormatting.DARK_AQUA;
    public static ChatFormatting r = ChatFormatting.RESET;
    public static String opener = (Object)g + "\u00a79Hope \u00a78\u00bb" + (Object)ChatFormatting.GRAY + " " + (Object)r;

    public static void toggle_message(WurstplusHack module) {
        if (module.is_active()) {
            if (module.get_tag().equals((Object)"AutoCrystal")) {
                WurstplusMessageUtil.client_message_simple(opener + "\u00a7rAutocrystal \u00a77toggled" + (Object)ChatFormatting.DARK_GREEN + " \u00a7aon");
            } else {
                WurstplusMessageUtil.client_message_simple(opener + (Object)r + module.get_name() + (Object)ChatFormatting.DARK_GREEN + " \u00a77toggled \u00a7aon");
            }
        } else if (module.get_tag().equals((Object)"AutoCrystal")) {
            WurstplusMessageUtil.client_message_simple(opener + "\u00a7rAutocrystal \u00a77toggled" + (Object)ChatFormatting.RED + " \u00a7coff" + (Object)r + "");
        } else {
            WurstplusMessageUtil.client_message_simple(opener + (Object)r + module.get_name() + (Object)ChatFormatting.RED + " \u00a77toggled \u00a7coff");
        }
    }

    public static void client_message_simple(String message) {
        if (WurstplusMessageUtil.mc.field_71439_g != null) {
            ITextComponent itc = new TextComponentString(message).func_150255_a(new Style().func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("ez"))));
            WurstplusMessageUtil.mc.field_71456_v.func_146158_b().func_146234_a(itc, 5936);
        }
    }

    public static void client_message(String message) {
        if (WurstplusMessageUtil.mc.field_71439_g != null) {
            WurstplusMessageUtil.mc.field_71439_g.func_145747_a((ITextComponent)new ChatMessage(message));
        }
    }

    public static void send_client_message_simple(String message) {
        WurstplusMessageUtil.client_message((Object)ChatFormatting.GOLD + "\u00a79Hope \u00a78\u00bb" + " " + (Object)r + message);
    }

    public static void send_client_message(String message) {
        WurstplusMessageUtil.client_message((Object)ChatFormatting.GOLD + "\u00a79Hope \u00a78\u00bb" + " " + (Object)r + message);
    }

    public static void send_client_error_message(String message) {
        WurstplusMessageUtil.client_message((Object)ChatFormatting.RED + "\u00a74Hope \u00a78\u00bb" + " " + (Object)r + message);
    }
}

package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.command.*;
import net.minecraft.util.text.*;

public class WurstplusCommandManager
{
    public static WurstplusCommands command_list;
    
    public WurstplusCommandManager() {
        WurstplusCommandManager.command_list = new WurstplusCommands(new Style().func_150238_a(TextFormatting.BLUE));
    }
    
    public static void set_prefix(final String new_prefix) {
        WurstplusCommandManager.command_list.set_prefix(new_prefix);
    }
    
    public static String get_prefix() {
        return WurstplusCommandManager.command_list.get_prefix();
    }
}

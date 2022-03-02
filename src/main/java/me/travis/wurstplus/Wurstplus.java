package me.travis.wurstplus;

import net.minecraftforge.fml.common.*;
import me.travis.wurstplus.wurstplustwo.guiscreen.*;
import me.travis.turok.*;
import com.mojang.realmsclient.gui.*;
import net.minecraftforge.fml.common.event.*;
import me.travis.wurstplus.wurstplustwo.manager.*;
import org.lwjgl.opengl.*;
import me.travis.wurstplus.wurstplustwo.event.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import me.travis.turok.task.*;

@Mod(modid = "wurstplus", version = "0.1.2")
public class Wurstplus
{
    @Mod.Instance
    private static Wurstplus MASTER;
    public static final String WURSTPLUS_NAME = "Minecraft 1.12.2 | Hope v0.1.2";
    public static final String WURSTPLUS_VERSION = "0.1.2";
    public static final String WURSTPLUS_SIGN = " ";
    public static final int WURSTPLUS_KEY_GUI = 54;
    public static final int WURSTPLUS_KEY_DELETE = 211;
    public static final int WURSTPLUS_KEY_GUI_ESCAPE = 1;
    public static Logger wurstplus_register_log;
    private static WurstplusSettingManager setting_manager;
    private static WurstplusConfigManager config_manager;
    private static WurstplusModuleManager module_manager;
    private static WurstplusHUDManager hud_manager;
    public static WurstplusGUI click_gui;
    public static WurstplusHUD click_hud;
    public static Turok turok;
    public static ChatFormatting g;
    public static ChatFormatting r;
    
    @Mod.EventHandler
    public void WurstplusStarting(final FMLInitializationEvent event) {
        this.init_log("Minecraft 1.12.2 | Hope+ v0.1.2");
        WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();
        send_minecraft_log("initialising managers");
        Wurstplus.setting_manager = new WurstplusSettingManager();
        Wurstplus.config_manager = new WurstplusConfigManager();
        Wurstplus.module_manager = new WurstplusModuleManager();
        Wurstplus.hud_manager = new WurstplusHUDManager();
        final WurstplusEventManager event_manager = new WurstplusEventManager();
        final WurstplusCommandManager command_manager = new WurstplusCommandManager();
        send_minecraft_log("done");
        send_minecraft_log("initialising guis");
        Display.setTitle("Minecraft 1.12.2 | Hope+ v0.1.2");
        Wurstplus.click_gui = new WurstplusGUI();
        Wurstplus.click_hud = new WurstplusHUD();
        send_minecraft_log("done");
        send_minecraft_log("initialising skidded framework");
        Wurstplus.turok = new Turok("Turok");
        send_minecraft_log("done");
        send_minecraft_log("initialising commands and events");
        WurstplusEventRegister.register_command_manager(command_manager);
        WurstplusEventRegister.register_module_manager(event_manager);
        send_minecraft_log("done");
        send_minecraft_log("loading settings");
        Wurstplus.config_manager.load_settings();
        send_minecraft_log("done");
        if (Wurstplus.module_manager.get_module_with_tag("GUI").is_active()) {
            Wurstplus.module_manager.get_module_with_tag("GUI").set_active(false);
        }
        if (Wurstplus.module_manager.get_module_with_tag("HUD").is_active()) {
            Wurstplus.module_manager.get_module_with_tag("HUD").set_active(false);
        }
        send_minecraft_log("client started");
        send_minecraft_log("we gaming");
    }
    
    public void init_log(final String name) {
        Wurstplus.wurstplus_register_log = LogManager.getLogger(name);
        send_minecraft_log("starting wurstplustwo");
    }
    
    public static void send_minecraft_log(final String log) {
        Wurstplus.wurstplus_register_log.info(log);
    }
    
    public static String get_name() {
        return "Minecraft 1.12.2 | Hope+ v0.1.2";
    }
    
    public static String get_version() {
        return "0.1.2";
    }
    
    public static String get_actual_user() {
        return Minecraft.func_71410_x().func_110432_I().func_111285_a();
    }
    
    public static WurstplusConfigManager get_config_manager() {
        return Wurstplus.config_manager;
    }
    
    public static WurstplusModuleManager get_hack_manager() {
        return Wurstplus.module_manager;
    }
    
    public static WurstplusSettingManager get_setting_manager() {
        return Wurstplus.setting_manager;
    }
    
    public static WurstplusHUDManager get_hud_manager() {
        return Wurstplus.hud_manager;
    }
    
    public static WurstplusModuleManager get_module_manager() {
        return Wurstplus.module_manager;
    }
    
    public static WurstplusEventHandler get_event_handler() {
        return WurstplusEventHandler.INSTANCE;
    }
    
    public static String smoth(final String base) {
        return Font.smoth(base);
    }
    
    static {
        Wurstplus.g = ChatFormatting.DARK_GRAY;
        Wurstplus.r = ChatFormatting.RESET;
    }
}

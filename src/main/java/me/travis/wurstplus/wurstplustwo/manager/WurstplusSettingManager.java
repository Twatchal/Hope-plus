package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.*;

public class WurstplusSettingManager
{
    public ArrayList<WurstplusSetting> array_setting;
    
    public WurstplusSettingManager() {
        this.array_setting = new ArrayList<WurstplusSetting>();
    }
    
    public void register(final WurstplusSetting setting) {
        this.array_setting.add(setting);
    }
    
    public ArrayList<WurstplusSetting> get_array_settings() {
        return this.array_setting;
    }
    
    public WurstplusSetting get_setting_with_tag(final WurstplusHack module, final String tag) {
        WurstplusSetting setting_requested = null;
        for (final WurstplusSetting settings : this.get_array_settings()) {
            if (settings.get_master().equals(module) && settings.get_tag().equalsIgnoreCase(tag)) {
                setting_requested = settings;
            }
        }
        return setting_requested;
    }
    
    public WurstplusSetting get_setting_with_tag(final String tag, final String tag_) {
        WurstplusSetting setting_requested = null;
        for (final WurstplusSetting settings : this.get_array_settings()) {
            if (settings.get_master().get_tag().equalsIgnoreCase(tag) && settings.get_tag().equalsIgnoreCase(tag_)) {
                setting_requested = settings;
                break;
            }
        }
        return setting_requested;
    }
    
    public ArrayList<WurstplusSetting> get_settings_with_hack(final WurstplusHack module) {
        final ArrayList<WurstplusSetting> setting_requesteds = new ArrayList<WurstplusSetting>();
        for (final WurstplusSetting settings : this.get_array_settings()) {
            if (settings.get_master().equals(module)) {
                setting_requesteds.add(settings);
            }
        }
        return setting_requesteds;
    }
}

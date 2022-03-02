package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.*;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.*;
import java.util.function.*;
import java.util.*;

public class WurstplusHUDManager
{
    public static ArrayList<WurstplusPinnable> array_hud;
    
    public WurstplusHUDManager() {
        this.add_component_pinnable((WurstplusPinnable)new WurstplusWatermark());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusArrayList());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusCoordinates());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusInventoryPreview());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusInventoryXCarryPreview());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusArmorPreview());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusUser());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusTotemCount());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusCrystalCount());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusEXPCount());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusGappleCount());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusTime());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusLogo());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusFPS());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusPing());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusSurroundBlocks());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusFriendList());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusArmorDurabilityWarner());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusPvpHud());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusCompass());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusEffectHud());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusSpeedometer());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusEntityList());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusTPS());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusPlayerList());
        this.add_component_pinnable((WurstplusPinnable)new WurstplusDirection());
        WurstplusHUDManager.array_hud.sort(Comparator.comparing((Function<? super WurstplusPinnable, ? extends Comparable>)WurstplusPinnable::get_title));
    }
    
    public void add_component_pinnable(final WurstplusPinnable module) {
        WurstplusHUDManager.array_hud.add(module);
    }
    
    public ArrayList<WurstplusPinnable> get_array_huds() {
        return WurstplusHUDManager.array_hud;
    }
    
    public void render() {
        for (final WurstplusPinnable pinnables : this.get_array_huds()) {
            if (pinnables.is_active()) {
                pinnables.render();
            }
        }
    }
    
    public WurstplusPinnable get_pinnable_with_tag(final String tag) {
        WurstplusPinnable pinnable_requested = null;
        for (final WurstplusPinnable pinnables : this.get_array_huds()) {
            if (pinnables.get_tag().equalsIgnoreCase(tag)) {
                pinnable_requested = pinnables;
            }
        }
        return pinnable_requested;
    }
    
    static {
        WurstplusHUDManager.array_hud = new ArrayList<WurstplusPinnable>();
    }
}

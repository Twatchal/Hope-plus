package me.travis.wurstplus.wurstplustwo.hacks;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.WurstplusGUI;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

public class WurstplusClickGUI
extends WurstplusHack {
    WurstplusSetting label_frame;
    WurstplusSetting name_frame_r;
    WurstplusSetting name_frame_g;
    WurstplusSetting name_frame_b;
    WurstplusSetting background_frame_r;
    WurstplusSetting background_frame_g;
    WurstplusSetting background_frame_b;
    WurstplusSetting background_frame_a;
    WurstplusSetting border_frame_r;
    WurstplusSetting border_frame_g;
    WurstplusSetting border_frame_b;
    WurstplusSetting label_widget;
    WurstplusSetting name_widget_r;
    WurstplusSetting name_widget_g;
    WurstplusSetting name_widget_b;
    WurstplusSetting background_widget_r;
    WurstplusSetting background_widget_g;
    WurstplusSetting background_widget_b;
    WurstplusSetting background_widget_a;
    WurstplusSetting border_widget_r;
    WurstplusSetting border_widget_g;
    WurstplusSetting border_widget_b;

    public WurstplusClickGUI() {
        super(WurstplusCategory.WURSTPLUS_GUI);
        this.label_frame = this.create("info", "ClickGUIInfoFrame", "Frames");
        this.name_frame_r = this.create("Name R", "ClickGUINameFrameR", 255, 0, 255);
        this.name_frame_g = this.create("Name G", "ClickGUINameFrameG", 255, 0, 255);
        this.name_frame_b = this.create("Name B", "ClickGUINameFrameB", 255, 0, 255);
        this.background_frame_r = this.create("Background R", "ClickGUIBackgroundFrameR", 8, 0, 255);
        this.background_frame_g = this.create("Background G", "ClickGUIBackgroundFrameG", 8, 0, 255);
        this.background_frame_b = this.create("Background B", "ClickGUIBackgroundFrameB", 8, 0, 255);
        this.background_frame_a = this.create("Background A", "ClickGUIBackgroundFrameA", 210, 0, 255);
        this.border_frame_r = this.create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
        this.border_frame_g = this.create("Border G", "ClickGUIBorderFrameG", 1, 0, 255);
        this.border_frame_b = this.create("Border B", "ClickGUIBorderFrameB", 1, 0, 255);
        this.label_widget = this.create("info", "ClickGUIInfoWidget", "Widgets");
        this.name_widget_r = this.create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
        this.name_widget_g = this.create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
        this.name_widget_b = this.create("Name B", "ClickGUINameWidgetB", 255, 0, 255);
        this.background_widget_r = this.create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
        this.background_widget_g = this.create("Background G", "ClickGUIBackgroundWidgetG", 1, 0, 255);
        this.background_widget_b = this.create("Background B", "ClickGUIBackgroundWidgetB", 1, 0, 255);
        this.background_widget_a = this.create("Background A", "ClickGUIBackgroundWidgetA", 64, 0, 255);
        this.border_widget_r = this.create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
        this.border_widget_g = this.create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
        this.border_widget_b = this.create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);
        this.name = "GUI";
        this.tag = "GUI";
        this.description = "The main gui";
        this.set_bind(54);
    }

    public void update() {
        Wurstplus.click_gui.theme_frame_name_r = this.name_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_name_g = this.name_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_name_b = this.name_frame_b.get_value(1);
        Wurstplus.click_gui.theme_frame_background_r = this.background_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_background_g = this.background_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_background_b = this.background_frame_b.get_value(1);
        Wurstplus.click_gui.theme_frame_background_a = this.background_frame_a.get_value(1);
        Wurstplus.click_gui.theme_frame_border_r = this.border_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_border_g = this.border_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_border_b = this.border_frame_b.get_value(1);
        Wurstplus.click_gui.theme_widget_name_r = this.name_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_name_g = this.name_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_name_b = this.name_widget_b.get_value(1);
        Wurstplus.click_gui.theme_widget_background_r = this.background_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_background_g = this.background_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_background_b = this.background_widget_b.get_value(1);
        Wurstplus.click_gui.theme_widget_background_a = this.background_widget_a.get_value(1);
        Wurstplus.click_gui.theme_widget_border_r = this.border_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_border_g = this.border_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_border_b = this.border_widget_b.get_value(1);
    }

    public void enable() {
        if (WurstplusClickGUI.mc.field_71441_e != null && WurstplusClickGUI.mc.field_71439_g != null) {
            mc.func_147108_a((GuiScreen)Wurstplus.click_gui);
        }
    }

    public void disable() {
        if (WurstplusClickGUI.mc.field_71441_e != null && WurstplusClickGUI.mc.field_71439_g != null) {
            mc.func_147108_a(null);
        }
    }
}

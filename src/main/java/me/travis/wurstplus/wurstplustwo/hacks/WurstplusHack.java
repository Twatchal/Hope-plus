package me.travis.wurstplus.wurstplustwo.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.bus.EventBus;
import me.zero.alpine.fork.listener.Listenable;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class WurstplusHack
implements Listenable {
    public WurstplusCategory category;
    public String name = "";
    public String tag = "";
    public String description = "";
    public int bind = -1;
    public boolean state_module;
    public boolean toggle_message = true;
    public boolean widget_usage = false;
    public static final Minecraft mc = Minecraft.func_71410_x();

    public WurstplusHack(WurstplusCategory category) {
        this.category = category;
    }

    public void set_bind(int key) {
        this.bind = key;
    }

    public void set_if_can_send_message_toggle(boolean value) {
        this.toggle_message = value;
    }

    public boolean is_active() {
        return this.state_module;
    }

    public boolean using_widget() {
        return this.widget_usage;
    }

    public String get_name() {
        return this.name;
    }

    public String get_tag() {
        return this.tag;
    }

    public String get_description() {
        return this.description;
    }

    public int get_bind(int type) {
        return this.bind;
    }

    public String get_bind(String type) {
        String key;
        String converted_bind = "null";
        if (this.get_bind(0) < 0) {
            converted_bind = "NONE";
        }
        converted_bind = !converted_bind.equals((Object)"NONE") ? "" + Character.toUpperCase((char)key.charAt(0)) + ((key = Keyboard.getKeyName((int)this.get_bind(0))).length() != 1 ? key.substring(1).toLowerCase() : "") : "None";
        return converted_bind;
    }

    public WurstplusCategory get_category() {
        return this.category;
    }

    public boolean can_send_message_when_toggle() {
        return this.toggle_message;
    }

    public void set_disable() {
        this.state_module = false;
        this.disable();
        WurstplusEventBus.EVENT_BUS.unsubscribe((Listenable)this);
    }

    public void set_enable() {
        this.state_module = true;
        this.enable();
        WurstplusEventBus.EVENT_BUS.subscribe((Listenable)this);
    }

    public void set_active(boolean value) {
        if (this.state_module != value) {
            if (value) {
                this.set_enable();
            } else {
                this.set_disable();
            }
        }
        if (!this.tag.equals((Object)"GUI") && !this.tag.equals((Object)"HUD") && this.toggle_message) {
            WurstplusMessageUtil.toggle_message((WurstplusHack)this);
        }
    }

    public void toggle() {
        this.set_active(!this.is_active());
    }

    protected WurstplusSetting create(String name, String tag, int value, int min, int max) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }

    protected WurstplusSetting create(String name, String tag, double value, double min, double max) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }

    protected WurstplusSetting create(String name, String tag, boolean value) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }

    protected WurstplusSetting create(String name, String tag, String value) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }

    protected WurstplusSetting create(String name, String tag, String value, List<String> values) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, values, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }

    protected /* varargs */ List<String> combobox(String ... item) {
        return new ArrayList((Collection)Arrays.asList((Object[])item));
    }

    public void render(WurstplusEventRender event) {
    }

    public void render() {
    }

    public void update() {
    }

    public void event_widget() {
    }

    protected void disable() {
    }

    protected void enable() {
    }

    public String array_detail() {
        return null;
    }

    public void on_render_model(WurstplusEventRenderEntityModel event) {
    }
}

package me.travis.wurstplus.wurstplustwo.manager;

import net.minecraft.client.*;
import me.travis.wurstplus.wurstplustwo.hacks.chat.*;
import me.travis.wurstplus.wurstplustwo.hacks.combat.*;
import me.travis.wurstplus.wurstplustwo.hacks.movement.*;
import me.travis.wurstplus.wurstplustwo.hacks.render.*;
import me.travis.wurstplus.wurstplustwo.hacks.misc.*;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.*;
import me.travis.wurstplus.wurstplustwo.hacks.dev.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import me.travis.turok.draw.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import net.minecraft.client.renderer.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;

public class WurstplusModuleManager
{
    public static ArrayList<WurstplusHack> array_hacks;
    public static Minecraft mc;
    
    public WurstplusModuleManager() {
        this.add_hack((WurstplusHack)new WurstplusClickGUI());
        this.add_hack((WurstplusHack)new WurstplusClickHUD());
        this.add_hack((WurstplusHack)new WurstplusChatSuffix());
        this.add_hack((WurstplusHack)new WurstplusVisualRange());
        this.add_hack((WurstplusHack)new WurstplusTotempop());
        this.add_hack((WurstplusHack)new WurstplusClearChat());
        this.add_hack((WurstplusHack)new WurstplusChatMods());
        this.add_hack((WurstplusHack)new WurstplusAutoEz());
        this.add_hack((WurstplusHack)new WurstplusAntiRacist());
        this.add_hack((WurstplusHack)new WurstplusAnnouncer());
        this.add_hack((WurstplusHack)new WurstplusCriticals());
        this.add_hack((WurstplusHack)new WurstplusKillAura());
        this.add_hack((WurstplusHack)new WurstplusSurround());
        this.add_hack((WurstplusHack)new WurstplusVelocity());
        this.add_hack((WurstplusHack)new WurstplusAutoCrystal());
        this.add_hack((WurstplusHack)new WurstplusHoleFill());
        this.add_hack((WurstplusHack)new WurstplusTrap());
        this.add_hack((WurstplusHack)new WurstplusSocks());
        this.add_hack((WurstplusHack)new WurstplusSelfTrap());
        this.add_hack((WurstplusHack)new WurstplusAutoArmour());
        this.add_hack((WurstplusHack)new WurstplusAuto32k());
        this.add_hack((WurstplusHack)new WurstplusWebfill());
        this.add_hack((WurstplusHack)new WurstplusAutoWeb());
        this.add_hack((WurstplusHack)new WurstplusBedAura());
        this.add_hack((WurstplusHack)new WurstplusOffhand());
        this.add_hack((WurstplusHack)new WurstplusAutoGapple());
        this.add_hack((WurstplusHack)new WurstplusAutoTotem());
        this.add_hack((WurstplusHack)new WurstplusAutoMine());
        this.add_hack((WurstplusHack)new WurstplusXCarry());
        this.add_hack((WurstplusHack)new WurstplusNoSwing());
        this.add_hack((WurstplusHack)new WurstplusPortalGodMode());
        this.add_hack((WurstplusHack)new WurstplusPacketMine());
        this.add_hack((WurstplusHack)new WurstplusEntityMine());
        this.add_hack((WurstplusHack)new WurstplusBuildHeight());
        this.add_hack((WurstplusHack)new WurstplusCoordExploit());
        this.add_hack((WurstplusHack)new WurstplusNoHandshake());
        this.add_hack((WurstplusHack)new WurstplusStrafe());
        this.add_hack((WurstplusHack)new WurstplusStep());
        this.add_hack((WurstplusHack)new WurstplusSprint());
        this.add_hack((WurstplusHack)new WurstPlusAnchor());
        this.add_hack((WurstplusHack)new WurstplusHighlight());
        this.add_hack((WurstplusHack)new WurstplusHoleESP());
        this.add_hack((WurstplusHack)new WurstplusShulkerPreview());
        this.add_hack((WurstplusHack)new WurstplusViewmodleChanger());
        this.add_hack((WurstplusHack)new WurstplusVoidESP());
        this.add_hack((WurstplusHack)new WurstplusAntifog());
        this.add_hack((WurstplusHack)new WurstplusNameTags());
        this.add_hack((WurstplusHack)new WurstplusFuckedDetector());
        this.add_hack((WurstplusHack)new WurstplusTracers());
        this.add_hack((WurstplusHack)new WurstplusSkyColour());
        this.add_hack((WurstplusHack)new WurstplusChams());
        this.add_hack((WurstplusHack)new WurstplusCapes());
        this.add_hack((WurstplusHack)new WurstplusAlwaysNight());
        this.add_hack((WurstplusHack)new WurstplusCityEsp());
        this.add_hack((WurstplusHack)new WurstplusMiddleClickFriends());
        this.add_hack((WurstplusHack)new WurstplusStopEXP());
        this.add_hack((WurstplusHack)new WurstplusAutoReplenish());
        this.add_hack((WurstplusHack)new WurstplusAutoNomadHut());
        this.add_hack((WurstplusHack)new WurstplusFastUtil());
        this.add_hack((WurstplusHack)new WurstplusSpeedmine());
        this.add_hack((WurstplusHack)new WurstplusFakePlayer());
        WurstplusModuleManager.array_hacks.sort(Comparator.comparing((Function<? super WurstplusHack, ? extends Comparable>)WurstplusHack::get_name));
    }
    
    public void add_hack(final WurstplusHack module) {
        WurstplusModuleManager.array_hacks.add(module);
    }
    
    public ArrayList<WurstplusHack> get_array_hacks() {
        return WurstplusModuleManager.array_hacks;
    }
    
    public ArrayList<WurstplusHack> get_array_active_hacks() {
        final ArrayList<WurstplusHack> actived_modules = new ArrayList<WurstplusHack>();
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.is_active()) {
                actived_modules.add(modules);
            }
        }
        return actived_modules;
    }
    
    public Vec3d process(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
    
    public Vec3d get_interpolated_pos(final Entity entity, final double ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(this.process(entity, ticks, ticks, ticks));
    }
    
    public void render(final RenderWorldLastEvent event) {
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("wurstplus");
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("setup");
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179097_i();
        GlStateManager.func_187441_d(1.0f);
        final Vec3d pos = this.get_interpolated_pos((Entity)WurstplusModuleManager.mc.field_71439_g, event.getPartialTicks());
        final WurstplusEventRender event_render = new WurstplusEventRender((Tessellator)RenderHelp.INSTANCE, pos);
        event_render.reset_translation();
        WurstplusModuleManager.mc.field_71424_I.func_76319_b();
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.is_active()) {
                WurstplusModuleManager.mc.field_71424_I.func_76320_a(modules.get_tag());
                modules.render(event_render);
                WurstplusModuleManager.mc.field_71424_I.func_76319_b();
            }
        }
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("release");
        GlStateManager.func_187441_d(1.0f);
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179089_o();
        RenderHelp.release_gl();
        WurstplusModuleManager.mc.field_71424_I.func_76319_b();
        WurstplusModuleManager.mc.field_71424_I.func_76319_b();
    }
    
    public void update() {
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.is_active()) {
                modules.update();
            }
        }
    }
    
    public void render() {
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.is_active()) {
                modules.render();
            }
        }
    }
    
    public void bind(final int event_key) {
        if (event_key == 0) {
            return;
        }
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.get_bind(0) == event_key) {
                modules.toggle();
            }
        }
    }
    
    public WurstplusHack get_module_with_tag(final String tag) {
        WurstplusHack module_requested = null;
        for (final WurstplusHack module : this.get_array_hacks()) {
            if (module.get_tag().equalsIgnoreCase(tag)) {
                module_requested = module;
            }
        }
        return module_requested;
    }
    
    public ArrayList<WurstplusHack> get_modules_with_category(final WurstplusCategory category) {
        final ArrayList<WurstplusHack> module_requesteds = new ArrayList<WurstplusHack>();
        for (final WurstplusHack modules : this.get_array_hacks()) {
            if (modules.get_category().equals((Object)category)) {
                module_requesteds.add(modules);
            }
        }
        return module_requesteds;
    }
    
    static {
        WurstplusModuleManager.array_hacks = new ArrayList<WurstplusHack>();
        WurstplusModuleManager.mc = Minecraft.func_71410_x();
    }
}

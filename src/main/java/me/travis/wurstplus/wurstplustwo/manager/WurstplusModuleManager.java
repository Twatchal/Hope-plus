package me.travis.wurstplus.wurstplustwo.manager;

import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickGUI;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickHUD;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAnnouncer;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAntiRacist;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAutoEz;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusChatMods;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusChatSuffix;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusClearChat;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusVisualRange;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAuto32k;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoArmour;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoCrystal;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoGapple;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoMine;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoTotem;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoWeb;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusBedAura;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusCriticals;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusHoleFill;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusKillAura;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusOffhand;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSelfTrap;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSocks;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSurround;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusTrap;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusVelocity;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusWebfill;
import me.travis.wurstplus.wurstplustwo.hacks.dev.WurstplusFakePlayer;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusBuildHeight;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusCoordExploit;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusEntityMine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusNoHandshake;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusNoSwing;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusPacketMine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusPortalGodMode;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusSpeedmine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusXCarry;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusAutoNomadHut;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusAutoReplenish;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusFastUtil;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusMiddleClickFriends;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusStopEXP;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstPlusAnchor;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusSprint;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStep;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStrafe;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusAlwaysNight;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusAntifog;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusCapes;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusChams;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusCityEsp;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusFuckedDetector;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusHighlight;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusHoleESP;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusNameTags;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusShulkerPreview;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusSkyColour;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusTracers;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusViewmodleChanger;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusVoidESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class WurstplusModuleManager {
    public static ArrayList<WurstplusHack> array_hacks = new ArrayList();
    public static Minecraft mc = Minecraft.func_71410_x();

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
        array_hacks.sort(Comparator.comparing((Function)(Function)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, get_name(), (Lme/travis/wurstplus/wurstplustwo/hacks/WurstplusHack;)Ljava/lang/String;)()));
    }

    public void add_hack(WurstplusHack module) {
        array_hacks.add((Object)module);
    }

    public ArrayList<WurstplusHack> get_array_hacks() {
        return array_hacks;
    }

    public ArrayList<WurstplusHack> get_array_active_hacks() {
        ArrayList actived_modules = new ArrayList();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            actived_modules.add((Object)modules);
        }
        return actived_modules;
    }

    public Vec3d process(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }

    public Vec3d get_interpolated_pos(Entity entity, double ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(this.process(entity, ticks, ticks, ticks));
    }

    public void render(RenderWorldLastEvent event) {
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("wurstplus");
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("setup");
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        GlStateManager.func_179097_i();
        GlStateManager.func_187441_d((float)1.0f);
        Vec3d pos = this.get_interpolated_pos((Entity)WurstplusModuleManager.mc.field_71439_g, event.getPartialTicks());
        WurstplusEventRender event_render = new WurstplusEventRender((Tessellator)RenderHelp.INSTANCE, pos);
        event_render.reset_translation();
        WurstplusModuleManager.mc.field_71424_I.func_76319_b();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            WurstplusModuleManager.mc.field_71424_I.func_76320_a(modules.get_tag());
            modules.render(event_render);
            WurstplusModuleManager.mc.field_71424_I.func_76319_b();
        }
        WurstplusModuleManager.mc.field_71424_I.func_76320_a("release");
        GlStateManager.func_187441_d((float)1.0f);
        GlStateManager.func_179103_j((int)7424);
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
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            modules.update();
        }
    }

    public void render() {
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            modules.render();
        }
    }

    public void bind(int event_key) {
        if (event_key == 0) {
            return;
        }
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (modules.get_bind(0) != event_key) continue;
            modules.toggle();
        }
    }

    public WurstplusHack get_module_with_tag(String tag) {
        WurstplusHack module_requested = null;
        for (WurstplusHack module : this.get_array_hacks()) {
            if (!module.get_tag().equalsIgnoreCase(tag)) continue;
            module_requested = module;
        }
        return module_requested;
    }

    public ArrayList<WurstplusHack> get_modules_with_category(WurstplusCategory category) {
        ArrayList module_requesteds = new ArrayList();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.get_category().equals((Object)category)) continue;
            module_requesteds.add((Object)modules);
        }
        return module_requesteds;
    }
}

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntityRemoved;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAutoEz;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPosManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRotationUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoCrystal
extends WurstplusHack {
    WurstplusSetting debug;
    WurstplusSetting place_crystal;
    WurstplusSetting break_crystal;
    WurstplusSetting break_trys;
    WurstplusSetting anti_weakness;
    WurstplusSetting hit_range;
    WurstplusSetting place_range;
    WurstplusSetting hit_range_wall;
    WurstplusSetting place_delay;
    WurstplusSetting break_delay;
    WurstplusSetting min_player_place;
    WurstplusSetting min_player_break;
    WurstplusSetting max_self_damage;
    WurstplusSetting rotate_mode;
    WurstplusSetting raytrace;
    WurstplusSetting auto_switch;
    WurstplusSetting anti_suicide;
    WurstplusSetting fast_mode;
    WurstplusSetting client_side;
    WurstplusSetting jumpy_mode;
    WurstplusSetting anti_stuck;
    WurstplusSetting endcrystal;
    WurstplusSetting faceplace_mode;
    WurstplusSetting faceplace_mode_damage;
    WurstplusSetting fuck_armor_mode;
    WurstplusSetting fuck_armor_mode_precent;
    WurstplusSetting stop_while_mining;
    WurstplusSetting faceplace_check;
    WurstplusSetting swing;
    WurstplusSetting render_mode;
    WurstplusSetting old_render;
    WurstplusSetting future_render;
    WurstplusSetting top_block;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting a_out;
    WurstplusSetting rainbow_mode;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    WurstplusSetting height;
    WurstplusSetting render_damage;
    WurstplusSetting chain_length;
    private final ConcurrentHashMap<EntityEnderCrystal, Integer> attacked_crystals;
    private final WurstplusTimer remove_visual_timer;
    private final WurstplusTimer chain_timer;
    private EntityPlayer autoez_target;
    private String detail_name;
    private int detail_hp;
    private BlockPos render_block_init;
    private BlockPos render_block_old;
    private double render_damage_value;
    private float yaw;
    private float pitch;
    private boolean already_attacking;
    private boolean place_timeout_flag;
    private boolean is_rotating;
    private boolean did_anything;
    private boolean outline;
    private boolean solid;
    private int chain_step;
    private int current_chain_index;
    private int place_timeout;
    private int break_timeout;
    private int break_delay_counter;
    private int place_delay_counter;
    @EventHandler
    private Listener<WurstplusEventEntityRemoved> on_entity_removed;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    @EventHandler
    private Listener<WurstplusEventMotionUpdate> on_movement;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener;

    public WurstplusAutoCrystal() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.debug = this.create("Debug", "CaDebug", false);
        this.place_crystal = this.create("Place", "CaPlace", true);
        this.break_crystal = this.create("Break", "CaBreak", true);
        this.break_trys = this.create("Break Attempts", "CaBreakAttempts", 1, 1, 6);
        this.anti_weakness = this.create("Anti-Weakness", "CaAntiWeakness", false);
        this.hit_range = this.create("Hit Range", "CaHitRange", 4.79, 1.0, 6.0);
        this.place_range = this.create("Place Range", "CaPlaceRange", 4.47, 1.0, 6.0);
        this.hit_range_wall = this.create("Range Wall", "CaRangeWall", 4.47, 1.0, 6.0);
        this.place_delay = this.create("Place Delay", "CaPlaceDelay", 0, 0, 10);
        this.break_delay = this.create("Break Delay", "CaBreakDelay", 1, 0, 10);
        this.min_player_place = this.create("Min Enemy Place", "CaMinEnemyPlace", 6, 0, 20);
        this.min_player_break = this.create("Min Enemy Break", "CaMinEnemyBreak", 6, 0, 20);
        this.max_self_damage = this.create("Max Self Damage", "CaMaxSelfDamage", 8, 0, 20);
        this.rotate_mode = this.create("Rotate", "CaRotateMode", "Off", this.combobox(new String[]{"Off", "Old", "Const", "Good"}));
        this.raytrace = this.create("Raytrace", "CaRaytrace", true);
        this.auto_switch = this.create("Auto Switch", "CaAutoSwitch", false);
        this.anti_suicide = this.create("Anti Suicide", "CaAntiSuicide", false);
        this.fast_mode = this.create("Fast Mode", "CaSpeed", true);
        this.client_side = this.create("Client Side", "CaClientSide", false);
        this.jumpy_mode = this.create("Jumpy Mode", "CaJumpyMode", false);
        this.anti_stuck = this.create("Anti Stuck", "CaAntiStuck", false);
        this.endcrystal = this.create("1.13 Mode", "CaThirteen", false);
        this.faceplace_mode = this.create("Faceplace Mode", "CaTabbottMode", true);
        this.faceplace_mode_damage = this.create("F Health", "CaTabbottModeHealth", 8, 0, 36);
        this.fuck_armor_mode = this.create("Armor Destroy", "CaArmorDestory", true);
        this.fuck_armor_mode_precent = this.create("Armor %", "CaArmorPercent", 25, 0, 100);
        this.stop_while_mining = this.create("Stop While Mining", "CaStopWhileMining", false);
        this.faceplace_check = this.create("No Sword FP", "CaJumpyFaceMode", false);
        this.swing = this.create("Swing", "CaSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.render_mode = this.create("Render", "CaRenderMode", "Solid", this.combobox(new String[]{"Pretty", "Solid", "Outline", "None"}));
        this.old_render = this.create("Old Render", "CaOldRender", false);
        this.future_render = this.create("Future Render", "CaFutureRender", true);
        this.top_block = this.create("Top Block", "CaTopBlock", false);
        this.r = this.create("R", "CaR", 32, 0, 255);
        this.g = this.create("G", "CaG", 255, 0, 255);
        this.b = this.create("B", "CaB", 255, 0, 255);
        this.a = this.create("A", "CaA", 100, 0, 255);
        this.a_out = this.create("Outline A", "CaOutlineA", 255, 0, 255);
        this.rainbow_mode = this.create("Rainbow", "CaRainbow", false);
        this.sat = this.create("Satiation", "CaSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "CaBrightness", 0.8, 0.0, 1.0);
        this.height = this.create("Height", "CaHeight", 1.0, 0.0, 1.0);
        this.render_damage = this.create("Render Damage", "RenderDamage", true);
        this.chain_length = this.create("Chain Length", "CaChainLength", 3, 1, 6);
        this.attacked_crystals = new ConcurrentHashMap();
        this.remove_visual_timer = new WurstplusTimer();
        this.chain_timer = new WurstplusTimer();
        this.autoez_target = null;
        this.detail_name = null;
        this.detail_hp = 0;
        this.already_attacking = false;
        this.place_timeout_flag = false;
        this.chain_step = 0;
        this.current_chain_index = 0;
        this.on_entity_removed = new Listener(event -> {
            if (event.get_entity() instanceof EntityEnderCrystal) {
                this.attacked_crystals.remove((Object)event.get_entity());
            }
        }
        , new Predicate[0]);
        this.send_listener = new Listener(event -> {
            CPacketPlayer p;
            if (event.get_packet() instanceof CPacketPlayer && this.is_rotating && this.rotate_mode.in("Old")) {
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"Rotating");
                }
                p = (CPacketPlayer)event.get_packet();
                p.field_149476_e = this.yaw;
                p.field_149473_f = this.pitch;
                this.is_rotating = false;
            }
            if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock && this.is_rotating && this.rotate_mode.in("Old")) {
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"Rotating");
                }
                p = (CPacketPlayerTryUseItemOnBlock)event.get_packet();
                p.field_149577_f = this.render_block_init.func_177958_n();
                p.field_149578_g = this.render_block_init.func_177956_o();
                p.field_149584_h = this.render_block_init.func_177952_p();
                this.is_rotating = false;
            }
        }
        , new Predicate[0]);
        this.on_movement = new Listener(event -> {
            if (event.stage == 0 && (this.rotate_mode.in("Good") || this.rotate_mode.in("Const"))) {
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"updating rotation");
                }
                WurstplusPosManager.updatePosition();
                WurstplusRotationUtil.updateRotations();
                this.do_ca();
            }
            if (event.stage == 1 && (this.rotate_mode.in("Good") || this.rotate_mode.in("Const"))) {
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"resetting rotation");
                }
                WurstplusPosManager.restorePosition();
                WurstplusRotationUtil.restoreRotations();
            }
        }
        , new Predicate[0]);
        this.receive_listener = new Listener(event -> {
            SPacketSoundEffect packet;
            if (event.get_packet() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)event.get_packet()).func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB) {
                for (Entity e : WurstplusAutoCrystal.mc.field_71441_e.field_72996_f) {
                    if (!(e instanceof EntityEnderCrystal) || e.func_70011_f(packet.func_149207_d(), packet.func_149211_e(), packet.func_149210_f()) > 6.0) continue;
                    e.func_70106_y();
                }
            }
        }
        , new Predicate[0]);
        this.name = "Auto Crystal";
        this.tag = "AutoCrystal";
        this.description = "kills people (if ur good)";
    }

    public void do_ca() {
        this.did_anything = false;
        if (WurstplusAutoCrystal.mc.field_71439_g == null || WurstplusAutoCrystal.mc.field_71441_e == null) {
            return;
        }
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
        if (this.remove_visual_timer.passed(1000L)) {
            this.remove_visual_timer.reset();
            this.attacked_crystals.clear();
        }
        if (this.check_pause()) {
            return;
        }
        if (this.place_crystal.get_value(true) && this.place_delay_counter > this.place_timeout) {
            this.place_crystal();
        }
        if (this.break_crystal.get_value(true) && this.break_delay_counter > this.break_timeout) {
            this.break_crystal();
        }
        if (!this.did_anything) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            this.autoez_target = null;
            this.is_rotating = false;
        }
        if (this.autoez_target != null) {
            WurstplusAutoEz.add_target((String)this.autoez_target.func_70005_c_());
            this.detail_name = this.autoez_target.func_70005_c_();
            this.detail_hp = Math.round((float)(this.autoez_target.func_110143_aJ() + this.autoez_target.func_110139_bj()));
        }
        if (this.chain_timer.passed(1000L)) {
            this.chain_timer.reset();
            this.chain_step = 0;
        }
        this.render_block_old = this.render_block_init;
        ++this.break_delay_counter;
        ++this.place_delay_counter;
    }

    public void update() {
        if (this.rotate_mode.in("Off") || this.rotate_mode.in("Old")) {
            this.do_ca();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB((float)tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 255);
        this.g.set_value(color_rgb_o >> 8 & 255);
        this.b.set_value(color_rgb_o & 255);
    }

    public EntityEnderCrystal get_best_crystal() {
        double best_damage = 0.0;
        double maximum_damage_self = this.max_self_damage.get_value(1);
        double best_distance = 0.0;
        EntityEnderCrystal best_crystal = null;
        for (Entity c : WurstplusAutoCrystal.mc.field_71441_e.field_72996_f) {
            EntityEnderCrystal crystal;
            if (!(c instanceof EntityEnderCrystal) || WurstplusAutoCrystal.mc.field_71439_g.func_70032_d((Entity)crystal) > (float)(!WurstplusAutoCrystal.mc.field_71439_g.func_70685_l((Entity)(crystal = (EntityEnderCrystal)c)) ? this.hit_range_wall.get_value(1) : this.hit_range.get_value(1)) || !WurstplusAutoCrystal.mc.field_71439_g.func_70685_l((Entity)crystal) && this.raytrace.get_value(true) || crystal.field_70128_L || this.attacked_crystals.containsKey((Object)crystal) && (Integer)this.attacked_crystals.get((Object)crystal) > 5 && this.anti_stuck.get_value(true)) continue;
            for (Entity player : WurstplusAutoCrystal.mc.field_71441_e.field_73010_i) {
                double self_damage;
                if (player == WurstplusAutoCrystal.mc.field_71439_g || !(player instanceof EntityPlayer) || WurstplusFriendUtil.isFriend((String)player.func_70005_c_()) || player.func_70032_d((Entity)WurstplusAutoCrystal.mc.field_71439_g) >= 11.0f) continue;
                EntityPlayer target = (EntityPlayer)player;
                if (target.field_70128_L || target.func_110143_aJ() <= 0.0f) continue;
                boolean no_place = this.faceplace_check.get_value(true) && WurstplusAutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u;
                double minimum_damage = target.func_110143_aJ() < (float)this.faceplace_mode_damage.get_value(1) && this.faceplace_mode.get_value(true) && !no_place || this.get_armor_fucker(target) && !no_place ? 2.0 : (double)this.min_player_break.get_value(1);
                double target_damage = WurstplusCrystalUtil.calculateDamage((EntityEnderCrystal)crystal, (Entity)target);
                if (target_damage < minimum_damage || (self_damage = (double)WurstplusCrystalUtil.calculateDamage((EntityEnderCrystal)crystal, (Entity)WurstplusAutoCrystal.mc.field_71439_g)) > maximum_damage_self || this.anti_suicide.get_value(true) && (double)(WurstplusAutoCrystal.mc.field_71439_g.func_110143_aJ() + WurstplusAutoCrystal.mc.field_71439_g.func_110139_bj()) - self_damage <= 0.5 || target_damage <= best_damage || this.jumpy_mode.get_value(true)) continue;
                this.autoez_target = target;
                best_damage = target_damage;
                best_crystal = crystal;
            }
            if (!this.jumpy_mode.get_value(true) || WurstplusAutoCrystal.mc.field_71439_g.func_70068_e((Entity)crystal) <= best_distance) continue;
            best_distance = WurstplusAutoCrystal.mc.field_71439_g.func_70068_e((Entity)crystal);
            best_crystal = crystal;
        }
        return best_crystal;
    }

    public BlockPos get_best_block() {
        if (this.get_best_crystal() != null && !this.fast_mode.get_value(true)) {
            this.place_timeout_flag = true;
            return null;
        }
        if (this.place_timeout_flag) {
            this.place_timeout_flag = false;
            return null;
        }
        List<WurstplusPair<Double, BlockPos>> damage_blocks = new List<WurstplusPair<Double, BlockPos>>();
        double best_damage = 0.0;
        double maximum_damage_self = this.max_self_damage.get_value(1);
        BlockPos best_block = null;
        List blocks = WurstplusCrystalUtil.possiblePlacePositions((float)this.place_range.get_value(1), (boolean)this.endcrystal.get_value(true), (boolean)true);
        for (Entity player : WurstplusAutoCrystal.mc.field_71441_e.field_73010_i) {
            if (WurstplusFriendUtil.isFriend((String)player.func_70005_c_())) continue;
            for (BlockPos block : blocks) {
                double self_damage;
                if (player == WurstplusAutoCrystal.mc.field_71439_g || !(player instanceof EntityPlayer) || player.func_70032_d((Entity)WurstplusAutoCrystal.mc.field_71439_g) >= 11.0f || !WurstplusBlockUtil.rayTracePlaceCheck((BlockPos)block, (boolean)this.raytrace.get_value(true)) || !WurstplusBlockUtil.canSeeBlock((BlockPos)block) && WurstplusAutoCrystal.mc.field_71439_g.func_70011_f((double)block.func_177958_n(), (double)block.func_177956_o(), (double)block.func_177952_p()) > (double)this.hit_range_wall.get_value(1)) continue;
                EntityPlayer target = (EntityPlayer)player;
                if (target.field_70128_L || target.func_110143_aJ() <= 0.0f) continue;
                boolean no_place = this.faceplace_check.get_value(true) && WurstplusAutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u;
                double minimum_damage = target.func_110143_aJ() < (float)this.faceplace_mode_damage.get_value(1) && this.faceplace_mode.get_value(true) && !no_place || this.get_armor_fucker(target) && !no_place ? 2.0 : (double)this.min_player_place.get_value(1);
                double target_damage = WurstplusCrystalUtil.calculateDamage((double)((double)block.func_177958_n() + 0.5), (double)((double)block.func_177956_o() + 1.0), (double)((double)block.func_177952_p() + 0.5), (Entity)target);
                if (target_damage < minimum_damage || (self_damage = (double)WurstplusCrystalUtil.calculateDamage((double)((double)block.func_177958_n() + 0.5), (double)((double)block.func_177956_o() + 1.0), (double)((double)block.func_177952_p() + 0.5), (Entity)WurstplusAutoCrystal.mc.field_71439_g)) > maximum_damage_self || this.anti_suicide.get_value(true) && (double)(WurstplusAutoCrystal.mc.field_71439_g.func_110143_aJ() + WurstplusAutoCrystal.mc.field_71439_g.func_110139_bj()) - self_damage <= 0.5 || target_damage <= best_damage) continue;
                best_damage = target_damage;
                best_block = block;
                this.autoez_target = target;
            }
        }
        blocks.clear();
        if (this.chain_step == 1) {
            this.current_chain_index = this.chain_length.get_value(1);
        } else if (this.chain_step > 1) {
            --this.current_chain_index;
        }
        this.render_damage_value = best_damage;
        this.render_block_init = best_block;
        damage_blocks = this.sort_best_blocks(damage_blocks);
        return best_block;
    }

    public List<WurstplusPair<Double, BlockPos>> sort_best_blocks(List<WurstplusPair<Double, BlockPos>> list) {
        ArrayList new_list = new ArrayList();
        double damage_cap = 1000.0;
        for (int i = 0; i < list.size(); ++i) {
            double biggest_dam = 0.0;
            WurstplusPair best_pair = null;
            for (WurstplusPair pair : list) {
                if ((Double)pair.getKey() <= biggest_dam || (Double)pair.getKey() >= damage_cap) continue;
                best_pair = pair;
            }
            if (best_pair == null) continue;
            damage_cap = (Double)best_pair.getKey();
            new_list.add((Object)best_pair);
        }
        return new_list;
    }

    public void place_crystal() {
        BlockPos target_block = this.get_best_block();
        if (target_block == null) {
            return;
        }
        this.place_delay_counter = 0;
        this.already_attacking = false;
        boolean offhand_check = false;
        if (WurstplusAutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP) {
            if (WurstplusAutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_185158_cP && this.auto_switch.get_value(true)) {
                if (this.find_crystals_hotbar() == -1) {
                    return;
                }
                WurstplusAutoCrystal.mc.field_71439_g.field_71071_by.field_70461_c = this.find_crystals_hotbar();
                return;
            }
        } else {
            offhand_check = true;
        }
        if (this.debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message((String)"placing");
        }
        ++this.chain_step;
        this.did_anything = true;
        this.rotate_to_pos(target_block);
        this.chain_timer.reset();
        WurstplusBlockUtil.placeCrystalOnBlock((BlockPos)target_block, (EnumHand)(offhand_check ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
    }

    public boolean get_armor_fucker(EntityPlayer p) {
        for (ItemStack stack : p.func_184193_aE()) {
            if (stack == null || stack.func_77973_b() == Items.field_190931_a) {
                return true;
            }
            float armor_percent = (float)(stack.func_77958_k() - stack.func_77952_i()) / (float)stack.func_77958_k() * 100.0f;
            if (!this.fuck_armor_mode.get_value(true) || (float)this.fuck_armor_mode_precent.get_value(1) < armor_percent) continue;
            return true;
        }
        return false;
    }

    public void break_crystal() {
        EntityEnderCrystal crystal = this.get_best_crystal();
        if (crystal == null) {
            return;
        }
        if (this.anti_weakness.get_value(true) && WurstplusAutoCrystal.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
            boolean should_weakness = true;
            if (WurstplusAutoCrystal.mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) && ((PotionEffect)Objects.requireNonNull((Object)WurstplusAutoCrystal.mc.field_71439_g.func_70660_b(MobEffects.field_76420_g))).func_76458_c() == 2) {
                should_weakness = false;
            }
            if (should_weakness) {
                if (!this.already_attacking) {
                    this.already_attacking = true;
                }
                int new_slot = -1;
                for (int i = 0; i < 9; ++i) {
                    ItemStack stack = WurstplusAutoCrystal.mc.field_71439_g.field_71071_by.func_70301_a(i);
                    if (!(stack.func_77973_b() instanceof ItemSword) && !(stack.func_77973_b() instanceof ItemTool)) continue;
                    new_slot = i;
                    WurstplusAutoCrystal.mc.field_71442_b.func_78765_e();
                    break;
                }
                if (new_slot != -1) {
                    WurstplusAutoCrystal.mc.field_71439_g.field_71071_by.field_70461_c = new_slot;
                }
            }
        }
        if (this.debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message((String)"attacking");
        }
        this.did_anything = true;
        this.rotate_to((Entity)crystal);
        for (int i = 0; i < this.break_trys.get_value(1); ++i) {
            WurstplusEntityUtil.attackEntity((Entity)crystal, (boolean)false, (WurstplusSetting)this.swing);
        }
        this.add_attacked_crystal(crystal);
        if (this.client_side.get_value(true) && crystal.func_70089_S()) {
            crystal.func_70106_y();
        }
        this.break_delay_counter = 0;
    }

    public boolean check_pause() {
        if (this.find_crystals_hotbar() == -1 && WurstplusAutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP) {
            return true;
        }
        if (this.stop_while_mining.get_value(true) && WurstplusAutoCrystal.mc.field_71474_y.field_74312_F.func_151470_d() && WurstplusAutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemPickaxe) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("Surround").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("HoleFill").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("Trap").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        return false;
    }

    private int find_crystals_hotbar() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusAutoCrystal.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_185158_cP) continue;
            return i;
        }
        return -1;
    }

    private void add_attacked_crystal(EntityEnderCrystal crystal) {
        if (this.attacked_crystals.containsKey((Object)crystal)) {
            int value = (Integer)this.attacked_crystals.get((Object)crystal);
            this.attacked_crystals.put((Object)crystal, (Object)(value + 1));
        } else {
            this.attacked_crystals.put((Object)crystal, (Object)1);
        }
    }

    public void rotate_to_pos(BlockPos pos) {
        float[] angle = this.rotate_mode.in("Const") ? WurstplusMathUtil.calcAngle((Vec3d)WurstplusAutoCrystal.mc.field_71439_g.func_174824_e(mc.func_184121_ak()), (Vec3d)new Vec3d((double)((float)pos.func_177958_n() + 0.5f), (double)((float)pos.func_177956_o() + 0.5f), (double)((float)pos.func_177952_p() + 0.5f))) : WurstplusMathUtil.calcAngle((Vec3d)WurstplusAutoCrystal.mc.field_71439_g.func_174824_e(mc.func_184121_ak()), (Vec3d)new Vec3d((double)((float)pos.func_177958_n() + 0.5f), (double)((float)pos.func_177956_o() - 0.5f), (double)((float)pos.func_177952_p() + 0.5f)));
        if (this.rotate_mode.in("Off")) {
            this.is_rotating = false;
        }
        if (this.rotate_mode.in("Good") || this.rotate_mode.in("Const")) {
            WurstplusRotationUtil.setPlayerRotations((float)angle[0], (float)angle[1]);
        }
        if (this.rotate_mode.in("Old")) {
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.is_rotating = true;
        }
    }

    public void rotate_to(Entity entity) {
        float[] angle = WurstplusMathUtil.calcAngle((Vec3d)WurstplusAutoCrystal.mc.field_71439_g.func_174824_e(mc.func_184121_ak()), (Vec3d)entity.func_174791_d());
        if (this.rotate_mode.in("Off")) {
            this.is_rotating = false;
        }
        if (this.rotate_mode.in("Good")) {
            WurstplusRotationUtil.setPlayerRotations((float)angle[0], (float)angle[1]);
        }
        if (this.rotate_mode.in("Old") || this.rotate_mode.in("Cont")) {
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.is_rotating = true;
        }
    }

    public void render(WurstplusEventRender event) {
        if (this.render_block_init == null) {
            return;
        }
        if (this.render_mode.in("None")) {
            return;
        }
        if (this.render_mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
        }
        if (this.render_mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
        }
        if (this.render_mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
        }
        this.render_block(this.render_block_init);
        if (this.future_render.get_value(true) && this.render_block_old != null) {
            this.render_block(this.render_block_old);
        }
        if (this.render_damage.get_value(true)) {
            WurstplusRenderUtil.drawText((BlockPos)this.render_block_init, (String)((Object)(Math.floor((double)this.render_damage_value) == this.render_damage_value ? Integer.valueOf((int)((int)this.render_damage_value)) : String.format((String)"%.1f", (Object[])new Object[]{this.render_damage_value})) + ""));
        }
    }

    public void render_block(BlockPos pos) {
        BlockPos render_block = this.top_block.get_value(true) ? pos.func_177984_a() : pos;
        float h = (float)this.height.get_value(1.0);
        if (this.solid) {
            RenderHelp.prepare((String)"quads");
            RenderHelp.draw_cube((BufferBuilder)RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), (float)1.0f, (float)h, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a.get_value(1), (String)"all");
            RenderHelp.release();
        }
        if (this.outline) {
            RenderHelp.prepare((String)"lines");
            RenderHelp.draw_cube_line((BufferBuilder)RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), (float)1.0f, (float)h, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a_out.get_value(1), (String)"all");
            RenderHelp.release();
        }
    }

    public void enable() {
        this.place_timeout = this.place_delay.get_value(1);
        this.break_timeout = this.break_delay.get_value(1);
        this.place_timeout_flag = false;
        this.is_rotating = false;
        this.autoez_target = null;
        this.chain_step = 0;
        this.current_chain_index = 0;
        this.chain_timer.reset();
        this.remove_visual_timer.reset();
        this.detail_name = null;
        this.detail_hp = 20;
    }

    public void disable() {
        this.render_block_init = null;
        this.autoez_target = null;
    }

    public String array_detail() {
        return this.detail_name != null ? this.detail_name + " | " + this.detail_hp : "None";
    }
}

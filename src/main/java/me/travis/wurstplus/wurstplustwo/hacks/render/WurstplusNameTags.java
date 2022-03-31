package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderName;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDamageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class WurstplusNameTags
extends WurstplusHack {
    WurstplusSetting show_armor;
    WurstplusSetting show_health;
    WurstplusSetting show_ping;
    WurstplusSetting show_totems;
    WurstplusSetting show_invis;
    WurstplusSetting reverse;
    WurstplusSetting simplify;
    WurstplusSetting m_scale;
    WurstplusSetting range;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting rainbow_mode;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    @EventHandler
    private final Listener<WurstplusEventRenderName> on_render_name;

    public WurstplusNameTags() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.show_armor = this.create("Armor", "NametagArmor", true);
        this.show_health = this.create("Health", "NametagHealth", true);
        this.show_ping = this.create("Ping", "NametagPing", true);
        this.show_totems = this.create("Totem Count", "NametagTotems", true);
        this.show_invis = this.create("Invis", "NametagInvis", true);
        this.reverse = this.create("Armour Reverse", "NametagReverse", false);
        this.simplify = this.create("Simplify", "NametagSimp", true);
        this.m_scale = this.create("Scale", "NametagScale", 4, 1, 15);
        this.range = this.create("Range", "NametagRange", 75, 1, 250);
        this.r = this.create("R", "NametagR", 255, 0, 255);
        this.g = this.create("G", "NametagG", 255, 0, 255);
        this.b = this.create("B", "NametagB", 255, 0, 255);
        this.a = this.create("A", "NametagA", 0.1, 0.0, 1.0);
        this.rainbow_mode = this.create("Rainbow", "NametagRainbow", false);
        this.sat = this.create("Saturation", "NametagSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "NametagBrightness", 0.8, 0.0, 1.0);
        this.on_render_name = new Listener(event -> {
            event.cancel();
        }
        , new Predicate[0]);
        this.name = "Name Tags";
        this.tag = "NameTags";
        this.description = "MORE DETAILED NAMESSSSS";
    }

    public void render(WurstplusEventRender event) {
        for (EntityPlayer player : WurstplusNameTags.mc.field_71441_e.field_73010_i) {
            if (player == null || player.equals((Object)WurstplusNameTags.mc.field_71439_g) || !player.func_70089_S() || player.func_82150_aj() && !this.show_invis.get_value(true) || WurstplusNameTags.mc.field_71439_g.func_70032_d((Entity)player) >= (float)this.range.get_value(1)) continue;
            double x = this.interpolate(player.field_70142_S, player.field_70165_t, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78725_b;
            double y = this.interpolate(player.field_70137_T, player.field_70163_u, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78726_c;
            double z = this.interpolate(player.field_70136_U, player.field_70161_v, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78723_d;
            this.renderNameTag(player, x, y, z, event.get_partial_ticks());
        }
    }

    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB((float)tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 255);
        this.g.set_value(color_rgb_o >> 8 & 255);
        this.b.set_value(color_rgb_o & 255);
    }

    private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
        double tempY = y;
        tempY += player.func_70093_af() ? 0.5 : 0.7;
        Entity camera = mc.func_175606_aa();
        assert (camera != null);
        double originalPositionX = camera.field_70165_t;
        double originalPositionY = camera.field_70163_u;
        double originalPositionZ = camera.field_70161_v;
        camera.field_70165_t = this.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
        camera.field_70163_u = this.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
        camera.field_70161_v = this.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
        String displayTag = this.getDisplayTag(player);
        double distance = camera.func_70011_f(x + WurstplusNameTags.mc.func_175598_ae().field_78730_l, y + WurstplusNameTags.mc.func_175598_ae().field_78731_m, z + WurstplusNameTags.mc.func_175598_ae().field_78728_n);
        int width = WurstplusNameTags.mc.field_71466_p.func_78256_a(displayTag) / 2;
        double scale = (0.0018 + (double)this.m_scale.get_value(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.func_179094_E();
        RenderHelper.func_74519_b();
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a((float)1.0f, (float)-1500000.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179109_b((float)((float)x), (float)((float)tempY + 1.4f), (float)((float)z));
        GlStateManager.func_179114_b((float)(- WurstplusNameTags.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)WurstplusNameTags.mc.func_175598_ae().field_78732_j, (float)(WurstplusNameTags.mc.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.func_179139_a((double)(- scale), (double)(- scale), (double)scale);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179147_l();
        boolean is_friend = WurstplusFriendUtil.isFriend((String)player.func_70005_c_());
        boolean is_enemy = WurstplusEnemyUtil.isEnemy((String)player.func_70005_c_());
        int red = this.r.get_value(1);
        int green = this.g.get_value(1);
        int blue = this.b.get_value(1);
        if (is_friend) {
            red = 157;
            green = 99;
            blue = 255;
        }
        if (is_enemy) {
            red = 255;
            green = 40;
            blue = 7;
        }
        WurstplusRenderUtil.drawRect((float)((float)(- width - 2) - 1.0f), (float)((float)(- WurstplusNameTags.mc.field_71466_p.field_78288_b + 1) - 1.0f), (float)((float)width + 3.0f), (float)2.5f, (float)red, (float)green, (float)blue, (float)this.a.get_value(1));
        WurstplusRenderUtil.drawRect((float)(- width - 2), (float)(- WurstplusNameTags.mc.field_71466_p.field_78288_b + 1), (float)((float)width + 2.0f), (float)1.5f, (int)1426063360);
        GlStateManager.func_179084_k();
        ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s() && (renderMainHand.func_77973_b() instanceof ItemTool || renderMainHand.func_77973_b() instanceof ItemArmor)) {
            renderMainHand.field_77994_a = 1;
        }
        if (!renderMainHand.field_190928_g && renderMainHand.func_77973_b() != Items.field_190931_a) {
            String stackName = renderMainHand.func_82833_r();
            int stackNameWidth = WurstplusNameTags.mc.field_71466_p.func_78256_a(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.0f);
            WurstplusNameTags.mc.field_71466_p.func_175063_a(stackName, (float)(- stackNameWidth), - this.getBiggestArmorTag(player) + 18.0f, -1);
            GL11.glScalef((float)1.5f, (float)1.5f, (float)1.0f);
            GL11.glPopMatrix();
        }
        if (this.show_armor.get_value(true)) {
            GlStateManager.func_179094_E();
            int xOffset = -8;
            for (ItemStack stack : player.field_71071_by.field_70460_b) {
                if (stack == null) continue;
                xOffset -= 8;
            }
            xOffset -= 8;
            ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
            if (renderOffhand.func_77962_s() && (renderOffhand.func_77973_b() instanceof ItemTool || renderOffhand.func_77973_b() instanceof ItemArmor)) {
                renderOffhand.field_77994_a = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (this.reverse.get_value(true)) {
                for (ItemStack stack2 : player.field_71071_by.field_70460_b) {
                    if (stack2 == null) continue;
                    ItemStack armourStack = stack2.func_77946_l();
                    if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                        armourStack.field_77994_a = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            } else {
                for (int i = player.field_71071_by.field_70460_b.size(); i > 0; --i) {
                    ItemStack stack2 = (ItemStack)player.field_71071_by.field_70460_b.get(i - 1);
                    ItemStack armourStack = stack2.func_77946_l();
                    if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                        armourStack.field_77994_a = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            }
            this.renderItemStack(renderMainHand, xOffset);
            GlStateManager.func_179121_F();
        }
        WurstplusNameTags.mc.field_71466_p.func_175063_a(displayTag, (float)(- width), (float)(- WurstplusNameTags.mc.field_71466_p.field_78288_b - 1), this.getDisplayColour(player));
        camera.field_70165_t = originalPositionX;
        camera.field_70163_u = originalPositionY;
        camera.field_70161_v = originalPositionZ;
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GlStateManager.func_179113_r();
        GlStateManager.func_179136_a((float)1.0f, (float)1500000.0f);
        GlStateManager.func_179121_F();
    }

    private void renderItemStack(ItemStack stack, int x) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179086_m((int)256);
        RenderHelper.func_74519_b();
        WurstplusNameTags.mc.func_175599_af().field_77023_b = -150.0f;
        GlStateManager.func_179118_c();
        GlStateManager.func_179126_j();
        GlStateManager.func_179129_p();
        mc.func_175599_af().func_180450_b(stack, x, -29);
        mc.func_175599_af().func_175030_a(WurstplusNameTags.mc.field_71466_p, stack, x, -29);
        WurstplusNameTags.mc.func_175599_af().field_77023_b = 0.0f;
        RenderHelper.func_74518_a();
        GlStateManager.func_179089_o();
        GlStateManager.func_179141_d();
        GlStateManager.func_179152_a((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.func_179097_i();
        this.renderEnchantmentText(stack, x);
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.func_179121_F();
    }

    private void renderEnchantmentText(ItemStack stack, int x) {
        int enchantmentY = -37;
        NBTTagList enchants = stack.func_77986_q();
        if (enchants.func_74745_c() > 2 && this.simplify.get_value(true)) {
            WurstplusNameTags.mc.field_71466_p.func_175063_a("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        } else {
            for (int index = 0; index < enchants.func_74745_c(); ++index) {
                short id = enchants.func_150305_b(index).func_74765_d("id");
                short level = enchants.func_150305_b(index).func_74765_d("lvl");
                Enchantment enc = Enchantment.func_185262_c((int)id);
                if (enc == null) continue;
                String encName = enc.func_190936_d() ? (Object)TextFormatting.RED + enc.func_77316_c((int)level).substring(11).substring(0, 1).toLowerCase() : enc.func_77316_c((int)level).substring(0, 1).toLowerCase();
                encName = encName + level;
                WurstplusNameTags.mc.field_71466_p.func_175063_a(encName, (float)(x * 2), (float)enchantmentY, -1);
                enchantmentY -= 8;
            }
        }
        if (WurstplusDamageUtil.hasDurability((ItemStack)stack)) {
            int percent = WurstplusDamageUtil.getRoundedDamage((ItemStack)stack);
            String color = percent >= 60 ? this.section_sign() + "a" : (percent >= 25 ? this.section_sign() + "e" : this.section_sign() + "c");
            WurstplusNameTags.mc.field_71466_p.func_175063_a(color + percent + "%", (float)(x * 2), enchantmentY < -62 ? (float)enchantmentY : -62.0f, -1);
        }
    }

    private float getBiggestArmorTag(EntityPlayer player) {
        ItemStack renderOffHand;
        Enchantment enc;
        int index;
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (ItemStack stack : player.field_71071_by.field_70460_b) {
            float encY = 0.0f;
            if (stack != null) {
                NBTTagList enchants = stack.func_77986_q();
                for (index = 0; index < enchants.func_74745_c(); ++index) {
                    short id = enchants.func_150305_b(index).func_74765_d("id");
                    enc = Enchantment.func_185262_c((int)id);
                    if (enc == null) continue;
                    encY += 8.0f;
                    arm = true;
                }
            }
            if (encY <= enchantmentY) continue;
            enchantmentY = encY;
        }
        ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s()) {
            float encY2 = 0.0f;
            NBTTagList enchants2 = renderMainHand.func_77986_q();
            for (int index2 = 0; index2 < enchants2.func_74745_c(); ++index2) {
                short id2 = enchants2.func_150305_b(index2).func_74765_d("id");
                Enchantment enc2 = Enchantment.func_185262_c((int)id2);
                if (enc2 == null) continue;
                encY2 += 8.0f;
                arm = true;
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        if ((renderOffHand = player.func_184592_cb().func_77946_l()).func_77962_s()) {
            float encY = 0.0f;
            NBTTagList enchants = renderOffHand.func_77986_q();
            for (index = 0; index < enchants.func_74745_c(); ++index) {
                short id = enchants.func_150305_b(index).func_74765_d("id");
                enc = Enchantment.func_185262_c((int)id);
                if (enc == null) continue;
                encY += 8.0f;
                arm = true;
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (float)(arm ? 0 : 20) + enchantmentY;
    }

    private String getDisplayTag(EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!this.show_health.get_value(true)) {
            return name;
        }
        float health = player.func_110143_aJ() + player.func_110139_bj();
        if (health <= 0.0f) {
            return name + " - DEAD";
        }
        String color = health > 25.0f ? this.section_sign() + "5" : (health > 20.0f ? this.section_sign() + "a" : (health > 15.0f ? this.section_sign() + "2" : (health > 10.0f ? this.section_sign() + "6" : (health > 5.0f ? this.section_sign() + "c" : this.section_sign() + "4"))));
        String pingStr = "";
        if (this.show_ping.get_value(true)) {
            try {
                int responseTime = ((NetHandlerPlayClient)Objects.requireNonNull((Object)mc.func_147114_u())).func_175102_a(player.func_110124_au()).func_178853_c();
                pingStr = responseTime > 150 ? pingStr + this.section_sign() + "4" : (responseTime > 100 ? pingStr + this.section_sign() + "6" : pingStr + this.section_sign() + "2");
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception responseTime) {
                // empty catch block
            }
        }
        String popStr = " ";
        if (this.show_totems.get_value(true)) {
            try {
                popStr = popStr + (WurstplusTotempop.totem_pop_counter.get((Object)player.func_70005_c_()) == null ? new StringBuilder().append(this.section_sign()).append("70").toString() : new StringBuilder().append(this.section_sign()).append("c -").append(WurstplusTotempop.totem_pop_counter.get((Object)player.func_70005_c_())).toString());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        name = Math.floor((double)health) == (double)health ? name + color + " " + (Object)(health > 0.0f ? Integer.valueOf((int)((int)Math.floor((double)health))) : "dead") : name + color + " " + (Object)(health > 0.0f ? Integer.valueOf((int)((int)health)) : "dead");
        return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
    }

    private int getDisplayColour(EntityPlayer player) {
        int colour = -5592406;
        if (WurstplusFriendUtil.isFriend((String)player.func_70005_c_())) {
            return -11157267;
        }
        if (WurstplusEnemyUtil.isEnemy((String)player.func_70005_c_())) {
            return -49632;
        }
        return colour;
    }

    private double interpolate(double previous, double current, float delta) {
        return previous + (current - previous) * (double)delta;
    }

    public String section_sign() {
        return "\u00a7";
    }
}

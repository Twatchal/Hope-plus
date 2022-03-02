package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.text.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.nbt.*;
import net.minecraft.client.network.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.hacks.chat.*;

public class WurstplusNameTags extends WurstplusHack
{
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
        this.on_render_name = (Listener<WurstplusEventRenderName>)new Listener(event -> event.cancel(), new Predicate[0]);
        this.name = "Name Tags";
        this.tag = "NameTags";
        this.description = "MORE DETAILED NAMESSSSS";
    }
    
    public void render(final WurstplusEventRender event) {
        for (final EntityPlayer player : WurstplusNameTags.mc.field_71441_e.field_73010_i) {
            if (player != null && !player.equals((Object)WurstplusNameTags.mc.field_71439_g) && player.func_70089_S() && (!player.func_82150_aj() || this.show_invis.get_value(true)) && WurstplusNameTags.mc.field_71439_g.func_70032_d((Entity)player) < this.range.get_value(1)) {
                final double x = this.interpolate(player.field_70142_S, player.field_70165_t, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78725_b;
                final double y = this.interpolate(player.field_70137_T, player.field_70163_u, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78726_c;
                final double z = this.interpolate(player.field_70136_U, player.field_70161_v, event.get_partial_ticks()) - WurstplusNameTags.mc.func_175598_ae().field_78723_d;
                this.renderNameTag(player, x, y, z, event.get_partial_ticks());
            }
        }
    }
    
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
    
    private void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.func_70093_af() ? 0.5 : 0.7);
        final Entity camera = WurstplusNameTags.mc.func_175606_aa();
        assert camera != null;
        final double originalPositionX = camera.field_70165_t;
        final double originalPositionY = camera.field_70163_u;
        final double originalPositionZ = camera.field_70161_v;
        camera.field_70165_t = this.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
        camera.field_70163_u = this.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
        camera.field_70161_v = this.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.func_70011_f(x + WurstplusNameTags.mc.func_175598_ae().field_78730_l, y + WurstplusNameTags.mc.func_175598_ae().field_78731_m, z + WurstplusNameTags.mc.func_175598_ae().field_78728_n);
        final int width = WurstplusNameTags.mc.field_71466_p.func_78256_a(displayTag) / 2;
        double scale = (0.0018 + this.m_scale.get_value(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.func_179094_E();
        RenderHelper.func_74519_b();
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(1.0f, -1500000.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179109_b((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.func_179114_b(-WurstplusNameTags.mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(WurstplusNameTags.mc.func_175598_ae().field_78732_j, (WurstplusNameTags.mc.field_71474_y.field_74320_O == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179139_a(-scale, -scale, scale);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179147_l();
        final boolean is_friend = WurstplusFriendUtil.isFriend(player.func_70005_c_());
        final boolean is_enemy = WurstplusEnemyUtil.isEnemy(player.func_70005_c_());
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
        WurstplusRenderUtil.drawRect(-width - 2 - 1.0f, -(WurstplusNameTags.mc.field_71466_p.field_78288_b + 1) - 1.0f, width + 3.0f, 2.5f, (float)red, (float)green, (float)blue, (float)this.a.get_value(1));
        WurstplusRenderUtil.drawRect((float)(-width - 2), (float)(-(WurstplusNameTags.mc.field_71466_p.field_78288_b + 1)), width + 2.0f, 1.5f, 1426063360);
        GlStateManager.func_179084_k();
        final ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s() && (renderMainHand.func_77973_b() instanceof ItemTool || renderMainHand.func_77973_b() instanceof ItemArmor)) {
            renderMainHand.field_77994_a = 1;
        }
        if (!renderMainHand.field_190928_g && renderMainHand.func_77973_b() != Items.field_190931_a) {
            final String stackName = renderMainHand.func_82833_r();
            final int stackNameWidth = WurstplusNameTags.mc.field_71466_p.func_78256_a(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            WurstplusNameTags.mc.field_71466_p.func_175063_a(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 18.0f), -1);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.show_armor.get_value(true)) {
            GlStateManager.func_179094_E();
            int xOffset = -8;
            for (final ItemStack stack : player.field_71071_by.field_70460_b) {
                if (stack != null) {
                    xOffset -= 8;
                }
            }
            xOffset -= 8;
            final ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
            if (renderOffhand.func_77962_s() && (renderOffhand.func_77973_b() instanceof ItemTool || renderOffhand.func_77973_b() instanceof ItemArmor)) {
                renderOffhand.field_77994_a = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (this.reverse.get_value(true)) {
                for (final ItemStack stack2 : player.field_71071_by.field_70460_b) {
                    if (stack2 != null) {
                        final ItemStack armourStack = stack2.func_77946_l();
                        if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                            armourStack.field_77994_a = 1;
                        }
                        this.renderItemStack(armourStack, xOffset);
                        xOffset += 16;
                    }
                }
            }
            else {
                for (int i = player.field_71071_by.field_70460_b.size(); i > 0; --i) {
                    final ItemStack stack2 = (ItemStack)player.field_71071_by.field_70460_b.get(i - 1);
                    final ItemStack armourStack = stack2.func_77946_l();
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
        WurstplusNameTags.mc.field_71466_p.func_175063_a(displayTag, (float)(-width), (float)(-(WurstplusNameTags.mc.field_71466_p.field_78288_b - 1)), this.getDisplayColour(player));
        camera.field_70165_t = originalPositionX;
        camera.field_70163_u = originalPositionY;
        camera.field_70161_v = originalPositionZ;
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GlStateManager.func_179113_r();
        GlStateManager.func_179136_a(1.0f, 1500000.0f);
        GlStateManager.func_179121_F();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179086_m(256);
        RenderHelper.func_74519_b();
        WurstplusNameTags.mc.func_175599_af().field_77023_b = -150.0f;
        GlStateManager.func_179118_c();
        GlStateManager.func_179126_j();
        GlStateManager.func_179129_p();
        WurstplusNameTags.mc.func_175599_af().func_180450_b(stack, x, -29);
        WurstplusNameTags.mc.func_175599_af().func_175030_a(WurstplusNameTags.mc.field_71466_p, stack, x, -29);
        WurstplusNameTags.mc.func_175599_af().field_77023_b = 0.0f;
        RenderHelper.func_74518_a();
        GlStateManager.func_179089_o();
        GlStateManager.func_179141_d();
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        GlStateManager.func_179097_i();
        this.renderEnchantmentText(stack, x);
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        GlStateManager.func_179121_F();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -37;
        final NBTTagList enchants = stack.func_77986_q();
        if (enchants.func_74745_c() > 2 && this.simplify.get_value(true)) {
            WurstplusNameTags.mc.field_71466_p.func_175063_a("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        }
        else {
            for (int index = 0; index < enchants.func_74745_c(); ++index) {
                final short id = enchants.func_150305_b(index).func_74765_d("id");
                final short level = enchants.func_150305_b(index).func_74765_d("lvl");
                final Enchantment enc = Enchantment.func_185262_c((int)id);
                if (enc != null) {
                    String encName = enc.func_190936_d() ? (TextFormatting.RED + enc.func_77316_c((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.func_77316_c((int)level).substring(0, 1).toLowerCase();
                    encName += level;
                    WurstplusNameTags.mc.field_71466_p.func_175063_a(encName, (float)(x * 2), (float)enchantmentY, -1);
                    enchantmentY -= 8;
                }
            }
        }
        if (WurstplusDamageUtil.hasDurability(stack)) {
            final int percent = WurstplusDamageUtil.getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = this.section_sign() + "a";
            }
            else if (percent >= 25) {
                color = this.section_sign() + "e";
            }
            else {
                color = this.section_sign() + "c";
            }
            WurstplusNameTags.mc.field_71466_p.func_175063_a(color + percent + "%", (float)(x * 2), (enchantmentY < -62) ? ((float)enchantmentY) : -62.0f, -1);
        }
    }
    
    private float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (final ItemStack stack : player.field_71071_by.field_70460_b) {
            float encY = 0.0f;
            if (stack != null) {
                final NBTTagList enchants = stack.func_77986_q();
                for (int index = 0; index < enchants.func_74745_c(); ++index) {
                    final short id = enchants.func_150305_b(index).func_74765_d("id");
                    final Enchantment enc = Enchantment.func_185262_c((int)id);
                    if (enc != null) {
                        encY += 8.0f;
                        arm = true;
                    }
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        final ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderMainHand.func_77986_q();
            for (int index2 = 0; index2 < enchants2.func_74745_c(); ++index2) {
                final short id2 = enchants2.func_150305_b(index2).func_74765_d("id");
                final Enchantment enc2 = Enchantment.func_185262_c((int)id2);
                if (enc2 != null) {
                    encY2 += 8.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand = player.func_184592_cb().func_77946_l();
        if (renderOffHand.func_77962_s()) {
            float encY = 0.0f;
            final NBTTagList enchants = renderOffHand.func_77986_q();
            for (int index = 0; index < enchants.func_74745_c(); ++index) {
                final short id = enchants.func_150305_b(index).func_74765_d("id");
                final Enchantment enc = Enchantment.func_185262_c((int)id);
                if (enc != null) {
                    encY += 8.0f;
                    arm = true;
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (arm ? 0 : 20) + enchantmentY;
    }
    
    private String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!this.show_health.get_value(true)) {
            return name;
        }
        final float health = player.func_110143_aJ() + player.func_110139_bj();
        if (health <= 0.0f) {
            return name + " - DEAD";
        }
        String color;
        if (health > 25.0f) {
            color = this.section_sign() + "5";
        }
        else if (health > 20.0f) {
            color = this.section_sign() + "a";
        }
        else if (health > 15.0f) {
            color = this.section_sign() + "2";
        }
        else if (health > 10.0f) {
            color = this.section_sign() + "6";
        }
        else if (health > 5.0f) {
            color = this.section_sign() + "c";
        }
        else {
            color = this.section_sign() + "4";
        }
        String pingStr = "";
        if (this.show_ping.get_value(true)) {
            try {
                final int responseTime = Objects.requireNonNull(WurstplusNameTags.mc.func_147114_u()).func_175102_a(player.func_110124_au()).func_178853_c();
                if (responseTime > 150) {
                    pingStr = pingStr + this.section_sign() + "4";
                }
                else if (responseTime > 100) {
                    pingStr = pingStr + this.section_sign() + "6";
                }
                else {
                    pingStr = pingStr + this.section_sign() + "2";
                }
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception ex) {}
        }
        String popStr = " ";
        if (this.show_totems.get_value(true)) {
            try {
                popStr += ((WurstplusTotempop.totem_pop_counter.get(player.func_70005_c_()) == null) ? (this.section_sign() + "70") : (this.section_sign() + "c -" + WurstplusTotempop.totem_pop_counter.get(player.func_70005_c_())));
            }
            catch (Exception ex2) {}
        }
        if (Math.floor(health) == health) {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)Math.floor(health)) : "dead");
        }
        else {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)health) : "dead");
        }
        return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
    }
    
    private int getDisplayColour(final EntityPlayer player) {
        final int colour = -5592406;
        if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
            return -11157267;
        }
        if (WurstplusEnemyUtil.isEnemy(player.func_70005_c_())) {
            return -49632;
        }
        return colour;
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    public String section_sign() {
        return "§";
    }
}

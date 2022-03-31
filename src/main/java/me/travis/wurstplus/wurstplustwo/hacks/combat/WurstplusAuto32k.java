package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import java.util.Objects;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class WurstplusAuto32k
extends WurstplusHack {
    private BlockPos pos;
    private int hopper_slot;
    private int redstone_slot;
    private int shulker_slot;
    private int ticks_past;
    private int[] rot;
    private boolean setup;
    private boolean place_redstone;
    private boolean dispenser_done;
    WurstplusSetting place_mode;
    WurstplusSetting swing;
    WurstplusSetting delay;
    WurstplusSetting rotate;
    WurstplusSetting debug;

    public WurstplusAuto32k() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.place_mode = this.create("Place Mode", "AutotkPlaceMode", "Auto", this.combobox(new String[]{"Auto", "Looking", "Hopper"}));
        this.swing = this.create("Swing", "AutotkSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.delay = this.create("Delay", "AutotkDelay", 4, 0, 10);
        this.rotate = this.create("Rotate", "Autotkrotate", false);
        this.debug = this.create("Debug", "AutotkDebug", false);
        this.name = "Auto 32k";
        this.tag = "Auto32k";
        this.description = "fastest in the west";
    }

    protected void enable() {
        this.ticks_past = 0;
        this.setup = false;
        this.dispenser_done = false;
        this.place_redstone = false;
        this.hopper_slot = -1;
        int dispenser_slot = -1;
        this.redstone_slot = -1;
        this.shulker_slot = -1;
        int block_slot = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = WurstplusAuto32k.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == Item.func_150898_a((Block)Blocks.field_150438_bZ)) {
                this.hopper_slot = i;
                continue;
            }
            if (item == Item.func_150898_a((Block)Blocks.field_150367_z)) {
                dispenser_slot = i;
                continue;
            }
            if (item == Item.func_150898_a((Block)Blocks.field_150451_bX)) {
                this.redstone_slot = i;
                continue;
            }
            if (item instanceof ItemShulkerBox) {
                this.shulker_slot = i;
                continue;
            }
            if (!(item instanceof ItemBlock)) continue;
            block_slot = i;
        }
        if (!(this.hopper_slot != -1 && dispenser_slot != -1 && this.redstone_slot != -1 && this.shulker_slot != -1 && block_slot != -1 || this.place_mode.in("Hopper"))) {
            WurstplusMessageUtil.send_client_message((String)"missing item");
            this.set_disable();
            return;
        }
        if (this.hopper_slot == -1 || this.shulker_slot == -1) {
            WurstplusMessageUtil.send_client_message((String)"missing item");
            this.set_disable();
            return;
        }
        if (this.place_mode.in("Looking")) {
            int[] arrn;
            RayTraceResult r = WurstplusAuto32k.mc.field_71439_g.func_174822_a(5.0, mc.func_184121_ak());
            this.pos = ((RayTraceResult)Objects.requireNonNull((Object)r)).func_178782_a().func_177984_a();
            double pos_x = (double)this.pos.func_177958_n() - WurstplusAuto32k.mc.field_71439_g.field_70165_t;
            double pos_z = (double)this.pos.func_177952_p() - WurstplusAuto32k.mc.field_71439_g.field_70161_v;
            if (Math.abs((double)pos_x) > Math.abs((double)pos_z)) {
                if (pos_x > 0.0) {
                    int[] arrn2 = new int[2];
                    arrn2[0] = -1;
                    arrn = arrn2;
                    arrn2[1] = 0;
                } else {
                    int[] arrn3 = new int[2];
                    arrn3[0] = 1;
                    arrn = arrn3;
                    arrn3[1] = 0;
                }
            } else if (pos_z > 0.0) {
                int[] arrn4 = new int[2];
                arrn4[0] = 0;
                arrn = arrn4;
                arrn4[1] = -1;
            } else {
                int[] arrn5 = new int[2];
                arrn5[0] = 0;
                arrn = arrn5;
                arrn5[1] = 1;
            }
            this.rot = arrn;
            if (WurstplusBlockUtil.canPlaceBlock((BlockPos)this.pos) && WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos) && WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(this.rot[0], 1, this.rot[1]))) {
                WurstplusBlockUtil.placeBlock((BlockPos)this.pos, (int)block_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                WurstplusBlockUtil.rotatePacket((double)((double)this.pos.func_177982_a(- this.rot[0], 1, - this.rot[1]).func_177958_n() + 0.5), (double)(this.pos.func_177956_o() + 1), (double)((double)this.pos.func_177982_a(- this.rot[0], 1, - this.rot[1]).func_177952_p() + 0.5));
                WurstplusBlockUtil.placeBlock((BlockPos)this.pos.func_177984_a(), (int)dispenser_slot, (boolean)false, (boolean)false, (WurstplusSetting)this.swing);
                WurstplusBlockUtil.openBlock((BlockPos)this.pos.func_177984_a());
                this.setup = true;
            } else {
                WurstplusMessageUtil.send_client_message((String)"unable to place");
                this.set_disable();
            }
        } else if (this.place_mode.in("Auto")) {
            for (int x = -2; x <= 2; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -2; z <= 2; ++z) {
                        int[] arrn;
                        if (Math.abs((int)x) > Math.abs((int)z)) {
                            if (x > 0) {
                                int[] arrn6 = new int[2];
                                arrn6[0] = -1;
                                arrn = arrn6;
                                arrn6[1] = 0;
                            } else {
                                int[] arrn7 = new int[2];
                                arrn7[0] = 1;
                                arrn = arrn7;
                                arrn7[1] = 0;
                            }
                        } else if (z > 0) {
                            int[] arrn8 = new int[2];
                            arrn8[0] = 0;
                            arrn = arrn8;
                            arrn8[1] = -1;
                        } else {
                            int[] arrn9 = new int[2];
                            arrn9[0] = 0;
                            arrn = arrn9;
                            arrn9[1] = 1;
                        }
                        this.rot = arrn;
                        this.pos = WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(x, y, z);
                        if (WurstplusAuto32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(WurstplusAuto32k.mc.field_71439_g.func_174791_d().func_72441_c((double)((float)x - (float)this.rot[0] / 2.0f), (double)y + 0.5, (double)(z + this.rot[1] / 2))) > 4.5 || WurstplusAuto32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(WurstplusAuto32k.mc.field_71439_g.func_174791_d().func_72441_c((double)x + 0.5, (double)y + 2.5, (double)z + 0.5)) > 4.5 || !WurstplusBlockUtil.canPlaceBlock((BlockPos)this.pos) || !WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos) || !WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(this.rot[0], 0, this.rot[1])) || !WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(0, 1, 0)) || !WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(0, 2, 0)) || !WurstplusBlockUtil.isBlockEmpty((BlockPos)this.pos.func_177982_a(this.rot[0], 1, this.rot[1]))) continue;
                        WurstplusBlockUtil.placeBlock((BlockPos)this.pos, (int)block_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                        WurstplusBlockUtil.rotatePacket((double)((double)this.pos.func_177982_a(- this.rot[0], 1, - this.rot[1]).func_177958_n() + 0.5), (double)(this.pos.func_177956_o() + 1), (double)((double)this.pos.func_177982_a(- this.rot[0], 1, - this.rot[1]).func_177952_p() + 0.5));
                        WurstplusBlockUtil.placeBlock((BlockPos)this.pos.func_177984_a(), (int)dispenser_slot, (boolean)false, (boolean)false, (WurstplusSetting)this.swing);
                        WurstplusBlockUtil.openBlock((BlockPos)this.pos.func_177984_a());
                        this.setup = true;
                        return;
                    }
                }
            }
            WurstplusMessageUtil.send_client_message((String)"unable to place");
            this.set_disable();
        } else {
            for (int z = -2; z <= 2; ++z) {
                for (int y = -1; y <= 2; ++y) {
                    for (int x = -2; x <= 2; ++x) {
                        if (z == 0 && y == 0 && x == 0 || z == 0 && y == 1 && x == 0 || !WurstplusBlockUtil.isBlockEmpty((BlockPos)WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y, x)) || WurstplusAuto32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(WurstplusAuto32k.mc.field_71439_g.func_174791_d().func_72441_c((double)z + 0.5, (double)y + 0.5, (double)x + 0.5)) >= 4.5 || !WurstplusBlockUtil.isBlockEmpty((BlockPos)WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y + 1, x)) || WurstplusAuto32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(WurstplusAuto32k.mc.field_71439_g.func_174791_d().func_72441_c((double)z + 0.5, (double)y + 1.5, (double)x + 0.5)) >= 4.5) continue;
                        WurstplusBlockUtil.placeBlock((BlockPos)WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y, x), (int)this.hopper_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                        WurstplusBlockUtil.placeBlock((BlockPos)WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y + 1, x), (int)this.shulker_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                        WurstplusBlockUtil.openBlock((BlockPos)WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y, x));
                        this.pos = WurstplusAuto32k.mc.field_71439_g.func_180425_c().func_177982_a(z, y, x);
                        this.dispenser_done = true;
                        this.place_redstone = true;
                        this.setup = true;
                        return;
                    }
                }
            }
        }
    }

    public void update() {
        if (this.ticks_past > 50 && !(WurstplusAuto32k.mc.field_71462_r instanceof GuiHopper)) {
            WurstplusMessageUtil.send_client_message((String)"inactive too long, disabling");
            this.set_disable();
            return;
        }
        if (this.setup && this.ticks_past > this.delay.get_value(1)) {
            if (!this.dispenser_done) {
                try {
                    WurstplusAuto32k.mc.field_71442_b.func_187098_a(WurstplusAuto32k.mc.field_71439_g.field_71070_bA.field_75152_c, 36 + this.shulker_slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAuto32k.mc.field_71439_g);
                    this.dispenser_done = true;
                    if (this.debug.get_value(true)) {
                        WurstplusMessageUtil.send_client_message((String)"sent item");
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (!this.place_redstone) {
                WurstplusBlockUtil.placeBlock((BlockPos)this.pos.func_177982_a(0, 2, 0), (int)this.redstone_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"placed redstone");
                }
                this.place_redstone = true;
                return;
            }
            if (!this.place_mode.in("Hopper") && WurstplusAuto32k.mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 1, this.rot[1])).func_177230_c() instanceof BlockShulkerBox && WurstplusAuto32k.mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])).func_177230_c() != Blocks.field_150438_bZ && this.place_redstone && this.dispenser_done && !(WurstplusAuto32k.mc.field_71462_r instanceof GuiInventory)) {
                WurstplusBlockUtil.placeBlock((BlockPos)this.pos.func_177982_a(this.rot[0], 0, this.rot[1]), (int)this.hopper_slot, (boolean)this.rotate.get_value(true), (boolean)false, (WurstplusSetting)this.swing);
                WurstplusBlockUtil.openBlock((BlockPos)this.pos.func_177982_a(this.rot[0], 0, this.rot[1]));
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message((String)"in the hopper");
                }
            }
            if (WurstplusAuto32k.mc.field_71462_r instanceof GuiHopper) {
                GuiHopper gui = (GuiHopper)WurstplusAuto32k.mc.field_71462_r;
                for (int slot = 32; slot <= 40; ++slot) {
                    if (EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185302_k, (ItemStack)gui.field_147002_h.func_75139_a(slot).func_75211_c()) <= 5) continue;
                    WurstplusAuto32k.mc.field_71439_g.field_71071_by.field_70461_c = slot - 32;
                    break;
                }
                if (!(((Slot)gui.field_147002_h.field_75151_b.get(0)).func_75211_c().func_77973_b() instanceof ItemAir)) {
                    boolean swapReady = true;
                    if (((GuiContainer)WurstplusAuto32k.mc.field_71462_r).field_147002_h.func_75139_a((int)0).func_75211_c().field_190928_g) {
                        swapReady = false;
                    }
                    if (!((GuiContainer)WurstplusAuto32k.mc.field_71462_r).field_147002_h.func_75139_a((int)(this.shulker_slot + 32)).func_75211_c().field_190928_g) {
                        swapReady = false;
                    }
                    if (swapReady) {
                        WurstplusAuto32k.mc.field_71442_b.func_187098_a(((GuiContainer)WurstplusAuto32k.mc.field_71462_r).field_147002_h.field_75152_c, 0, this.shulker_slot, ClickType.SWAP, (EntityPlayer)WurstplusAuto32k.mc.field_71439_g);
                        this.disable();
                    }
                }
            }
        }
        ++this.ticks_past;
    }
}

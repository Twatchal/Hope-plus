package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.ArrayList;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstPlusAnchor
extends WurstplusHack {
    WurstplusSetting Pitch;
    WurstplusSetting Pull;
    private final ArrayList<BlockPos> holes;
    int holeblocks;
    public static boolean AnchorING;
    private Vec3d Center;
    @EventHandler
    private Listener<WurstplusEventMotionUpdate> OnClientTick;

    public WurstPlusAnchor() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.Pitch = this.create("Pitch", "AnchorPitch", 60, 0, 90);
        this.Pull = this.create("Pull", "AnchorPull", true);
        this.holes = new ArrayList();
        this.Center = Vec3d.field_186680_a;
        this.OnClientTick = new Listener(event -> {
            if (WurstPlusAnchor.mc.field_71439_g.field_70125_A >= (float)this.Pitch.get_value(60)) {
                if (this.isBlockHole(this.getPlayerPos().func_177979_c(1)) || this.isBlockHole(this.getPlayerPos().func_177979_c(2)) || this.isBlockHole(this.getPlayerPos().func_177979_c(3)) || this.isBlockHole(this.getPlayerPos().func_177979_c(4))) {
                    AnchorING = true;
                    if (!this.Pull.get_value(true)) {
                        WurstPlusAnchor.mc.field_71439_g.field_70159_w = 0.0;
                        WurstPlusAnchor.mc.field_71439_g.field_70179_y = 0.0;
                    } else {
                        this.Center = this.GetCenter(WurstPlusAnchor.mc.field_71439_g.field_70165_t, WurstPlusAnchor.mc.field_71439_g.field_70163_u, WurstPlusAnchor.mc.field_71439_g.field_70161_v);
                        double XDiff = Math.abs((double)(this.Center.field_72450_a - WurstPlusAnchor.mc.field_71439_g.field_70165_t));
                        double ZDiff = Math.abs((double)(this.Center.field_72449_c - WurstPlusAnchor.mc.field_71439_g.field_70161_v));
                        if (XDiff <= 0.1 && ZDiff <= 0.1) {
                            this.Center = Vec3d.field_186680_a;
                        } else {
                            double MotionX = this.Center.field_72450_a - WurstPlusAnchor.mc.field_71439_g.field_70165_t;
                            double MotionZ = this.Center.field_72449_c - WurstPlusAnchor.mc.field_71439_g.field_70161_v;
                            WurstPlusAnchor.mc.field_71439_g.field_70159_w = MotionX / 2.0;
                            WurstPlusAnchor.mc.field_71439_g.field_70179_y = MotionZ / 2.0;
                        }
                    }
                } else {
                    AnchorING = false;
                }
            }
        }
        , new Predicate[0]);
        this.name = "Anchor";
        this.tag = "WurstPlusAnchor";
        this.description = "Stops all movement if player is above a hole";
    }

    public boolean isBlockHole(BlockPos blockpos) {
        this.holeblocks = 0;
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 3, 0)).func_177230_c() == Blocks.field_150350_a) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 2, 0)).func_177230_c() == Blocks.field_150350_a) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 0)).func_177230_c() == Blocks.field_150350_a) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150343_Z || WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z || WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z || WurstPlusAnchor.mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h) {
            ++this.holeblocks;
        }
        if (this.holeblocks >= 9) {
            return true;
        }
        return false;
    }

    public Vec3d GetCenter(double posX, double posY, double posZ) {
        double x = Math.floor((double)posX) + 0.5;
        double y = Math.floor((double)posY);
        double z = Math.floor((double)posZ) + 0.5;
        return new Vec3d(x, y, z);
    }

    public void onDisable() {
        AnchorING = false;
        this.holeblocks = 0;
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor((double)WurstPlusAnchor.mc.field_71439_g.field_70165_t), Math.floor((double)WurstPlusAnchor.mc.field_71439_g.field_70163_u), Math.floor((double)WurstPlusAnchor.mc.field_71439_g.field_70161_v));
    }
}

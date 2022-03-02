package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.init.*;

public class WurstPlusAnchor extends WurstplusHack
{
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
        this.holes = new ArrayList<BlockPos>();
        this.Center = Vec3d.field_186680_a;
        this.OnClientTick = (Listener<WurstplusEventMotionUpdate>)new Listener(event -> {
            if (WurstPlusAnchor.mc.field_71439_g.field_70125_A >= this.Pitch.get_value(60)) {
                if (this.isBlockHole(this.getPlayerPos().func_177979_c(1)) || this.isBlockHole(this.getPlayerPos().func_177979_c(2)) || this.isBlockHole(this.getPlayerPos().func_177979_c(3)) || this.isBlockHole(this.getPlayerPos().func_177979_c(4))) {
                    WurstPlusAnchor.AnchorING = true;
                    if (!this.Pull.get_value(true)) {
                        WurstPlusAnchor.mc.field_71439_g.field_70159_w = 0.0;
                        WurstPlusAnchor.mc.field_71439_g.field_70179_y = 0.0;
                    }
                    else {
                        this.Center = this.GetCenter(WurstPlusAnchor.mc.field_71439_g.field_70165_t, WurstPlusAnchor.mc.field_71439_g.field_70163_u, WurstPlusAnchor.mc.field_71439_g.field_70161_v);
                        final double XDiff = Math.abs(this.Center.field_72450_a - WurstPlusAnchor.mc.field_71439_g.field_70165_t);
                        final double ZDiff = Math.abs(this.Center.field_72449_c - WurstPlusAnchor.mc.field_71439_g.field_70161_v);
                        if (XDiff <= 0.1 && ZDiff <= 0.1) {
                            this.Center = Vec3d.field_186680_a;
                        }
                        else {
                            final double MotionX = this.Center.field_72450_a - WurstPlusAnchor.mc.field_71439_g.field_70165_t;
                            final double MotionZ = this.Center.field_72449_c - WurstPlusAnchor.mc.field_71439_g.field_70161_v;
                            WurstPlusAnchor.mc.field_71439_g.field_70159_w = MotionX / 2.0;
                            WurstPlusAnchor.mc.field_71439_g.field_70179_y = MotionZ / 2.0;
                        }
                    }
                }
                else {
                    WurstPlusAnchor.AnchorING = false;
                }
            }
        }, new Predicate[0]);
        this.name = "Anchor";
        this.tag = "WurstPlusAnchor";
        this.description = "Stops all movement if player is above a hole";
    }
    
    public boolean isBlockHole(final BlockPos blockpos) {
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
        return this.holeblocks >= 9;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public void onDisable() {
        WurstPlusAnchor.AnchorING = false;
        this.holeblocks = 0;
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(WurstPlusAnchor.mc.field_71439_g.field_70165_t), Math.floor(WurstPlusAnchor.mc.field_71439_g.field_70163_u), Math.floor(WurstPlusAnchor.mc.field_71439_g.field_70161_v));
    }
}

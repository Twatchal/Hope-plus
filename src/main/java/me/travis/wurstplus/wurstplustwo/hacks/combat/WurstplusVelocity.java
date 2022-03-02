package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import me.travis.wurstplus.wurstplustwo.event.*;
import net.minecraft.network.play.server.*;

public class WurstplusVelocity extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> damage;
    @EventHandler
    private Listener<WurstplusEventEntity.WurstplusEventColision> explosion;
    
    public WurstplusVelocity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.damage = (Listener<WurstplusEventPacket.ReceivePacket>)new Listener(event -> {
            if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
                if (event.get_packet() instanceof SPacketEntityVelocity) {
                    final SPacketEntityVelocity knockback = (SPacketEntityVelocity)event.get_packet();
                    if (knockback.func_149412_c() == WurstplusVelocity.mc.field_71439_g.func_145782_y()) {
                        event.cancel();
                        final SPacketEntityVelocity sPacketEntityVelocity = knockback;
                        sPacketEntityVelocity.field_149415_b *= (int)0.0f;
                        final SPacketEntityVelocity sPacketEntityVelocity2 = knockback;
                        sPacketEntityVelocity2.field_149416_c *= (int)0.0f;
                        final SPacketEntityVelocity sPacketEntityVelocity3 = knockback;
                        sPacketEntityVelocity3.field_149414_d *= (int)0.0f;
                    }
                }
                else if (event.get_packet() instanceof SPacketExplosion) {
                    event.cancel();
                    final SPacketExplosion sPacketExplosion;
                    final SPacketExplosion knockback2 = sPacketExplosion = (SPacketExplosion)event.get_packet();
                    sPacketExplosion.field_149152_f *= 0.0f;
                    final SPacketExplosion sPacketExplosion2 = knockback2;
                    sPacketExplosion2.field_149153_g *= 0.0f;
                    final SPacketExplosion sPacketExplosion3 = knockback2;
                    sPacketExplosion3.field_149159_h *= 0.0f;
                }
            }
        }, new Predicate[0]);
        this.explosion = (Listener<WurstplusEventEntity.WurstplusEventColision>)new Listener(event -> {
            if (event.get_entity() == WurstplusVelocity.mc.field_71439_g) {
                event.cancel();
            }
        }, new Predicate[0]);
        this.name = "Velocity";
        this.tag = "Velocity";
        this.description = "No kockback";
    }
}

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntity;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class WurstplusVelocity
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener(event -> {
        if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
            if (event.get_packet() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity knockback = (SPacketEntityVelocity)event.get_packet();
                if (knockback.func_149412_c() == WurstplusVelocity.mc.field_71439_g.func_145782_y()) {
                    event.cancel();
                    knockback.field_149415_b = (int)((float)knockback.field_149415_b * 0.0f);
                    knockback.field_149416_c = (int)((float)knockback.field_149416_c * 0.0f);
                    knockback.field_149414_d = (int)((float)knockback.field_149414_d * 0.0f);
                }
            } else if (event.get_packet() instanceof SPacketExplosion) {
                event.cancel();
                SPacketExplosion knockback = (SPacketExplosion)event.get_packet();
                knockback.field_149152_f *= 0.0f;
                knockback.field_149153_g *= 0.0f;
                knockback.field_149159_h *= 0.0f;
            }
        }
    }
    , new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener(event -> {
        if (event.get_entity() == WurstplusVelocity.mc.field_71439_g) {
            event.cancel();
        }
    }
    , new Predicate[0]);

    public WurstplusVelocity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Velocity";
        this.tag = "Velocity";
        this.description = "No kockback";
    }
}

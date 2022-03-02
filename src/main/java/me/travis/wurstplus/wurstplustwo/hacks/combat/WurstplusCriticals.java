package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class WurstplusCriticals extends WurstplusHack
{
    WurstplusSetting mode;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;
    
    public WurstplusCriticals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "CriticalsMode", "Packet", this.combobox(new String[] { "Packet", "Jump" }));
        this.listener = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (event.get_packet() instanceof CPacketUseEntity) {
                final CPacketUseEntity event_entity = (CPacketUseEntity)event.get_packet();
                if (event_entity.func_149565_c() == CPacketUseEntity.Action.ATTACK && WurstplusCriticals.mc.field_71439_g.field_70122_E) {
                    if (this.mode.in("Packet")) {
                        WurstplusCriticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.field_71439_g.field_70165_t, WurstplusCriticals.mc.field_71439_g.field_70163_u + 0.10000000149011612, WurstplusCriticals.mc.field_71439_g.field_70161_v, false));
                        WurstplusCriticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.field_71439_g.field_70165_t, WurstplusCriticals.mc.field_71439_g.field_70163_u, WurstplusCriticals.mc.field_71439_g.field_70161_v, false));
                    }
                    else if (this.mode.in("Jump")) {
                        WurstplusCriticals.mc.field_71439_g.func_70664_aZ();
                    }
                }
            }
        }, new Predicate[0]);
        this.name = "Criticals";
        this.tag = "Criticals";
        this.description = "You can hit with criticals when attack.";
    }
    
    public String array_detail() {
        return this.mode.get_current_value();
    }
}

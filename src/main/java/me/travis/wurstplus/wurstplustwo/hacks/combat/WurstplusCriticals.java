package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class WurstplusCriticals
extends WurstplusHack {
    WurstplusSetting mode;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;

    public WurstplusCriticals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "CriticalsMode", "Packet", this.combobox(new String[]{"Packet", "Jump"}));
        this.listener = new Listener(event -> {
            CPacketUseEntity event_entity;
            if (event.get_packet() instanceof CPacketUseEntity && (event_entity = (CPacketUseEntity)event.get_packet()).func_149565_c() == CPacketUseEntity.Action.ATTACK && WurstplusCriticals.mc.field_71439_g.field_70122_E) {
                if (this.mode.in("Packet")) {
                    WurstplusCriticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.field_71439_g.field_70165_t, WurstplusCriticals.mc.field_71439_g.field_70163_u + 0.10000000149011612, WurstplusCriticals.mc.field_71439_g.field_70161_v, false));
                    WurstplusCriticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.field_71439_g.field_70165_t, WurstplusCriticals.mc.field_71439_g.field_70163_u, WurstplusCriticals.mc.field_71439_g.field_70161_v, false));
                } else if (this.mode.in("Jump")) {
                    WurstplusCriticals.mc.field_71439_g.func_70664_aZ();
                }
            }
        }
        , new Predicate[0]);
        this.name = "Criticals";
        this.tag = "Criticals";
        this.description = "You can hit with criticals when attack.";
    }

    public String array_detail() {
        return this.mode.get_current_value();
    }
}

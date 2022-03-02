package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class WurstplusAntiRacist extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting anti_nword;
    WurstplusSetting chanter;
    List<String> chants;
    Random r;
    int tick_delay;
    String[] random_correction;
    CharSequence nigger;
    CharSequence nigga;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;
    
    public WurstplusAntiRacist() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.delay = this.create("Delay", "AntiRacistDelay", 10, 0, 100);
        this.anti_nword = this.create("AntiNword", "AntiRacismAntiNword", true);
        this.chanter = this.create("Chanter", "AntiRacismChanter", false);
        this.chants = new ArrayList<String>();
        this.r = new Random();
        this.random_correction = new String[] { "Yuo jstu got nea nae'd by worst+2", "Wurst+2 just stopped me from saying something racially incorrect!", "<Insert nword word here>", "Im an edgy teenager and saying the nword makes me feel empowered on the internet.", "My mom calls me a late bloomer", "I really do think im funny", "Niger is a great county, I do say so myself", "Mommy and daddy are wrestling in the bedroom again so im going to play minecraft until their done", "How do you open the impact GUI?", "What time does FitMC do his basehunting livestreams?" };
        this.nigger = "nigger";
        this.nigga = "nigga";
        this.listener = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (!(event.get_packet() instanceof CPacketChatMessage)) {
                return;
            }
            if (this.anti_nword.get_value(true)) {
                String message = ((CPacketChatMessage)event.get_packet()).func_149439_c().toLowerCase();
                if (message.contains(this.nigger) || message.contains(this.nigga)) {
                    final String x = Integer.toString((int)WurstplusAntiRacist.mc.field_71439_g.field_70165_t);
                    final String z = Integer.toString((int)WurstplusAntiRacist.mc.field_71439_g.field_70161_v);
                    final String coords = x + " " + z;
                    message = this.random_string(this.random_correction);
                    WurstplusAntiRacist.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage("Hi, im at " + coords + ", come teach me a lesson about racism"));
                }
                ((CPacketChatMessage)event.get_packet()).field_149440_a = message;
            }
        }, new Predicate[0]);
        this.name = "Anti Racist";
        this.tag = "AntiRacist";
        this.description = "i love black squares (circles on the other hand...)";
    }
    
    protected void enable() {
        this.tick_delay = 0;
        this.chants.add("<player> you fucking racist");
        this.chants.add("RIP GEORGE FLOYD");
        this.chants.add("#BLM");
        this.chants.add("#ICANTBREATHE");
        this.chants.add("#NOJUSTICENOPEACE");
        this.chants.add("IM NOT BLACK BUT I STAND WITH YOU");
        this.chants.add("END RACISM, JOIN EMPERIUM");
        this.chants.add("DEFUND THE POLICE");
        this.chants.add("<player> I HOPE YOU POSTED YOUR BLACK SQUARE");
        this.chants.add("RESPECT BLM");
        this.chants.add("IF YOURE NOT WITH US, YOURE AGAINST US");
        this.chants.add("DEREK CHAUVIN WAS A RACIST");
    }
    
    public void update() {
        if (this.chanter.get_value(true)) {
            ++this.tick_delay;
            if (this.tick_delay < this.delay.get_value(1) * 10) {
                return;
            }
            final String s = this.chants.get(this.r.nextInt(this.chants.size()));
            final String name = this.get_random_name();
            if (name.equals(WurstplusAntiRacist.mc.field_71439_g.func_70005_c_())) {
                return;
            }
            WurstplusAntiRacist.mc.field_71439_g.func_71165_d(s.replace("<player>", name));
            this.tick_delay = 0;
        }
    }
    
    public String get_random_name() {
        final List<EntityPlayer> players = (List<EntityPlayer>)WurstplusAntiRacist.mc.field_71441_e.field_73010_i;
        return players.get(this.r.nextInt(players.size())).func_70005_c_();
    }
    
    public String random_string(final String[] list) {
        return list[this.r.nextInt(list.length)];
    }
}

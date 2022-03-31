package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommands;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGameOverlay;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.bus.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class WurstplusEventManager {
    private final Minecraft mc = Minecraft.func_71410_x();

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) {
            return;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.mc.field_71439_g == null) {
            return;
        }
        Wurstplus.get_hack_manager().update();
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        Wurstplus.get_hack_manager().render(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.isCanceled()) {
            return;
        }
        WurstplusEventBus.EVENT_BUS.post((Object)new WurstplusEventGameOverlay(event.getPartialTicks(), new ScaledResolution(this.mc)));
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;
        if (!this.mc.field_71439_g.func_184812_l_() && this.mc.field_71439_g.func_184187_bx() instanceof AbstractHorse) {
            target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
        }
        if (event.getType() == target) {
            Wurstplus.get_hack_manager().render();
            if (!Wurstplus.get_hack_manager().get_module_with_tag("GUI").is_active()) {
                Wurstplus.get_hud_manager().render();
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3042);
            GlStateManager.func_179147_l();
            GL11.glPopMatrix();
            RenderHelp.release_gl();
        }
    }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Wurstplus.get_hack_manager().bind(Keyboard.getEventKey());
        }
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void onChat(ClientChatEvent event) {
        String message = event.getMessage();
        String[] message_args = WurstplusCommandManager.command_list.get_message(event.getMessage());
        boolean true_command = false;
        if (message_args.length > 0) {
            event.setCanceled(true);
            this.mc.field_71456_v.func_146158_b().func_146239_a(event.getMessage());
            for (WurstplusCommand command : WurstplusCommands.get_pure_command_list()) {
                try {
                    if (!WurstplusCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) continue;
                    true_command = command.get_message(WurstplusCommandManager.command_list.get_message(event.getMessage()));
                }
                catch (Exception exception) {}
            }
            if (!true_command && WurstplusCommandManager.command_list.has_prefix(event.getMessage())) {
                WurstplusMessageUtil.send_client_message((String)("Try using " + WurstplusCommandManager.get_prefix() + "help list to see all commands"));
                true_command = false;
            }
        }
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        WurstplusEventBus.EVENT_BUS.post((Object)event);
    }
}

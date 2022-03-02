package me.travis.wurstplus.wurstplustwo.manager;

import net.minecraft.client.*;
import net.minecraftforge.event.entity.living.*;
import me.travis.wurstplus.*;
import me.travis.wurstplus.wurstplustwo.event.*;
import net.minecraft.client.gui.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import net.minecraft.entity.passive.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.travis.turok.draw.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.travis.wurstplus.wurstplustwo.command.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.*;
import net.minecraftforge.client.event.*;

public class WurstplusEventManager
{
    private final Minecraft mc;
    
    public WurstplusEventManager() {
        this.mc = Minecraft.func_71410_x();
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) {
            return;
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.field_71439_g == null) {
            return;
        }
        Wurstplus.get_hack_manager().update();
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        Wurstplus.get_hack_manager().render(event);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
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
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GlStateManager.func_179147_l();
            GL11.glPopMatrix();
            RenderHelp.release_gl();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Wurstplus.get_hack_manager().bind(Keyboard.getEventKey());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onChat(final ClientChatEvent event) {
        final String message = event.getMessage();
        final String[] message_args = WurstplusCommandManager.command_list.get_message(event.getMessage());
        boolean true_command = false;
        if (message_args.length > 0) {
            event.setCanceled(true);
            this.mc.field_71456_v.func_146158_b().func_146239_a(event.getMessage());
            for (final WurstplusCommand command : WurstplusCommands.get_pure_command_list()) {
                try {
                    if (!WurstplusCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) {
                        continue;
                    }
                    true_command = command.get_message(WurstplusCommandManager.command_list.get_message(event.getMessage()));
                }
                catch (Exception ex) {}
            }
            if (!true_command && WurstplusCommandManager.command_list.has_prefix(event.getMessage())) {
                WurstplusMessageUtil.send_client_message("Try using " + WurstplusCommandManager.get_prefix() + "help list to see all commands");
                true_command = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        WurstplusEventBus.EVENT_BUS.post((Object)event);
    }
}

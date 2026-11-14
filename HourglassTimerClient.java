package it.gabriel.hourglasstimer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public final class HourglassTimerClient implements ClientModInitializer {
    private static final long ONE_HOUR_MS = 60L * 60L * 1000L;
    private static KeyMapping timerKey;
    private static long endTimeMs = 0L;
    private static boolean running = false;

    @Override
    public void onInitializeClient() {
        timerKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.hourglasstimer.start",
                GLFW.GLFW_KEY_H,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (timerKey.consumeClick()) {
                endTimeMs = System.currentTimeMillis() + ONE_HOUR_MS;
                running = true;
            }
            if (running && System.currentTimeMillis() >= endTimeMs) {
                running = false;
                endTimeMs = 0L;
                if (client.player != null) {
                    client.player.displayClientMessage(Component.literal("§e[Timer] §cTempo scaduto!"), false);
                }
            }
        });

        HudRenderCallback.EVENT.register((graphics, tickCounter) -> renderTimer(graphics));
    }

    private static void renderTimer(GuiGraphics graphics) {
        if (!running) return;

        long remaining = Math.max(0L, endTimeMs - System.currentTimeMillis());
        long totalSeconds = (remaining + 999L) / 1000L;
        long minutes = totalSeconds / 60L;
        long seconds = totalSeconds % 60L;
        String text = String.format("%02d:%02d", minutes, seconds);

        Minecraft client = Minecraft.getInstance();
        int x = client.getWindow().getGuiScaledWidth() - 68;
        int y = 8;
        int width = 60;
        int height = 20;

        graphics.fill(x, y, x + width, y + height, 0xB0000000);
        graphics.fill(x, y, x + width, y + 2, 0xFF55CC88);
        graphics.drawCenteredString(client.font, text, x + width / 2, y + 6, 0xFFFFFFFF);
    }
}

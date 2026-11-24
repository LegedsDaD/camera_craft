package dev.cameracraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class CameraCraftClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("cameracraft", "camera"));
    private static KeyMapping openCamera;

    @Override
    public void onInitializeClient() {
        openCamera = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.cameracraft.open",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_C,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openCamera.consumeClick()) {
                if (client.player != null && client.gui.screen() == null) {
                    client.gui.setScreen(new CameraScreen());
                }
            }
        });
    }

    static void notifyPlayer(String message) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.sendSystemMessage(Component.literal("[CameraCraft] " + message));
        }
    }
}

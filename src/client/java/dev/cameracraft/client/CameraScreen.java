package dev.cameracraft.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Locale;

/**
 * Client-side camera control panel.
 * Minecraft's native screenshot system (F2) is used for the actual image capture.
 */
public final class CameraScreen extends Screen {
    private static final String[] TIERS = {
            "Pocket • LOW", "Explorer • MEDIUM", "HD • HIGH",
            "Professional • ULTRA", "Cinema • EXTREME"
    };
    private static final String[] LENSES = {
            "Standard 50mm", "Wide 24mm", "Portrait 85mm", "Telephoto 200mm", "Macro"
    };
    private static final String[] FILTERS = {
            "Natural", "Vivid", "Monochrome", "Sepia", "Cinematic"
    };

    private int tier;
    private int lens;
    private int filter;
    private int zoom = 1;
    private boolean timer;

    public CameraScreen() {
        super(Component.literal("CameraCraft"));
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int top = this.height / 2 - 88;

        addRenderableWidget(Button.builder(Component.literal("Camera: " + TIERS[tier]),
                b -> { tier = (tier + 1) % TIERS.length; refresh(); })
                .bounds(center - 105, top, 210, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Lens: " + LENSES[lens]),
                b -> { lens = (lens + 1) % LENSES.length; refresh(); })
                .bounds(center - 105, top + 25, 210, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Filter: " + FILTERS[filter]),
                b -> { filter = (filter + 1) % FILTERS.length; refresh(); })
                .bounds(center - 105, top + 50, 210, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Zoom: " + zoom + "x"),
                b -> { zoom = zoom >= 8 ? 1 : zoom + 1; refresh(); })
                .bounds(center - 105, top + 75, 100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Timer: " + (timer ? "ON" : "OFF")),
                b -> { timer = !timer; refresh(); })
                .bounds(center + 5, top + 75, 100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Close"),
                b -> onClose())
                .bounds(center - 105, top + 111, 100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Capture help"),
                b -> CameraCraftClient.notifyPlayer("Aim your view and press F2 to save a screenshot."))
                .bounds(center + 5, top + 111, 100, 20).build());
    }

    private void refresh() {
        if (minecraft != null) minecraft.setScreen(new CameraScreen(tier, lens, filter, zoom, timer));
    }

    private CameraScreen(int tier, int lens, int filter, int zoom, boolean timer) {
        this();
        this.tier = tier;
        this.lens = lens;
        this.filter = filter;
        this.zoom = zoom;
        this.timer = timer;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        int center = this.width / 2;
        int top = this.height / 2 - 112;

        graphics.fill(center - 125, top, center + 125, top + 244, 0xE5101724);
        graphics.fill(center - 123, top + 2, center + 123, top + 30, 0xFF263B54);
        graphics.drawCenteredString(font, "CAMERACRAFT  /  FIELD CONSOLE", center, top + 11, 0xFFFFFFFF);
        graphics.drawCenteredString(font, "QUALITY PROFILE", center, top + 39, 0xFF8FCBFF);
        graphics.drawCenteredString(font, TIERS[tier], center, top + 52, 0xFFFFFFFF);
        graphics.drawCenteredString(font, "LENS  " + LENSES[lens], center, top + 77, 0xFFE4EAF2);
        graphics.drawCenteredString(font, "FILTER  " + FILTERS[filter], center, top + 91, 0xFFE4EAF2);
        graphics.drawCenteredString(font, "ZOOM  " + zoom + "x     TIMER  " + (timer ? "ARMED" : "OFF"),
                center, top + 105, 0xFFE4EAF2);
        graphics.drawCenteredString(font, "COMPOSITION  •  CENTER / RULE OF THIRDS", center, top + 213, 0xFF8FCBFF);
        graphics.drawCenteredString(font, "Press F2 to capture using Minecraft's screenshot system",
                center, top + 226, 0xFFB8C4D2);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

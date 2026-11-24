package dev.cameracraft.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

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
        int left = center - 125;
        int right = center + 5;
        int top = this.height / 2 - 25;

        addRenderableWidget(Button.builder(Component.literal("Camera: " + TIERS[tier]),
                b -> { tier = (tier + 1) % TIERS.length; refresh(); })
                .bounds(left, top, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Lens: " + LENSES[lens]),
                b -> { lens = (lens + 1) % LENSES.length; refresh(); })
                .bounds(right, top, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Filter: " + FILTERS[filter]),
                b -> { filter = (filter + 1) % FILTERS.length; refresh(); })
                .bounds(left, top + 25, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Zoom: " + zoom + "x"),
                b -> { zoom = zoom >= 8 ? 1 : zoom + 1; refresh(); })
                .bounds(right, top + 25, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Timer: " + (timer ? "ON" : "OFF")),
                b -> { timer = !timer; refresh(); })
                .bounds(left, top + 50, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Capture help"),
                b -> CameraCraftClient.notifyPlayer("Aim your view and press F2 to save a screenshot."))
                .bounds(right, top + 50, 120, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Close"),
                b -> onClose())
                .bounds(center - 60, top + 80, 120, 20).build());
    }

    private void refresh() {
        if (minecraft != null) {
            minecraft.gui.setScreen(new CameraScreen(tier, lens, filter, zoom, timer));
        }
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
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // Let Screen extract the vanilla background and widgets first.
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        // Draw the title in the clear space above the controls.
        int center = this.width / 2;
        int top = this.height / 2 - 25;
        graphics.fill(center - 135, top - 78, center + 135, top - 35, 0xE5101724);
        graphics.centeredText(font, "CAMERACRAFT  /  FIELD CONSOLE",
                center, top - 67, 0xFFFFFFFF);
        graphics.centeredText(font, TIERS[tier] + "  •  " + LENSES[lens] + "  •  " + FILTERS[filter],
                center, top - 49, 0xFF8FCBFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

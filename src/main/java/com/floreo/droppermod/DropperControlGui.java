package com.floreo.droppermod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class DropperControlGui extends Screen {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING = 10;

    private int selectedDropper = 1; // 1 oder 2
    private int selectedOutput = 1; // 1-9

    public DropperControlGui() {
        super(Text.literal("Dropper Output Controller"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Dropper Selection (1 oder 2)
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Dropper 1"),
            button -> this.selectedDropper = 1
        ).dimensions(centerX - 100, centerY - 80, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Dropper 2"),
            button -> this.selectedDropper = 2
        ).dimensions(centerX + 20, centerY - 80, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        // Output Items 1-9
        int startX = centerX - 120;
        int startY = centerY - 30;

        for (int i = 1; i <= 9; i++) {
            final int itemNumber = i;
            int row = (i - 1) / 3;
            int col = (i - 1) % 3;

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(String.valueOf(i)),
                button -> this.selectedOutput = itemNumber
            ).dimensions(
                startX + col * 50,
                startY + row * 30,
                40,
                BUTTON_HEIGHT
            ).build());
        }

        // Confirm Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Set Output"),
            button -> this.setOutput()
        ).dimensions(centerX - 50, centerY + 80, 100, BUTTON_HEIGHT).build());

        // Close Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Close"),
            button -> this.close()
        ).dimensions(centerX - 50, centerY + 110, 100, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Title
        context.drawCenteredTextWithShadow(
            this.textRenderer,
            "Dropper Output Controller",
            centerX,
            centerY - 120,
            0xFFFFFF
        );

        // Status
        context.drawCenteredTextWithShadow(
            this.textRenderer,
            "Dropper: " + this.selectedDropper,
            centerX,
            centerY - 60,
            0xFFFF00
        );

        context.drawCenteredTextWithShadow(
            this.textRenderer,
            "Output Item: " + this.selectedOutput,
            centerX,
            centerY + 50,
            0x00FF00
        );
    }

    private void setOutput() {
        if (this.client != null && this.client.player != null) {
            this.client.player.sendMessage(
                Text.literal("§6Dropper " + this.selectedDropper + " -> Item " + this.selectedOutput),
                false
            );
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }
}

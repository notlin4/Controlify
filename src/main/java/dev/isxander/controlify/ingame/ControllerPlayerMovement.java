package dev.isxander.controlify.ingame;

import dev.isxander.controlify.controller.Controller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;

public class ControllerPlayerMovement extends Input {
    private final Controller controller;

    public ControllerPlayerMovement(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void tick(boolean slowDown, float f) {
        if (Minecraft.getInstance().screen != null) {
            this.up = false;
            this.down = false;
            this.left = false;
            this.right = false;
            this.leftImpulse = 0;
            this.forwardImpulse = 0;
            this.jumping = false;
            this.shiftKeyDown = false;
            return;
        }

        var axes = controller.state().axes();

        this.up = axes.leftStickY() < 0;
        this.down = axes.leftStickY() > 0;
        this.left = axes.leftStickX() < 0;
        this.right = axes.leftStickX() > 0;
        this.leftImpulse = -axes.leftStickX();
        this.forwardImpulse = -axes.leftStickY();

        if (slowDown) {
            this.leftImpulse *= f;
            this.forwardImpulse *= f;
        }

        var bindings = controller.bindings();

        this.jumping = bindings.JUMP.held();
        if (bindings.SNEAK.justPressed()) {
            this.shiftKeyDown = !this.shiftKeyDown;
        }
    }
}

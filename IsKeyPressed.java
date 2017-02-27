import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class IsKeyPressed {

    private static boolean wPressed, sPressed, aPressed, dPressed;

    public static boolean isWPressed() {
        synchronized (IsKeyPressed.class) {
            return wPressed;
        }
    }

    public static boolean isSPressed() {
        synchronized (IsKeyPressed.class) {
            return sPressed;
        }
    }

    public static boolean isAPressed() {
        synchronized (IsKeyPressed.class) {
            return aPressed;
        }
    }

    public static boolean isDPressed() {
        synchronized (IsKeyPressed.class) {
            return dPressed;
        }
    }

    public static void detectKeyPress() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                synchronized (IsKeyPressed.class) {
                    switch (keyEvent.getID()) {

                        case KeyEvent.KEY_PRESSED:
                            if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
                                wPressed = true;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                                sPressed = true;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = true;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
                                dPressed = true;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
                                wPressed = false;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                                sPressed = false;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = false;
                            } else if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
                                dPressed = false;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }
}

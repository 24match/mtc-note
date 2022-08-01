package factoryMethod.factory;

import factoryMethod.buttons.Button;
import factoryMethod.buttons.WindowsButton;

public class WindowsDialog extends Dialog {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}

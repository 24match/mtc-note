package factoryMethod.factory;

import factoryMethod.buttons.Button;
import factoryMethod.buttons.HtmlButton;

public class HtmlDialog extends Dialog {
    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}

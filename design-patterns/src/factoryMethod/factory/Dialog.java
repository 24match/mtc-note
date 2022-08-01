package factoryMethod.factory;

import factoryMethod.buttons.Button;

public abstract class Dialog {

    public void renderWindow() {

        Button okButton = createButton();
        okButton.render();
    }

    public abstract Button createButton();

}

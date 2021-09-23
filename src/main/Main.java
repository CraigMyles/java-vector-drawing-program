package main;

import guiDelegate.GuiDelegate;
import model.Model;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        //Pass model obj into delegate so it can observe, display and update the model.
        new GuiDelegate(model);

    }
}

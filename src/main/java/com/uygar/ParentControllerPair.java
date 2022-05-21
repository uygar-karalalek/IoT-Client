package com.uygar;

import javafx.scene.Parent;

public class ParentControllerPair<PARENT extends Parent, CONTROLLER> {
    private PARENT parent;
    private CONTROLLER controller;

    public ParentControllerPair(PARENT parent, CONTROLLER controller) {
        this.parent = parent;
        this.controller = controller;
    }

    public PARENT getParent() {
        return parent;
    }

    public void setParent(PARENT parent) {
        this.parent = parent;
    }

    public CONTROLLER getController() {
        return controller;
    }

    public void setController(CONTROLLER controller) {
        this.controller = controller;
    }
}

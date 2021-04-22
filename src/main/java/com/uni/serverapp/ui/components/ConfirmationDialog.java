package com.uni.serverapp.ui.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ConfirmationDialog extends Composite<Dialog>  {
    private final Dialog dialog = getContent();
    private final Span message = new Span();

    private Runnable confirmAction;

    public ConfirmationDialog() {
        Button confirm = new Button("Confirm",
                e -> {
                    confirmAction.run();
                    dialog.close();
                });
        Button cancel = new Button("Cancel",
                e -> {
                    dialog.close();
                });
        dialog.add(message, new HorizontalLayout(confirm, cancel));
    }

    public void open(Runnable confirmAction, String message) {
        this.confirmAction = confirmAction;
        this.message.setText(message);
        dialog.open();
    }
}

package me.soso.controller.base;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

@FXMLController(value = "/fxml/base/Dialog.fxml", title = "对话窗")
public class DialogController {

    @FXML
    private JFXDialog dialog;

    @FXML
    private Label dialogTitleLabel;

    @FXML
    private Label dialogMsgLabel;

    @FXML
    private JFXButton acceptButton;

    public void setDialogContainer(StackPane dialogContainer) {
        dialog.setDialogContainer(dialogContainer);
        acceptButton.setOnAction(action -> dialog.close());
    }

    public void showDialog(String title, String msg) {
        dialogTitleLabel.setText(title);
        dialogMsgLabel.setText(msg);
        dialog.show();
    }

}

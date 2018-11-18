package me.soso.controller.base;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.soso.config.ApplicationConfig;
import me.soso.config.module.Module;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@FXMLController(value = "/fxml/base/SideMenu.fxml")
public class SideMenuController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private JFXListView<Label> sideList;

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException exc) {
                            exc.printStackTrace();
                        } catch (FlowException exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            }).start();
        });

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        try {
            List<Module> modules = ApplicationConfig.shareInstance().getModules();
            ObservableList<Label> labelList = FXCollections.observableArrayList();
            for (Module module : modules) {
                Label label = new Label();
                label.setId(module.getId());
                label.setText(module.getTitle());
                labelList.add(label);
                contentFlow.withGlobalLink(module.getId(), module.getControllerClass());
            }
            sideList.setItems(labelList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setContext(ViewFlowContext context) {
        this.context = context;
    }

}

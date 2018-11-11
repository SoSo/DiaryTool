package me.soso;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.soso.controller.MainController;

public class MainApp extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    final private double applicationWidth = 600;
    final private double applicationHeight = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Flow flow = new Flow(MainController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", primaryStage);
        flow.createHandler(flowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));

        double width = applicationWidth;
        double height = applicationHeight;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        }catch (Exception e){ }

        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(this.getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
                this.getClass().getResource("/css/jfoenix-design.css").toExternalForm(),
                this.getClass().getResource("/css/diary-tool.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

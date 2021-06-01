/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

/**
 *
 * @author PAQUIER Frédéric
 */
public class DessinCanvas extends Pane {
    
    private Board main;  // Le canvas doit aussi avoir accès au pain pour afficher les ordres

    private Canvas realCanvas;
    //private RectangleHV asRect;

    public DessinCanvas(Board main) {
        
        this.main = main;
        this.realCanvas = new Canvas(this.getWidth(), this.getHeight()); //toute cette partie permet d'ajuster la taille du canvas à la fenêtre.
        this.getChildren().add(this.realCanvas);
        this.realCanvas.heightProperty().bind(this.heightProperty());
        this.realCanvas.heightProperty().addListener((o) -> {
            this.redrawAll();
        });
        this.realCanvas.widthProperty().bind(this.widthProperty());
        this.realCanvas.widthProperty().addListener((o) -> {
            this.redrawAll();
        });
        this.realCanvas.setOnMouseClicked((t) -> { //l'évèneemnt est géré par le controleur
        Controleur control = this.main.getControleur();
        control.clicDansZoneDessin(t);
         });
    }

    public void redrawAll() {
        GraphicsContext context = this.realCanvas.getGraphicsContext2D();

        context.setFill(Color.WHITE);

        context.fillRect(0, 0, this.realCanvas.getWidth(), this.realCanvas.getHeight());
       Groupe model = this.main.getModel();
        model.dessine(context);//pour l'afficher
        List<Figure> select = this.main.getControleur().getSelection(); //change la couleur de la selection= plus visuel
           for (Figure f : select) {
               f.dessineSelection(context);
          }
       
    }
}

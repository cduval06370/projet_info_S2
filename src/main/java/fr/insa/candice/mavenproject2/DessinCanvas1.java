/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author PAQUIER Frédéric
 */
public class DessinCanvas1 extends Canvas {
    public DessinCanvas1 (double w, double h ){
        super(w,h);
        GraphicsContext context =this.getGraphicsContext2D();
        context.setFill(Color.GOLDENROD);
        context.fillRect(0,0, this.getWidth(), this.getHeight());
    }
    
    
}

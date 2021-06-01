/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public abstract class Figure {

    public static Color COULEUR_SELECTION = Color.PURPLE; // rend visuel la selection

    /**
     * null si aucun groupe
     */
    private Groupe groupe;

    public Groupe getGroupe() {
        return groupe;
    }

    void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public abstract double maxX();
    public abstract double minX();
    public abstract double maxY();
    public abstract double minY();
    public abstract double distancePoint(Point p);
    public abstract void dessine(GraphicsContext context);// la figure va s'afficher dans le graphic context.
    public abstract void dessineSelection(GraphicsContext context);
    //public abstract void changeCouleur(Color value);
   
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.scene.shape.*;

/**
 *
 * @author PAQUIER Frédéric
 */

public abstract class Rectangle extends Groupe {
  private double longueur, largeur;
  private double pxr, pyr;

  public Rectangle(int longueur, int largeur) {
    this.longueur = longueur;
    this.largeur = largeur;
  }
}

  
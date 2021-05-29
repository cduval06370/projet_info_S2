/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.candice.mavenproject2;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
//import recup.Lire;

/**
 *
 * @author francois
 */
public class Groupe extends Figure {

    private List<Figure> contient;
    private List<Point> contientp;

    public List<Figure> getContient() {
        return contient;
    }

    public List<Point> getContientP() {
        return contientp;
    }

    public Groupe() {
        this.contient = new ArrayList<Figure>();
        this.contientp = new ArrayList<Point>();

    }

    public void add(Figure f) {
        if (f.getGroupe() != this) {
            if (f.getGroupe() != null) {
                throw new Error("figure déja dans un autre groupe");
            }
            this.getContient().add(f);
            f.setGroupe(this);
        }
    }

    public void addP(Point p) {
        if (p.getGroupe() != this) {
            if (p.getGroupe() != null) {
                throw new Error("point déja dans un autre groupe");
            }
            this.getContientP().add(p);
            p.setGroupe(this);
        }
    }

    public void remove(Figure f) {
        if (f.getGroupe() != this) {
            throw new Error("la figure n'est pas dans le groupe");
        }
        this.getContient().remove(f);
        f.setGroupe(null);
    }

    public void removeP(Point p) {
        if (p.getGroupe() != this) {
            throw new Error("la figure n'est pas dans le groupe");
        }
        this.getContientP().remove(p);
        p.setGroupe(null);
    }

    public void removeAll(List<Figure> lf) {
        for (Figure f : lf) {
            this.remove(f);
        }
    }

    public void clear() {
        List<Figure> toRemove = new ArrayList<>(this.getContient());
        this.removeAll(toRemove);
    }

    public int size() {
        return this.getContient().size();
    }

    /**
     * retourne la figure contenue dans le groupe la plus proche du point et au
     * maximum à distMax du point; retourne null si aucune figure n'est à une
     * distance plus faible que distMax;
     */
    public Figure plusProche(Point p, double distMax) {
        if (this.getContient().isEmpty()) {
            return null;
        } else {
            Figure fmin = this.getContient().get(0);
            double min = fmin.distancePoint(p);
            for (int i = 1; i < this.getContient().size(); i++) {
                Figure fcur = this.getContient().get(i);
                double cur = fcur.distancePoint(p);
                if (cur < min) {
                    min = cur;
                    fmin = fcur;
                }
            }
            if (min <= distMax) {
                return fmin;
            } else {
                return null;
            }
        }
    }

    public Point PplusProche(Point pcl, double distMax) {
        if (this.getContientP().isEmpty()) {
            return null;
        } else {
            Point pmin = this.getContientP().get(0);
            double min = pmin.distancePoint(pcl);
            for (int i = 1; i < this.getContientP().size(); i++) {
                Point pcur = this.getContientP().get(i);
                double cur = pcur.distancePoint(pcl);
                if (cur < min) {
                    min = cur;
                    pmin = pcur;
                }
            }
            if (min <= distMax) {
                return pmin;
            } else {
                return null;
            }
        }
    }

    /**
     * crée un sous groupe avec les figures contenues dans lf. . les figures de
     * lf doivent appartenir au groupe this. . un nouveau groupe sg est créé .
     * les figures de lf sont retirées de this . les figures de lf sont ajoutées
     * au nouveau groupe sg . le groupe sg est ajouté au groupe this
     *
     * @return le sous-groupe créé.
     */
    public Groupe sousGroupe(List<Figure> lf) {
        // verifie que les figures font actuellement partie du groupe
        // et les enleve du groupe
        for (int i = 0; i < lf.size(); i++) {
            Figure f = lf.get(i);
            if (f.getGroupe() != this) {
                throw new Error(f + " n'appartient pas au groupe " + this);
            }
            this.getContient().remove(f);
            f.setGroupe(null);
        }
        Groupe sg = new Groupe();
        for (int i = 0; i < lf.size(); i++) {
            sg.add(lf.get(i));
        }
        this.add(sg);
        return sg;
    }

    public static String indente(String toIndente, String prefix) {
        return prefix + toIndente.replaceAll("\n", "\n" + prefix);
    }

    @Override
    public String toString() {
        String res = "Groupe {\n";
        for (int i = 0; i < this.getContient().size(); i++) {
            res = res + indente(this.getContient().get(i).toString(), "  ") + "\n";
        }
        return res + "}";
    }

    public static Groupe groupeTest() {   // a enlever mais donne une facon de poser le terrain
        Point p1 = new Point(10, 10);
        Point p2 = new Point(100, 10);
        Point p3 = new Point(100, 100);

        Point p6 = new Point(500, 500, Color.AQUAMARINE);
        Segment s1 = new Segment(p1, p2);
        Segment s2 = new Segment(p2, p3);
        Segment s3 = new Segment(p3, p1);
        Groupe triangle = new Groupe();
        triangle.add(s1);
        triangle.add(s2);
        triangle.add(s3);
        Groupe res = new Groupe();
        res.add(triangle);

        return res;
    }
   

    public Point choisiPoint() {
        List<Point> lp = new ArrayList<>();
        System.out.println("liste des points disponibles : ");
        int nbr = 0;
        for (int i = 0; i < this.getContient().size(); i++) {
            Figure f = this.getContient().get(i);
            if (f instanceof Point) {
                nbr++;
                lp.add((Point) f);
                System.out.println(nbr + ") " + f);
            }
        }
        if (nbr == 0) {
            System.out.println("Aucun point disponible");
            return null;
        } else {
            int rep = -1;
            while (rep < 0 || rep > nbr) {
                System.out.println("votre choix (0 pour annuler) : ");
                rep = Lire.i();
            }
            if (rep == 0) {
                return null;
            } else {
                return lp.get(rep - 1);
            }
        }
    }

    public List<Figure> choisiFigures() {
        List<Figure> res = new ArrayList<>();
        int rep = -1;
        while (rep != 0) {
            System.out.println("liste des figures disponibles : ");
            for (int i = 0; i < this.getContient().size(); i++) {
                System.out.println((i + 1) + ") " + this.getContient().get(i));
            }
            System.out.println("votre choix (0 pour finir) : ");
            rep = Lire.i();
            if (rep > 0 && rep <= this.getContient().size()) {
                Figure f = this.getContient().get(rep - 1);
                if (res.contains(f)) {
                    System.out.println("déja selectionnée !!");
                } else {
                    res.add(f);
                }
                System.out.println(res.size() + " figure(s) séléctionnée(s)");
            }
        }
        return res;
    }

    public void menuTexte() {
        int rep = -1;
        while (rep != 0) {
            System.out.println("1) afficher le groupe");
            System.out.println("2) ajouter un point");
            System.out.println("3) ajouter un segment avec deux nouveaux points");
            System.out.println("4) ajouter un segment sur deux points existants");
            System.out.println("5) créer un sous-groupe");
            System.out.println("6) afficher le rectangle englobant");
            System.out.println("7) calculer la distance à un point");
            System.out.println("8) retirer des figures du groupe");
            System.out.println("0) quitter");
            System.out.println("votre choix : ");
            rep = Lire.i();
            if (rep == 1) {
                System.out.println(this);
            } else if (rep == 2) {
                Point np = Point.demandePoint();
                this.add(np);
            } else if (rep == 3) {
                Segment ns = Segment.demandeSegment();
                this.add(ns);
            } else if (rep == 4) {
                System.out.println("choisissez le début du segment");
                Point deb = this.choisiPoint();
                if (deb != null) {
                    System.out.println("choisissez la fin du segment");
                    Point fin = this.choisiPoint();
                    Segment ns = new Segment(deb, fin);
                    this.add(ns);
                }
            } else if (rep == 5) {
                List<Figure> select = this.choisiFigures();
                this.sousGroupe(select);
            } else if (rep == 6) {
                System.out.println("maxX = " + this.maxX() + " ; "
                        + "minX = " + this.minX() + "\n"
                        + "maxY = " + this.maxY() + " ; "
                        + "minY = " + this.minY() + "\n");
            } else if (rep == 7) {
                System.out.println("entrez un point :");
                Point p = Point.demandePoint();
                System.out.println("distance : " + this.distancePoint(p));
            } else if (rep == 8) {
                List<Figure> select = this.choisiFigures();
                this.removeAll(select);
            }
        }
    }

    public static void test1() {
        System.out.println("groupe test : \n" + Groupe.groupeTest());
    }

    public static void testMenu() {
        Groupe g = groupeTest();
        g.menuTexte();
    }

    public static void exempleProblemeSauvegarde() {
        Point p11 = new Point(1, 1);
        Point p12 = new Point(2, 2);
        Point p13 = new Point(2, 2);
        Point p14 = new Point(3, 3);
        Segment s11 = new Segment(p11, p12);
        Segment s12 = new Segment(p13, p14);
        Groupe gr1 = new Groupe();
        gr1.add(s11);
        gr1.add(s12);
        Point p21 = new Point(1, 1);
        Point p22 = new Point(2, 2);
        Point p24 = new Point(3, 3);
        Segment s21 = new Segment(p21, p22);
        Segment s22 = new Segment(p22, p24);
        Groupe gr2 = new Groupe();
        gr2.add(s21);
        gr2.add(s22);
        gr2.add(gr1);
        System.out.println("Groupe 1 : " + gr1);
        System.out.println("Groupe 2 : " + gr2);
        try {
            gr1.sauvegarde(new File("groupe1.txt"));
            gr2.sauvegarde(new File("groupe2.txt"));
        } catch (IOException ex) {
            throw new Error("probleme : " + ex.getMessage());
        }
    }

    public static void testLecture() {
        try {
            Figure lue = Figure.lecture(new File("groupe2.txt"));
            System.out.println("fig lue : " + lue);
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    // public static void main(String[] args) {
//        test1();
//        testMenu();
//        exempleProblemeSauvegarde();
    //     testLecture();
    // }
    /**
     * abscice maximale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double maxX() {
        if (this.getContient().isEmpty()) {
            return 0;
        } else {
            double max = this.getContient().get(0).maxX();
            for (int i = 1; i < this.getContient().size(); i++) {
                double cur = this.getContient().get(i).maxX();
                if (cur > max) {
                    max = cur;
                }
            }
            return max;
        }
    }

    /**
     * abscice minimale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double minX() {
        if (this.getContient().isEmpty()) {
            return 0;
        } else {
            double min = this.getContient().get(0).minX();
            for (int i = 1; i < this.getContient().size(); i++) {
                double cur = this.getContient().get(i).minX();
                if (cur < min) {
                    min = cur;
                }
            }
            return min;
        }
    }

    /**
     * ordonnee maximale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double maxY() {
        if (this.getContient().isEmpty()) {
            return 0;
        } else {
            double max = this.getContient().get(0).maxY();
            for (int i = 1; i < this.getContient().size(); i++) {
                double cur = this.getContient().get(i).maxY();
                if (cur > max) {
                    max = cur;
                }
            }
            return max;
        }
    }

    /**
     * ordonnee minimale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double minY() {
        if (this.getContient().isEmpty()) {
            return 0;
        } else {
            double min = this.getContient().get(0).minY();
            for (int i = 1; i < this.getContient().size(); i++) {
                double cur = this.getContient().get(i).minY();
                if (cur < min) {
                    min = cur;
                }
            }
            return min;
        }
    }

    @Override
    public double distancePoint(Point p) {
        if (this.getContient().isEmpty()) {
            return new Point(0, 0).distancePoint(p);
        } else {
            double dist = this.getContient().get(0).distancePoint(p);
            for (int i = 1; i < this.getContient().size(); i++) {
                double cur = this.getContient().get(i).distancePoint(p);
                if (cur < dist) {
                    dist = cur;
                }
            }
            return dist;
        }
    }

    @Override
    public void dessine(GraphicsContext context) {
        for (Figure f : this.getContient()) {
            f.dessine(context); //on demande de dessiner les figures du groupe
        }
    }

    @Override
    public void dessineSelection(GraphicsContext context) {
        for (Figure f : this.getContient()) {
            f.dessineSelection(context);
        }
    }

    @Override
    public void changeCouleur(Color value) {
        for (Figure f : this.getContient()) {
            f.changeCouleur(value);
        }
    }

    @Override
    public void save(Writer w, Numeroteur<Figure> num) throws IOException {
        if (!num.objExist(this)) {
            int id = num.creeID(this);
            for (Figure f : this.getContient()) {
                f.save(w, num);
            }
            w.append("Groupe;" + id);
            for (Figure f : this.getContient()) {
                w.append(";" + num.getID(f));
            }
            w.append("\n");
        }
    }

}

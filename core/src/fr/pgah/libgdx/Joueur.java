package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Joueur {

  final int vitesse = 10;
  int longueurFenetre;
  int hauteurFenetre;
  Texture imgJ;
  int coordXJ;
  int coordYJ;
  int longueurEffective;
  int hauteurEffective;
  Rectangle zoneDeHits; // pour la détection des collisions

  public Joueur() {
    imgJ = new Texture("toto.png");
    longueurEffective = imgJ.getWidth();
    hauteurEffective = imgJ.getHeight();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();
    zoneDeHits = new Rectangle(coordXJ, coordYJ, longueurEffective, hauteurEffective);
  }

  public void majEtat(){
    deplacer();
    forcerAResterDansLeCadre();
  }

  private void deplacer() {
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      coordXJ -= vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      coordXJ += vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      coordYJ += vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      coordYJ -= vitesse;
    }

    // Coordonnées ont potentiellement changé
    // => Mise à jour zone de "hit"
    zoneDeHits.setPosition(coordXJ, coordYJ);
  }

  private void forcerAResterDansLeCadre() {
    // Gestion bordure droite
    if (coordXJ + longueurEffective > longueurFenetre) {
      coordXJ = longueurFenetre - longueurEffective;
    }

    // Gestion bordure gauche
    if (coordXJ < 0) {
      coordXJ = 0;
    }

    // Gestion bordures haute
    if (coordYJ + hauteurEffective > hauteurFenetre) {
      coordYJ = hauteurFenetre - hauteurEffective;
    }

    // Gestion bordure basse
    if (coordYJ < 0) {
      coordYJ = 0;
    }

    // Coordonnées ont potentiellement changé
    // => Mise à jour zone de "hit"
    zoneDeHits.setPosition(coordXJ, coordYJ);
  }

  public void dessiner(SpriteBatch batch) {
    batch.draw(imgJ, coordXJ, coordYJ);
  }

  public boolean estEnCollisionAvec(ArrayList<Sprite> sprites) {
    // pour chaque sprite dans sprites
    // si le sprite touche le joueur
    // alors renvoyer vrai
    for (Sprite sprite : sprites) {
      if (estEnCollisionAvec(sprite)) {
        return true;
      }
    }
    // si on arrive ici, on a parcouru tout le tableau sans return,
    // donc on n'a aucune collision => faux
    return false;
  }

  private boolean estEnCollisionAvec(Sprite sprite) {
    // 'overlaps' est une méthode fournie par libGDX
    // Elle teste si 2 rectangles se touchent
    if (zoneDeHits.overlaps(sprite.zoneDeHit)) {
      return true;
    } else {
      return false;
    }
  }


  

}

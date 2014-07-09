/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inhoud;

import exceptions.GameBezigException;
import exceptions.OngeldigSpelException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 *
 * @author Ellen
 */
public class Model implements mijnObserver{

    private static final Model model = new Model();

    public static Model getInstance() {
        return model;
    }

    private List<Kaartje> kaartjes;
    private List<Speler> spelers;
    private List<RondePunten> punten;

    private Ronde ronde;
    private int huidigeSpeler;
    private boolean spelBezig;

    public static final int KAARTJESPP = 3;

    private Model() {
        newGame();
    }

    public void newGame() {
        kaartjes = new ArrayList<>();
        spelers = new ArrayList<>();
        punten = new ArrayList<>();
        ronde = Ronde.EEN;
        huidigeSpeler = 0;
        spelBezig = false;
    }

    public void addSpeler(Speler speler) throws GameBezigException {
        if (spelBezig) {
            throw new GameBezigException("kan geen spelers toevoegen als spel bezig is");
        }
        spelers.add(speler);
        for (int i = 0; i < KAARTJESPP; i++) {
            kaartjes.add(new Kaartje());
        }
        if (spelers.size() % 2 == 1) {
            punten.add(new RondePunten());
        }
        
        invalidate(Onderwerp.SPELERS_ADD);
        
    }

    public List<Kaartje> getKaartjes() {
        ArrayList<Kaartje> list = new ArrayList<>();
        list.addAll(kaartjes);
        return list;
    }

    public List<Speler> getSpelers() {
        ArrayList<Speler> list = new ArrayList<>();
        list.addAll(spelers);
        return list;
    }

    public List<RondePunten> getPunten() {
        return punten;
    }

    public Kaartje[] getKaartjes(Speler speler) {
        Kaartje[] k = new Kaartje[KAARTJESPP];

        int index = spelers.indexOf(speler);
        index = index * KAARTJESPP;
        for (int i = 0; i < KAARTJESPP; i++) {
            k[i] = kaartjes.get(index + i);
        }

        return k;
    }

    public Speler getPartner(Speler speler) {
        int index = spelers.indexOf(speler);

        if (index >= spelers.size() / 2) {
            return spelers.get(index - (spelers.size() / 2));
        } else {
            return spelers.get(index + (spelers.size() / 2));
        }
    }

    public RondePunten getPunten(Speler speler) {
        int index = spelers.indexOf(speler);

        if (index >= spelers.size() / 2) {
            return punten.get(index - (spelers.size() / 2));
        } else {
            return punten.get(index);
        }

    }

    public Ronde getRonde() {
        return ronde;
    }

    public boolean hasvolgenderonde() {
        return ronde.hasVolgende();
    }

    public void volgenderonde() throws OngeldigSpelException {
        if(!ronde.hasVolgende())
            throw new OngeldigSpelException("geen volgende ronde beschikbaar");
        ronde = ronde.volgende();
        invalidate(Onderwerp.RONDE_VOLGENDE);
    }

    public void print() {
        System.out.println(ronde);
        System.out.println("huidige speler: " + huidigeSpeler);
        System.out.println("bezig=" + spelBezig);
        System.out.println("SPELERS");
        for (int i = 0; i < spelers.size(); i++) {
            System.out.println("- " + i + " " + spelers.get(i));
        }
        System.out.println("KAARTJES");
        for (int i = 0; i < kaartjes.size(); i++) {
            System.out.println((i + 1) + " " + kaartjes.get(i));
        }
        System.out.println("PUNTEN");
        for (int i = 0; i < punten.size(); i++) {
            System.out.println(i + " " + punten.get(i));
        }
    }

    public void volgendeSpeler() throws GameBezigException {
        if (!spelBezig) {
            throw new GameBezigException("kan enkel naar volgende speler gaan tijdens een spel");
        }
        huidigeSpeler++;
        if (huidigeSpeler >= spelers.size()) {
            huidigeSpeler = 0;
        }
    }

    public void getHuidigeSpeler() {
        spelers.get(huidigeSpeler);
    }

    public void startSpel() throws GameBezigException, OngeldigSpelException {
        if (spelBezig) {
            throw new GameBezigException("spel is al bezig");
        }
        if (!geldigeInhoud()) {
            throw new OngeldigSpelException("Spel voldoet niet aan regels");
        }
        spelBezig = true;
    }

    public boolean geldigeInhoud() {
        if (spelers.size() % 2 != 0) {
            return false;
        }
        for (Kaartje k : kaartjes) {
            if (k.getWoord() == null) {
                return false;
            }
        }

        return true;
    }

    
    private List<mijnLuisteraar> luisteraars = new ArrayList<mijnLuisteraar>();
    private List<Onderwerp> onderwerpen = new ArrayList<Onderwerp>();
    
    @Override
    public void addLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp) {
        luisteraars.add(luisteraar);
        onderwerpen.add(onderwerp);

    
    }

    @Override
    public void removeLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp) {
        int i=luisteraars.indexOf(luisteraar);
        onderwerpen.remove(i);
        luisteraars.remove(i);
    }
    
    private void invalidate(Onderwerp onderwerp){
        for(int i=0;i<luisteraars.size();i++){
            if(onderwerpen.get(i)==onderwerp){
                luisteraars.get(i).invalidate(this, onderwerp);
            }
        }
    }
    
    
    
    
   
}

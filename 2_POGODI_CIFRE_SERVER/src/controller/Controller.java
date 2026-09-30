/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import forme.ServerskaForma;
import java.util.ArrayList;
import java.util.List;
import model.SkriveniBroj;
import model.User;

/**
 *
 * @author racunar
 */
public class Controller {

    private static Controller instance;
    private List<User> listaUsera = new ArrayList<>();
    private User ulogovani = null;
    private List<SkriveniBroj> brojevi = new ArrayList<>();
    private ServerskaForma sf;

    private Controller() {
        User u1 = new User("pera@gmail.com", "123456", "Pera", "Peric");
        User u2 = new User("mara@gmail.com", "654321", "Mara", "Maric");
        listaUsera.add(u1);
        listaUsera.add(u2);

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public User login(String email, String lozinka) {
        for (User u : listaUsera) {
            if (u.getEmail().equals(email) && u.getLozinka().equals(lozinka)) {
                ulogovani = u;
                return u;
            }
        }
        return null;
    }

    public User getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(User ulogovani) {
        this.ulogovani = ulogovani;
    }

    public List<SkriveniBroj> getBrojevi() {
        return brojevi;
    }

    public void setBrojevi(List<SkriveniBroj> brojevi) {
        this.brojevi = brojevi;
    }

    public void dodajBroj(SkriveniBroj sb) {
        brojevi.add(sb);
        System.out.println(sb);
    }

    public boolean postoji(SkriveniBroj sb) {
        for (SkriveniBroj skriveniBroj : brojevi) {
            if (sb.getVrednost() == skriveniBroj.getVrednost()) {
                return true;
            }
        }
        return false;
    }

    public SkriveniBroj pogodiBroj(SkriveniBroj skriveniBroj) {
        for (SkriveniBroj sb : brojevi) {
            if(sb.getKolona()==skriveniBroj.getKolona() && sb.getRed()==skriveniBroj.getRed()){
                return sb;
            }
        }
        return null;
    }

    public ServerskaForma getSf() {
        return sf;
    }

    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }
    
    public void pokreniNovuIgru() {
        brojevi = new ArrayList<>();
        sf.pokreniIgru();
    }

   

}

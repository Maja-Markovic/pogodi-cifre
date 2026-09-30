/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import controller.Controller;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SkriveniBroj;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import operacije.Operacije;

/**
 *
 * @author racunar
 */
public class ObradaKlijentskihZahteva extends Thread {

    private Socket s;

    public ObradaKlijentskihZahteva(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (s != null && !s.isClosed()) {
            try {
                KlijentskiZahtev kz = primiZahtev(); //ovo se desava i u zadnjoj iteraciji
                ServerskiOdgovor so = new ServerskiOdgovor();
                switch (kz.getOperacija()) {
                    case Operacije.POGODI_BROJ:
                        SkriveniBroj sb = Controller.getInstance().pogodiBroj((SkriveniBroj) kz.getParam());
                        so.setOdgovor(sb);
                        break;
                    case Operacije.NOVA_IGRA:
                        Controller.getInstance().pokreniNovuIgru();
                        break;
                    default:
                        System.out.println("GRESKA");
                }
                posaljiOdgovor(so);
            } catch (IOException ex) {
                Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private KlijentskiZahtev primiZahtev() throws IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) ois.readObject();
        } catch (EOFException ex) {
            System.out.println("KLIJENT SE ODVEZAO");
            s.close();
            s = null;
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

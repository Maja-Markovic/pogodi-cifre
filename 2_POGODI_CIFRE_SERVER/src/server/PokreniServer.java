/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author racunar
 */
public class PokreniServer extends Thread {

    ObradaKlijentskihZahteva nit;

    @Override
    public void run() {
        try {
            try {
                ServerSocket serverskiSocket = new ServerSocket(9000);

                while (true) {
                    Socket s = serverskiSocket.accept();
                    System.out.println("KLIJENT SE POVEZAO");

                    nit = new ObradaKlijentskihZahteva(s);
                    nit.start();
                }
            } catch (SocketException ex) {
                nit.interrupt();
                System.out.println("KLIJENT SE ODVEZAO");
            }
        } catch (SocketException ex) {
            System.out.println("KLIJENT SE ODVEZAO");
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

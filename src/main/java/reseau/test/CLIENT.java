package reseau.test;

import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.tool.NetworkTool;
import reseau.type.TypePartie;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

public class CLIENT {

    public static void main(String[] args) throws IOException {
        //Init network
        NetWorkManager nwm = new NetWorkManager(new Object());
        //Get ip
        InetAddress ip = NetworkTool.getAliveLocalIp();
        if (ip == null) {
            List<InetAddress> ips = NetworkTool.getInterfaces();
            int i = 0;
            for (InetAddress ia : ips) {
                System.out.println(i + " " + ia.getHostAddress());
                i++;
            }

            int val = new Scanner(System.in).nextInt();
            ip = ips.get(val);
        }

        //Demarrage des serveurs
        nwm.initConnection(SideConnection.CLIENT, ip);
        System.out.println(ip.getHostAddress());
        System.out.println(nwm.getTcpPort());

        System.out.println("Vous devez choisir un filtre : " + TypePartie.BOTU + " " + TypePartie.JRU + " " + TypePartie.MIXTE);
        String typePartie = new Scanner(System.in).nextLine();

        System.out.println("Nombre de partie a rechercher");
        int nbPartie = new Scanner(System.in).nextInt();

        //Demarrage de la partie
        nwm.getUdpSocket().sendPacket(nwm.getPacketsUdp().get("RP").build(TypePartie.valueOf(typePartie), nbPartie));
    }
}

package reseau.test;

import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.tool.NetworkTool;
import reseau.tool.ThreadTool;
import reseau.type.Status;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

public class SERVER {
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
        nwm.initConnection(SideConnection.SERVER, ip);
        System.out.println(ip.getHostAddress());
        System.out.println(nwm.getTcpPort());

        System.out.println("Donner le nom de la partie : ");
        nwm.nomPartie = new Scanner(System.in).nextLine();

        System.out.println("Donner le nombre de joueur au total (6 max) : ");
        do {
            nwm.nbjtotal = new Scanner(System.in).nextInt();
        } while (nwm.nbjtotal > 6);

        System.out.println("Donner le nombre de joueur réel au total : ");
        do {
            nwm.nbjr = new Scanner(System.in).nextInt();
            nwm.nbjv = nwm.nbjtotal - nwm.nbjr;
        } while (nwm.nbjr > 6);
        System.out.println("Il y aura " + nwm.nbjv + " joueurs virtuel !");

        String m = nwm.getPacketsUdp().get("ACP").build(
                nwm.partieIdi, nwm.getAddress().getHostAddress(), nwm.getTcpPort(), nwm.nomPartie, nwm.nbjtotal, nwm.nbjr, nwm.nbjv, Status.ATTENTE);
        nwm.getUdpSocket().sendPacket(m);

        //---------------------------------------------------

        Thread t = new Thread(() -> {
            System.out.println(ThreadTool.taskPacketTcp(NetworkTool.getAliveLocalIp(), 1025, "DJ-P0-0"));
            System.out.println("TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST");
        });

        t.run();
    }
}

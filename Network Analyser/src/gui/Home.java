/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import commons.Database;
//import commons.Initializer;
import constants.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import util.Classifier;

/**
 *
 * 
 */
public class Home extends javax.swing.JFrame implements PacketReceiver, Constants {

    private JpcapCaptor jpcap;
    private static final String TCP = "tcp", ICMP = "icmp", IP = "ip", ARP = "arp", UDP = "udp";
    private static final String LINE = System.getProperty("line.separator");
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
    private File packetFile = new File("packets.csv");

    private static Connection connection;
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
        try {
            jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            int i = 0;
            for (jpcap.NetworkInterface device : devices) {
                NetworkInterfaceAddress[] addresses = device.addresses;
                String displayName = null;
                for (NetworkInterfaceAddress add : addresses) {
                    NetworkInterface name = NetworkInterface.getByInetAddress(add.address);
                    if (name != null) {
                        displayName = i + " " + name.getDisplayName();
                    }
                }
                if (displayName == null) {
                    displayName = i + " " + device.description;
                }
                comboNetworkInterface.addItem(displayName);
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_tools = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboNetworkInterface = new javax.swing.JComboBox();
        buttonStart = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        buttonRestart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePackets = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Network Anomaly Detection");
        setResizable(false);

        panel_tools.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Netowrk Interfaces");

        comboNetworkInterface.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboNetworkInterface.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select network interface" }));
        comboNetworkInterface.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboNetworkInterfaceItemStateChanged(evt);
            }
        });

        buttonStart.setText("start capture");
        buttonStart.setEnabled(false);
        buttonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartActionPerformed(evt);
            }
        });

        buttonStop.setText("stop capture");
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });

        buttonRestart.setText("restart capture");
        buttonRestart.setEnabled(false);
        buttonRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_toolsLayout = new javax.swing.GroupLayout(panel_tools);
        panel_tools.setLayout(panel_toolsLayout);
        panel_toolsLayout.setHorizontalGroup(
            panel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_toolsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(comboNetworkInterface, 0, 357, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestart)
                .addContainerGap())
        );
        panel_toolsLayout.setVerticalGroup(
            panel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_toolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboNetworkInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonStart)
                    .addComponent(buttonStop)
                    .addComponent(buttonRestart))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablePackets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Source IP", "Destination IP", "Data", "DateTime", "Protocol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablePackets);

        jLabel1.setText("PACKET CAPTURE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_tools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(402, 402, 402))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboNetworkInterfaceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboNetworkInterfaceItemStateChanged
        if (comboNetworkInterface.getSelectedIndex() > 0) {
            setButtonsEnabled(true);
        } else {
            setButtonsEnabled(false);
        }
    }//GEN-LAST:event_comboNetworkInterfaceItemStateChanged

    private void buttonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartActionPerformed
        try {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    startCapturing();
                }
            });
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_buttonStartActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        if (jpcap != null) {
            jpcap.breakLoop();
        }
    }//GEN-LAST:event_buttonStopActionPerformed

    private void buttonRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestartActionPerformed
        if (jpcap != null) {
            jpcap.breakLoop();
        }
        clearPackets();
        try {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    startCapturing();
                }
            });
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_buttonRestartActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRestart;
    private javax.swing.JButton buttonStart;
    private javax.swing.JButton buttonStop;
    private javax.swing.JComboBox comboNetworkInterface;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_tools;
    private javax.swing.JTable tablePackets;
    // End of variables declaration//GEN-END:variables

    private void setButtonsEnabled(boolean is) {
        buttonStart.setEnabled(is);
        buttonRestart.setEnabled(is);
        buttonStop.setEnabled(is);
    }

    private void startCapturing() {
        try {
            int index = Integer.parseInt(comboNetworkInterface.getSelectedItem().toString().split(" ")[0].trim());
            jpcap = JpcapCaptor.openDevice(JpcapCaptor.getDeviceList()[index], 65535, true, 20);
            jpcap.loopPacket(-1, this);
        } catch (NumberFormatException | IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void addPacketToTable(String src, String dst, String data, String packetType) {
        data = data.replaceAll("[^a-zA-Z ]", "");
        Vector row = new Vector();
        row.add(src);
        row.add(dst);
        row.add(data);
        String dateTime = format.format(new Date());
        row.add(dateTime);
        row.add(packetType);
        DefaultTableModel model = (DefaultTableModel) tablePackets.getModel();
        model.addRow(row);
        try {
            FileWriter writer = new FileWriter(packetFile, true);
            StringBuilder builder = new StringBuilder();
            builder.append("\"").append(src).append("\"").append(",");
            builder.append("\"").append(dst).append("\"").append(",");
            builder.append("\"").append(data).append("\"").append(",");
            builder.append("\"").append(dateTime).append("\"").append(",");
            builder.append("\"").append(packetType).append("\"").append(LINE);
            writer.write(builder.toString());
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void clearPackets() {
        DefaultTableModel model = (DefaultTableModel) tablePackets.getModel();
        int total = model.getRowCount();
        for (int i = 0; i < total; i++) {
            model.removeRow(0);
        }
    }

    @Override
    public void receivePacket(Packet packet) {
        try {
            if (packet instanceof TCPPacket) {
                TCPPacket tcp = (TCPPacket) packet;
                String src = tcp.src_ip.getHostAddress() + ":" + tcp.src_port;
                String dst = tcp.dst_ip.getHostAddress() + ":" + tcp.dst_port;
                String data = new String(tcp.data);
                if(checkIfAttack(data)) {
                    addPacketToTable(src, dst, data, TCP);
                } else {
                    if(Classifier.categorizePacket(data) == 2) {
                        addPacketToTable(src, dst, data, TCP);
                    } else {
//                        System.err.println("no attack ");
                    }
                }
            } else if(packet instanceof UDPPacket) {
                UDPPacket udp = (UDPPacket) packet;
                String src = udp.src_ip.getHostAddress() + ":" + udp.src_port;
                String dst = udp.dst_ip.getHostAddress() + ":" + udp.dst_port;
                String data = new String(udp.data);
                if(checkIfAttack(data)) {
                    addPacketToTable(src, dst, data, UDP);
                } else {
                    if(Classifier.categorizePacket(data) == 2) {
                        addPacketToTable(src, dst, data, TCP);
                    } else {
//                        System.err.println("no attack ");
                    }
                }
            } else if(packet instanceof ARPPacket) {
                ARPPacket arp = (ARPPacket) packet;
                String data = new String(arp.data);
                String src = new String(arp.sender_protoaddr);
                String dst = new String(arp.target_protoaddr);
                if(checkIfAttack(data)) {
                    addPacketToTable(src, dst, new String(arp.data), ARP);
                } else {
                    if(Classifier.categorizePacket(data) == 2) {
                        addPacketToTable(src, dst, data, TCP);
                    } else {
//                        System.err.println("no attack ");
                    }
                }
            } else if (packet instanceof IPPacket) {
                IPPacket ip = (IPPacket) packet;
                String src = ip.src_ip.getHostAddress() + ":";
                String dst = ip.dst_ip.getHostAddress() + ":";
                String data = new String(ip.data);
                if(checkIfAttack(data)) {
                    addPacketToTable(src, dst, data, IP);
                } else {
                    if(Classifier.categorizePacket(data) == 2) {
                        addPacketToTable(src, dst, data, TCP);
                    } else {
//                        System.err.println("no attack ");
                    }
                }
            } else if (packet instanceof ICMPPacket) {
                ICMPPacket icmp = (ICMPPacket) packet;
                String src = icmp.src_ip.getHostAddress();
                String dst = icmp.dst_ip.getHostAddress();
                String data = new String(icmp.data);
                if(checkIfAttack(data)) {
                    addPacketToTable(src, dst, data, ICMP);
                } else {
                    if(Classifier.categorizePacket(data) == 2) {
                        addPacketToTable(src, dst, data, TCP);
                    } else {
//                        System.err.println("no attack ");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private boolean checkIfAttack(String data) {
        try {
            String query = "SELECT * FROM signatures WHERE signature_data=? AND packet_type_id=2";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.err.println("Error selecting record for " + data + "  " + ex.getMessage());
        }
        return false;
    }

}
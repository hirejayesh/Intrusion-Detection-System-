/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import commons.Database;
import constants.Constants;
//import commons.Initializer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * 
 */
public class Classifier implements Constants {

    private static Connection connection;

    public static int categorizePacket(String data) {
        int cluster = 1;
        try {
            // normal packet records
            if(connection == null || connection.isClosed())
                connection = DriverManager.getConnection(url, user, password);
            ArrayList<ArrayList> records = Database.select("signatures", null, "packet_type_id=" + 2, connection);
            int attack_packet_distance = Integer.MAX_VALUE;
            int normal_packet_distance = Integer.MAX_VALUE;
            if(records != null && !records.isEmpty()) {
                for(ArrayList record : records) {
                    String signature_data = record.get(1).toString();
                    if(signature_data != null) {
                        int dist = distance(signature_data, data);
                        if(attack_packet_distance > dist) {
                            attack_packet_distance = dist;
                        }
                    }
                }
            }
            records = Database.select("signatures", null, "packet_type_id=" + 1, connection);
            if(records != null && !records.isEmpty()) {
                for(ArrayList record : records) {
                    String signature_data = record.get(1).toString();
                    if(signature_data != null) {
                        int dist = distance(signature_data, data);
                        if(normal_packet_distance > dist) {
                            normal_packet_distance = dist;
                        }
                    }
                }
            }
            System.out.println("Normal Distance :: " + normal_packet_distance + " Attack Distance :: " + attack_packet_distance);
            if(normal_packet_distance < attack_packet_distance) {
                cluster = 2;
            } else {
                cluster = 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return cluster;
    }

    // Leven shtein distance
    private static int distance(String signature_1, String signature_2) {
        signature_1 = signature_1.toLowerCase();
        signature_2 = signature_2.toLowerCase();
        int[] costs = new int[signature_2.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= signature_1.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= signature_2.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), signature_1.charAt(i - 1) == signature_2.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[signature_2.length()];
    }

}
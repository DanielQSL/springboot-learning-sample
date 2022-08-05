package com.qsl.springboot.bigfile.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 生成指定大小的文件的工具类
 *
 * @author DanielQSL
 */
public class BigFileGenerator {

    public static void main(String[] args) {
        String path = "D:/bigfile1GB.txt";
        try {
            // 生成一个1GB的CSV文件
            creatBigFile(path, (long) 1024 * 1024 * 1024 * 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个指定byte的CSV文件
     *
     * @param path 生成路径位置
     * @param size 指定大小（单位：字节）
     * @throws IOException 异常
     */
    public static void creatBigFile(String path, long size) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        long start = System.currentTimeMillis();
        // 标题
        String title = "time,src_ip,request_url,dest_ip,dest_port,method,user_agent,connection,server,status,protocol\r\n";
        writer.write(title);

        long total = title.getBytes().length;
        long line = 0;
        while (total < size) {
            line++;
            String tempRecord = formatRecord();
            writer.write(tempRecord);
            total += tempRecord.getBytes().length;
        }
        long end = System.currentTimeMillis();

        writer.flush();
        writer.close();

        System.out.println("total line:" + line);
        System.out.println("cost :" + (end - start) / 1000);
    }

    private static final String SEPARATOR = ",";

    public static String formatRecord() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis()).append(SEPARATOR);
        sb.append(getSrcIp()).append(SEPARATOR);
        sb.append(getRequestUrl()).append(SEPARATOR);
        sb.append(getDestIp()).append(SEPARATOR);
        sb.append(new Random().nextInt(65535)).append(SEPARATOR);
        sb.append(getMethodType()).append(SEPARATOR);
        sb.append(getUseragent()).append(SEPARATOR);
        sb.append(getConnection()).append(SEPARATOR);
        sb.append(getServer()).append(SEPARATOR);
        sb.append(getStatus()).append(SEPARATOR);
        sb.append(getProtocolType()).append("\r\n");
        return sb.toString();
    }


    private static String getRequestUrl() {
        String[] urls = {"/log", "/logout", "/getUsers", "/countUsers", "/list", "/status", "/IPP", "/Jabber", "/Netflix"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return urls[next % urls.length];
    }

    private static String getSrcIp() {
        String[] ips = {"192.168.19.10", "192.168.19.11", "192.168.19.12", "192.168.19.13", "192.168.19.14", "192.168.19.15", "192.168.19.16", "192.168.19.17", "192.168.19.18", "192.168.19.19", "192.168.19.20",
                "192.168.10.10", "192.168.10.11", "192.168.10.12", "192.168.10.13", "192.168.10.14", "192.168.10.15", "192.168.10.16", "192.168.10.17", "192.168.10.18", "192.168.10.19", "192.168.10.20"
        };
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return ips[next % 20];
    }

    private static String getDestIp() {
        String[] destIps = {"192.168.11.10", "192.168.11.11", "192.168.11.12", "192.168.11.13", "192.168.11.14", "192.168.11.15", "192.168.11.16", "192.168.11.17", "192.168.11.18", "192.168.11.19", "192.168.11.20",
                "192.168.15.10", "192.168.15.11", "192.168.15.12", "192.168.15.13", "192.168.15.14", "192.168.15.15", "192.168.15.16", "192.168.15.17", "192.168.15.18", "192.168.15.19", "192.168.15.20"
        };
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return destIps[next % 20];
    }

    private static String getProtocolType() {
        String[] protocols = {"FTP", "Telnet", "http", "Telnet", "AFP", "ICMP", "IPP", "Jabber", "Netflix", "PPTP", "QQ", "Quake", "SAP", "SNMP", "SSH", "SSL", "Teredo"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return protocols[next % protocols.length];
    }

    private static String getMethodType() {
        String[] methods = {"trace", "put", "delete", "get", "post"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return methods[next % methods.length];
    }

    private static String getUseragent() {
        String[] userAgents = {"Firefox", "Edge", "windows", "chrome", "Wechat"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return userAgents[next % userAgents.length];
    }

    private static String getStatus() {
        String[] status = {"0", "1"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return status[next % status.length];
    }

    private static String getConnection() {
        String[] connections = {"no-catched", "keep-alived"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return connections[next % connections.length];
    }

    private static String getServer() {
        String[] servers = {"Mac", "Windows", "Linux", "Android"};
        Random r = new Random();
        int next = r.nextInt();
        next = Math.abs(next);
        return servers[next % servers.length];
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String a[]) {
        String targetIp = "202.24.34.55";
        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("---- Experimentos de Desempeño ----");
        System.out.println("Número de núcleos disponibles: " + cores);
        System.out.println("IP objetivo: " + targetIp);
        System.out.println();

        // Experimento 1: 1 hilo
        runExperiment(targetIp, 1, "Un solo hilo");

        // Experimento 2: Tantos hilos como núcleos
        runExperiment(targetIp, cores, "Tantos hilos como núcleos (" + cores + ")");

        // Experimento 3: Doble de núcleos
        runExperiment(targetIp, cores * 2, "Doble de núcleos (" + (cores * 2) + ")");

        // Experimento 4: 50 hilos
        runExperiment(targetIp, 50, "50 hilos");

        // Experimento 5: 100 hilos
        runExperiment(targetIp, 100, "100 hilos");
    }

    private static void runExperiment(String ip, int threads, String description) {
        HostBlackListsValidator hblv = new HostBlackListsValidator();

        System.out.println("Ejecutando: " + description + "...");
        long startTime = System.currentTimeMillis();

        List<Integer> blackListOcurrences = hblv.checkHost(ip, threads);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Resultado: " + blackListOcurrences);
        System.out.println("Tiempo de ejecución: " + duration + " ms");
        System.out.println("------------------------------------");
    }
}

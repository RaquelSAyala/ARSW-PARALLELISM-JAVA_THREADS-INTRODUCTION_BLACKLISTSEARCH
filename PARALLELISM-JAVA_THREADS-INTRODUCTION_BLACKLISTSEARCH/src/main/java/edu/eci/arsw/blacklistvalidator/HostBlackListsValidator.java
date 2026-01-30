/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * 
     * @param ipaddress suspicious host's IP address.
     * @return Blacklists numbers where the given host's IP address was found.
     */

    public List<Integer> checkHost(String ipaddress) {
        return checkHost(ipaddress, 1);
    }

    public List<Integer> checkHost(String ipaddress, int N) {

        LinkedList<Integer> blackListOcurrences = new LinkedList<>();

        int ocurrencesCount = 0;

        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        int totalServers = skds.getRegisteredServersCount();

        if (N <= 0)
            N = 1;
        if (N > totalServers)
            N = totalServers;

        int base = totalServers / N;
        int rem = totalServers % N;

        BlackListSearchThread[] threads = new BlackListSearchThread[N];

        int start = 0;
        for (int t = 0; t < N; t++) {
            int size = base + (t < rem ? 1 : 0);
            int end = start + size;

            threads[t] = new BlackListSearchThread(start, end, ipaddress, skds);
            start = end;
        }

        for (BlackListSearchThread th : threads) {
            th.start();
        }

        for (BlackListSearchThread th : threads) {
            try {
                th.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting threads.", ex);
            }
        }

        int checkedListsCount = 0;
        for (BlackListSearchThread th : threads) {
            blackListOcurrences.addAll(th.getBlackListOcurrences());
            ocurrencesCount += th.getOccurrencesCount();
            checkedListsCount += th.getCheckedListsCount();
        }

        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}",
                new Object[] { checkedListsCount, totalServers });

        return blackListOcurrences;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

}

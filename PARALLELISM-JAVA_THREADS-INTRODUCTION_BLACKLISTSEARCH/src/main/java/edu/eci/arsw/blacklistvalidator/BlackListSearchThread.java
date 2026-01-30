package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;

/**
 * Thread to search for an IP address in a specific range of blacklists.
 */
public class BlackListSearchThread extends Thread {

    private int start;
    private int end;
    private String ipAddress;
    private HostBlacklistsDataSourceFacade datasource;

    private int occurrencesCount = 0;
    private LinkedList<Integer> blackListOcurrences = new LinkedList<>();
    private int checkedListsCount = 0;

    /**
     * Constructor for BlackListSearchThread.
     * 
     * @param start      The starting index of the blacklist range.
     * @param end        The ending index of the blacklist range.
     * @param ipAddress  The IP address to search for.
     * @param datasource The datasource facade to use.
     */
    public BlackListSearchThread(int start, int end, String ipAddress, HostBlacklistsDataSourceFacade datasource) {
        this.start = start;
        this.end = end;
        this.ipAddress = ipAddress;
        this.datasource = datasource;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            checkedListsCount++;
            if (datasource.isInBlackListServer(i, ipAddress)) {
                blackListOcurrences.add(i);
                occurrencesCount++;
            }
        }
    }

    public int getOccurrencesCount() {
        return occurrencesCount;
    }

    public List<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
}

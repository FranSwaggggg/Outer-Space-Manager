package francois.tomasi.outerspacemanager.models;

import java.util.UUID;


public class SearchStatus {

    private UUID id;
    private String searchId;
    private String searching;
    private String dateSearching;

    public UUID getId() { return id; }

    public String getSearchId() { return searchId; }

    public String getSearching() { return searching; }

    public String getDateSearching() { return dateSearching; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public void setSearching(String searching) {
        this.searching = searching;
    }

    public void setDateSearching(String dateSearching) {
        this.dateSearching = dateSearching;
    }
}

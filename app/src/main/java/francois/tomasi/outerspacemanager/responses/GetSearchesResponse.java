package francois.tomasi.outerspacemanager.responses;

import java.util.List;
import francois.tomasi.outerspacemanager.models.Search;

public class GetSearchesResponse {
    private int size;
    private List<Search> searches;

    public GetSearchesResponse(int size, List<Search> searches) {
        this.size = size;
        this.searches = searches;
    }

    public int getSize() { return size; }

    public List<Search> getSearches() { return searches; }
}

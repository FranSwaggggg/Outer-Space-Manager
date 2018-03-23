package francois.tomasi.outerspacemanager.responses;

import java.util.ArrayList;

import francois.tomasi.outerspacemanager.models.User;

public class GetUsersResponse {
    private int size;
    private ArrayList<User> users;

    public GetUsersResponse(int size, ArrayList<User> users) {
        this.size = size;
        this.users = users;
    }

    public int getSize() { return size; }

    public ArrayList<User> getUsers() { return users; }
}

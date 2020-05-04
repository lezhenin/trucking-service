package core.repository;

import core.Order;

import java.util.ArrayList;
import java.util.List;

public class ListRepository<G> {

    List<G> itemList = new ArrayList<G>();

    public void add(G item) {
        itemList.add(item);
    }

    public void remove(G item) {
        itemList.remove(item);
    }

    public List<G> getAll() {
        return new ArrayList<G>(itemList);
    }

}

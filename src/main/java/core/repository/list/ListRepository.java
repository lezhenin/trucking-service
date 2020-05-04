package core.repository.list;

import core.repository.RepositoryItem;

import java.util.ArrayList;
import java.util.List;

public class ListRepository<G extends RepositoryItem> {

    private int idCounter = 0;

    List<G> itemList = new ArrayList<G>();

    public void add(G item) {
        itemList.add(item);
        item.setId(idCounter++);
    }

    public void remove(G item) {
        itemList.remove(item);
    }

    public List<G> getAll() {
        return new ArrayList<G>(itemList);
    }

}

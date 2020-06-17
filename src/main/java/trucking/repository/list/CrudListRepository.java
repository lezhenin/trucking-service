package trucking.repository.list;

import trucking.model.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NoRepositoryBean
public class CrudListRepository<T extends BaseEntity> implements CrudRepository<T, Long> {

    private Long idCounter = 0L;

    protected List<T> itemList = new ArrayList<T>();

    @Override
    public <S extends T> S save(S item) {
        if (item.getId() == null) {
            itemList.add(item);
            item.setId(idCounter++);
        }
        return item;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        for(T item: iterable) {
            save(item);
        }
        return iterable;
    }

    @Override
    public Optional<T> findById(Long id) {
        return itemList.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return itemList.stream()
                .anyMatch(item -> Objects.equals(item.getId(), id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<T>(itemList);
    }

    @Override
    public List<T> findAllById(Iterable<Long> iterable) {
        ArrayList<T> result =  new ArrayList<T>();
        for(Long id: iterable) {
            findById(id).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public long count() {
        return itemList.size();
    }

    @Override
    public void deleteById(Long id) {
        itemList = itemList.stream()
                .filter(item -> !Objects.equals(item.getId(), id))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(T t) {
        deleteById(t.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        for(T item: iterable) {
            delete(item);
        }
    }

    @Override
    public void deleteAll() {
        itemList.clear();
    }
}

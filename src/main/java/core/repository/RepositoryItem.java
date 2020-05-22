package core.repository;

import javax.persistence.*;

@MappedSuperclass
public abstract class RepositoryItem {

    @Id @GeneratedValue private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

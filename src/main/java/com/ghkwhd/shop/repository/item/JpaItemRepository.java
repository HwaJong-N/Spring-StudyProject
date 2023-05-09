package com.ghkwhd.shop.repository.item;

import com.ghkwhd.shop.domain.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaItemRepository implements ItemRepository{

    private final EntityManager em;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll() {
        String jpql = "select i from Item i";
        TypedQuery<Item> query = em.createQuery(jpql, Item.class);
        return query.getResultList();
    }

    @Override
    public void update(Long id, Item updateItem) {
        Item findItem = em.find(Item.class, id);
        findItem.setItemName(updateItem.getItemName());
        findItem.setPrice(updateItem.getPrice());
        findItem.setSeller(updateItem.getSeller());
        findItem.setContent(updateItem.getContent());
        findItem.setThumbnailName(updateItem.getThumbnailName());
        findItem.setThumbnailUUID(updateItem.getThumbnailUUID());
    }

    @Override
    public void delete(Long id) {
        String jpql = "delete from Item i where i.id=:id";
        Query query = em.createQuery(jpql).setParameter("id", id);
        query.executeUpdate();
    }

}

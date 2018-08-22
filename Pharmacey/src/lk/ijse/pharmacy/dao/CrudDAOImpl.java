package lk.ijse.pharmacy.dao;

import lk.ijse.pharmacy.dto.MedicineDTO;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public  abstract class CrudDAOImpl<T,ID> implements CrudDAO<T,ID> {

    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl() {
        entity = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public void save(T entity) throws Exception {
        session.persist(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.persist(entity);
    }

    @Override
    public void delete(ID id) throws Exception {
        T t = session.find(entity, id);
        session.remove(t);
    }

    @Override
    public T find(ID id) throws Exception {
        return session.find(entity, id);
    }

    @Override
    public ArrayList<T> getAll() throws Exception {
        return (ArrayList<T>) session.createQuery("FROM "+ entity.getName()).list();
    }

    @Override
    public void setSession(Session session) {

        this.session=session;
    }
}

package ra.service;

import java.util.List;

//T response
//K request
//L kieu du lieu
public interface IGenericService <T,K,E>{
    List<T> findAll();
    T findById(E id);
    T save(K k);
    T update(K k,E id);
    T delete(E id);
}
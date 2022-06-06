package entity;

import java.util.List;

public interface IBaseDao<T> {
    //创建对象文档集合
    public void createCollection();
    //插入数据
    public void insert(T entity);
    //id查询
    public T findById(String id);
    //name查询
    public List<T> findListByName(String name);
    //更新
    public void update(T entity);
    //删除
    public void delete(String id);
    //查询全部
    public List<T> findAll();
}

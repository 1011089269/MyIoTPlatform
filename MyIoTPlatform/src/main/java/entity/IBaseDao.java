package entity;

import java.util.List;

public interface IBaseDao<T> {
    //创建对象文档集合
    public void createCollection();
    //插入数据
    public void insert(T entity);
    //id查询
    public T findById(String id);
    //指定位置查询
    public List<T> findList(int skip, int limit);
    //name查询
    public List<T> findListByName(String name);
    //更新
    public void update(T entity);
    //删除
    public void delete(String id);
}

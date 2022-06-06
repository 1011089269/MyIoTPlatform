package entity;

import java.util.List;

public interface DataBaseDao<T> {
    //创建对象文档集合
    public void createCollection();
    //插入数据
    public void insert(T entity);
    //设备最后一个id查询
    public T findLastByDeviceId(String deviceId);
    //设备id指定位置查询
    public List<T> findListByDeviceId(String deviceId, int skip, int limit);
    //时间查询
    public List<T> findListByPeriod(String deviceId, String begin, String end);
    //删除
    public void delete(String id);
    //删除设备id
    public void deleteByDeviceId(String deviceId);
}

package com.qzc.db.dao;

import com.google.common.collect.Lists;
import com.qzc.exception.DaoException;
import com.qzc.pojo.Pager;
import com.qzc.pojo.Sorter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用Jpa DAO
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/12 11:08
 */
@Repository
@Primary // 对同一个接口，可能会有几种不同的实现类，需要默认采取其中一种的情况下 使用@Primary
@Slf4j
public class BaseJpaDAO {

    @Autowired
    private EntityManager entityManager;

    /**
     * 持久化对象
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:08
     */
    public <T> void save(T entity) {
        entityManager.persist(entity);
    }

    /**
     * 保存且刷新缓存
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:08
     */
    public <T> void saveAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

    /**
     * 更新对象
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:09
     */
    public <T> void update(T entity) {
        entityManager.merge(entity);
    }

    /**
     * 删除对象
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:09
     */
    public <T> void delete(T entity) {
        entityManager.remove(entity);
    }

    /**
     * 通过属性名称和属性值(批量)删除
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/20 16:02
     */
    public <T> void deleteByFieldValue(Class<T> clazz, String fieldName, Serializable fieldValue) {

        // 对于删除和更新动作，需要校验参数（sql一定要有where条件）
        this.checkFieldAndValue(fieldName, fieldValue);

        String sql = "DELETE FROM " + clazz.getName() + " WHERE " + fieldName + " = ? ";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, fieldValue);

        query.executeUpdate();
    }

    /**
     * 通过属性名称和属性值(批量)删除
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/20 16:02
     */
    public <T> void deleteByFieldValueMap(Class<T> clazz, Map<String, Object> fieldValueMap) {

        // 对于删除和更新动作，需要校验参数（sql一定要有where条件）
        this.checkFieldValueMap(fieldValueMap);

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(clazz.getName());

        List<Object> valueList = Lists.newArrayList();

        this.buildSQLAndParameters(sql, valueList, fieldValueMap, null);

        Query query = entityManager.createQuery(sql.toString());
        for (int i = 0; i < valueList.size(); i++) {
            query.setParameter(i + 1, valueList.get(i));
        }

        query.executeUpdate();
    }

    /**
     * 根据主键id查询对象
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:12
     */
    public <T> T getById(Class<T> clazz, Serializable id) {
        return entityManager.find(clazz, id);
    }

    /**
     * 查询所有对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:27
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> clazz) {
        String sql = "FROM " + clazz.getName();
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 11:16
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findByFieldValue(Class<T> clazz, String fieldName, Serializable fieldValue) {
        String sql = "FROM " + clazz.getName() + " WHERE " + fieldName + " = ? ";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, fieldValue);
        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/14 23:30
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findByFieldValueMap(Class<T> clazz, Map<String, Object> fieldValueMap) {
        StringBuilder sql = new StringBuilder();
        sql.append("FROM ").append(clazz.getName());

        List<Object> valueList = Lists.newArrayList();

        this.buildSQLAndParameters(sql, valueList, fieldValueMap, null);

        Query query = entityManager.createQuery(sql.toString());
        for (int i = 0; i < valueList.size(); i++) {
            query.setParameter(i + 1, valueList.get(i));
        }

        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/14 23:30
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findInByFieldValues(Class<T> clazz, String fieldName, List fieldValueList) {
        String sql = "FROM " + clazz.getName() + " WHERE " + fieldName + " IN (:values) ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("values", fieldValueList);
        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/14 23:30
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findLikeByFieldValue(Class<T> clazz, String fieldName, Serializable fieldValue) {
        String sql = "FROM " + clazz.getName() + " WHERE " + fieldName + " LIKE ? ";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, "%" + fieldValue + "%");
        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值分页查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/15 10:51
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findPagerByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap, Pager pager, Sorter sorter) {
        StringBuilder sql = new StringBuilder();
        sql.append("FROM ").append(clazz.getName());

        List<Object> valueList = Lists.newArrayList();
        this.buildSQLAndParameters(sql, valueList, fieldValueMap, sorter);

        Query query = entityManager.createQuery(sql.toString());

        for (int i = 0; i < valueList.size(); i++) {
            query.setParameter(i + 1, valueList.get(i));
        }

        if (pager != null) {
            // 分页
            query.setFirstResult((pager.getPageNum() - 1) * pager.getPageSize());
            query.setMaxResults(pager.getPageSize());
        }

        return query.getResultList();
    }

    /**
     * 根据属性名称和属性值统计记录数
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/17 22:14
     */
    public <T> Long countByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ").append(clazz.getName());

        List<Object> valueList = Lists.newArrayList();
        this.buildSQLAndParameters(sql, valueList, fieldValueMap, null);

        Query query = entityManager.createQuery(sql.toString());

        for (int i = 0; i < valueList.size(); i++) {
            query.setParameter(i + 1, valueList.get(i));
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * 根据sql查询对象列表
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/20 15:04
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findBySQL(Class<T> clazz, String sql, boolean isNative) {
        Query query;
        if (!isNative) {
            query = entityManager.createQuery(sql, clazz);
        } else {
            query = entityManager.createNativeQuery(sql, clazz);
        }

        return query.getResultList();
    }


    //=========================================================================
    private void buildSQLAndParameters(StringBuilder sql,
                                       List<Object> parameterList,
                                       Map<String, Object> fieldValueMap,
                                       Sorter sorter) {

        if (fieldValueMap != null && !fieldValueMap.isEmpty()) {
            sql.append(" WHERE 1 = 1 ");
            for (String key : fieldValueMap.keySet()) {
                sql.append("AND ")
                        .append(key);

                Object value = fieldValueMap.get(key);
                if (value != null) {
                    sql.append(" = ? ");
                    parameterList.add(value);
                } else {
                    sql.append(" IS NULL ");
                }
            }
        }

        if (sorter != null) {
            // 排序
            sql.append(" ORDER BY ").append(sorter.getOrderField()).append(" ").append(sorter.getDirection().toString());
        }
    }

    private void checkFieldAndValue(String fieldName, Serializable fieldValue) {
        if (StringUtils.isBlank(fieldName) || fieldValue == null) {
            log.error("fieldName or fieldValue is null or blank");
            throw new DaoException("fieldName or fieldValue is null or blank");
        }
    }

    private void checkFieldValueMap(Map<String, Object> fieldValueMap) {
        if (fieldValueMap == null || fieldValueMap.isEmpty()) {
            log.error("fieldValueMap is null or empty, check failure");
            throw new DaoException("fieldValueMap is null or empty, check failure");
        }

        for (Map.Entry<String, Object> e : fieldValueMap.entrySet()) {
            if (StringUtils.isBlank(e.getKey()) || e.getValue() == null) {
                log.error("fieldValueMap check failure, key[{}] or value[{}] is null or blank", e.getKey(), e.getValue());
                throw new DaoException("fieldValueMap check failure, field name or value exist null or empty");
            }
        }
    }
}

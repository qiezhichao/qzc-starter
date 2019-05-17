package com.qzc.db.service;

import com.qzc.db.dao.BaseJpaDAO;
import com.qzc.pojo.PageResult;
import com.qzc.pojo.Pager;
import com.qzc.pojo.Sorter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用Jpa Service
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/12 11:41
 */
@Service
public class BaseJpaService {

    @Autowired
    private BaseJpaDAO baseJpaDAO;

    @Transactional
    public <T> void save(T entity) {
        baseJpaDAO.save(entity);
    }

    @Transactional
    public <T> void saveAndFlush(T entity) {
        baseJpaDAO.saveAndFlush(entity);
    }

    @Transactional
    public <T> void update(T entity) {
        baseJpaDAO.update(entity);
    }

    @Transactional
    public <T> void delete(T entity) {
        baseJpaDAO.delete(entity);
    }

    public <T> T getById(Class<T> clazz, Serializable id) {
        return baseJpaDAO.getById(clazz, id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return baseJpaDAO.findAll(clazz);
    }

    public <T> List<T> findByFieldValue(Class<T> clazz, String fieldName, Serializable fieldValue) {
        return baseJpaDAO.findByFieldValue(clazz, fieldName, fieldValue);
    }

    public <T> List<T> findByFieldValueMap(Class<T> clazz, Map<String, Object> fieldValueMap) {
        return baseJpaDAO.findByFieldValueMap(clazz, fieldValueMap);
    }

    public <T> List<T> findInByFieldValues(Class<T> clazz, String fieldName, List fieldValueList) {
        return baseJpaDAO.findInByFieldValues(clazz, fieldName, fieldValueList);
    }

    public <T> PageResult<T> findPagerByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap, Pager pager, Sorter sorter) {
        List<T> resultList = baseJpaDAO.findPagerByFieldValues(clazz, fieldValueMap, pager, sorter);
        PageResult<T> resultPageList = new PageResult<>();
        resultPageList.setRows(resultList);

        if (pager.isQueryTotalCount()) {
            Long totalCount = baseJpaDAO.countByFieldValues(clazz, fieldValueMap);
            resultPageList.setTotalCount(totalCount);
        }

        return resultPageList;
    }

    public <T> List<T> findSortByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap, Sorter sorter) {
        return baseJpaDAO.findPagerByFieldValues(clazz, fieldValueMap, null, sorter);
    }

    // ========================================================================================

    public <T> T getByFieldValue(Class<T> clazz, String fieldName, Serializable fieldValue) {
        List<T> resultList = this.findByFieldValue(clazz, fieldName, fieldValue);
        return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
    }

    public <T> T getByUuid(Class<T> clazz, String uuid) {
        return this.getByFieldValue(clazz, "uuid", uuid);
    }

    public <T> T getByPhoneNum(Class<T> clazz, String phoneNum) {
        return this.getByFieldValue(clazz, "phoneNum", phoneNum);
    }


    public <T> T getByFieldValueMap(Class<T> clazz, Map<String, Object> fieldValueMap) {
        List<T> resultList = this.findByFieldValueMap(clazz, fieldValueMap);
        return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
    }

    public <T> PageResult<T> findPagerByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap, Pager pager) {
        Sorter sorter = new Sorter("id", Sort.Direction.DESC);
        return this.findPagerByFieldValues(clazz, fieldValueMap, pager, sorter);
    }

    public <T> T getFirstSortByFieldValues(Class<T> clazz, Map<String, Object> fieldValueMap, Sorter sorter) {
        List<T> resultList = this.findSortByFieldValues(clazz, fieldValueMap, sorter);

        return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
    }
}

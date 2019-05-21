package com.qzc.util;

import com.google.common.collect.Lists;
import com.qzc.db.service.BaseJpaService;
import com.qzc.env.BaseSpringApplicationContext;
import com.qzc.pojo.BaseIdEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class BaseJOJoinJOUtil {

    public <T, S extends BaseIdEntity> void jionById(List<T> targetList,
                                                     String targetJoinFieldName,
                                                     Class<S> sourceClazz,
                                                     JoinPair... joinPairs) {
        if (CollectionUtils.isEmpty(targetList)) {
            return;
        }

        try {
            Class targetClazz = targetList.get(0).getClass();
            Field targetJoinField = getField(targetJoinFieldName, targetClazz, Long.class);
            if (targetJoinField == null) {
                return;
            }
            //Field targetJoinField = targetClazz.getDeclaredField(targetJoinFieldName);

            List<Long> sourceIdList = Lists.newArrayList();
            for (T t : targetList) {
                targetJoinField.setAccessible(true);
                Long sourceId = (Long) targetJoinField.get(t);
                if (sourceId != null && !sourceIdList.contains(sourceId)) {
                    sourceIdList.add(sourceId);
                }
            }

            BaseJpaService baseJpaService = BaseSpringApplicationContext.getBean(BaseJpaService.class);
            if (baseJpaService == null) {
                return;
            }

            List<S> sourceList = baseJpaService.findInByFieldValues(sourceClazz, "id", sourceIdList);
            if (CollectionUtils.isEmpty(sourceList)) {
                return;
            }

            for (T t : targetList) {
                targetJoinField.setAccessible(true);
                Long sourceId = (Long) targetJoinField.get(t);
                if (sourceId == null) {
                    continue;
                }

                for (S s : sourceList) {
                    if (sourceId.longValue() == s.getId().longValue()) {
                        for (JoinPair jp : joinPairs) {
                            Field targetField = getField(jp.getTargetField(), targetClazz, null);
                            Field sourceField = getField(jp.getSourceField(), sourceClazz, null);
                            //Field sourceField = sourceClazz.getDeclaredField(jp.getSourceField());
                            //Field targetField = targetClazz.getDeclaredField(jp.getTargetField());
                            if (sourceField == null || targetField == null) {
                                continue;
                            }
                            sourceField.setAccessible(true);
                            targetField.setAccessible(true);

                            // 获取原对象中映射字段的值
                            Object sourceFieldValue = sourceField.get(s);

                            // 将原对象中映射字段的值设置到目标对象对应的值中
                            targetField.set(t, sourceFieldValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    private static Field getField(String fieldName, Class<?> clazz, Class<?> fieldType) {
        do {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getName().equals(fieldName)) {
                    if (fieldType == null) {
                        return field;
                    }

                    if (fieldType.isAssignableFrom(field.getType())) {
                        return field;
                    }
                }
            }

            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        return null;
    }

    // =========================================================
    public static class JoinPair {

        private String targetField;

        private String sourceField;

        public JoinPair(String targetField, String sourceField) {
            this.targetField = targetField;
            this.sourceField = sourceField;
        }

        public String getTargetField() {
            return targetField;
        }

        public void setTargetField(String targetField) {
            this.targetField = targetField;
        }

        public String getSourceField() {
            return sourceField;
        }

        public void setSourceField(String sourceField) {
            this.sourceField = sourceField;
        }
    }
}

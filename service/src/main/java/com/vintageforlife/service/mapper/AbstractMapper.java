package com.vintageforlife.service.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractMapper<E, D> implements Mapper<E, D> {
    @Override
    public abstract E toEntity(D dto);

    @Override
    public abstract D toDTO(E entity);

    @Override
    public void addToDTO(Object entity, D dto) {
        Field[] fields = dto.getClass().getDeclaredFields();
        String dtoName = getEntityName(entity) + "DTO";

        for (Field field : fields) {
            if (field.getType().getSimpleName().equals(dtoName)) {
                field.setAccessible(true);
                try {
                    Mapper<Object, Object> mapper = getMapperForEntity(entity);
                    if (mapper != null) {
                        Object entityDTO = mapper.toDTO(entity);
                        field.set(dto, entityDTO);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getEntityName(Object entity) {
        String entityClassName = entity.getClass().getSimpleName();
        int endIndex = entityClassName.indexOf("Entity");
        if (endIndex != -1) {
            entityClassName = entityClassName.substring(0, endIndex);
        }
        return entityClassName;
    }

    protected Mapper<Object, Object> getMapperForEntity(Object entity) {
        String mapperClassNameFull = "com.vintageforlife.service.mapper." + getEntityName(entity) + "Mapper";

        try {
            Class<?> mapperClass = Class.forName(mapperClassNameFull);
            return (Mapper<Object, Object>) mapperClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}

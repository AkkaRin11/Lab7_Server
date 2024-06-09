package org.example.repository;

import org.example.model.LabWork;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Класс для взаимодейстия со структурой, одной для программы
 *
 */

public interface LabWorkRepository {
    boolean add(LabWork labWork, int personId);

    void clear();

    boolean removeById(int id, int personId);

    boolean removeGreater(LabWork labWork, int personId);

    boolean save();

    Set<LabWork> getCollection();

    boolean updateById(LabWork labWork, int id, int personId);

    String getCollectionInfo();

    boolean isExistById(int id);

    int getPersonId(String name, String pass);
}

package org.example.service;

import org.example.model.LabWork;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Слой-менеждер для разделения логики взаимодействия с данными от команд
 *
 */

public interface LabWorkService {

    void add(LabWork lb, int personId);

    void clear();

    boolean removeById(int id, int personId);

    boolean removeGreater(LabWork lb, int personId);

    boolean save();

    Set<LabWork> getCollection();

    boolean addIfMax(LabWork labWork, int personId);

    String getCollectionInfo();

    LinkedHashSet<LabWork> getCollectionByContainsName(String nameFilter);

    LinkedHashSet<LabWork> getCollectionByGreaterMinimalPoint(int minimalPoint);

    boolean updateById(LabWork labWork, int id, int personId);

    long getSumOfAveragePoint();

    boolean isExistById(int id);

    int getPersonId(String name, String pass);

    boolean registration(String name, String pass);

    boolean log(String name, String pass);
}

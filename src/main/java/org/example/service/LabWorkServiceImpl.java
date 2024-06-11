package org.example.service;

import org.example.model.LabWork;
import org.example.repository.DB;
import org.example.repository.LabWorkRepository;
import org.example.repository.LabWorkRepositoryImpl;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 *
 * Реализация LabWorkService
 *
 */

public class LabWorkServiceImpl implements LabWorkService {
    private final LabWorkRepository labWorkRepository;
    private final DB db;

    public LabWorkServiceImpl() {
        labWorkRepository = LabWorkRepositoryImpl.getInstance();
        db = DB.getInstance();
    }

    @Override
    public void add(LabWork lb, int personId) {
        labWorkRepository.add(lb, personId);
    }

    @Override
    public void clear() {
        labWorkRepository.clear();
    }

    @Override
    public boolean removeById(int id, int personId) {
        return labWorkRepository.removeById(id, personId);
    }

    @Override
    public boolean removeGreater(LabWork lb, int personId) {
        return labWorkRepository.removeGreater(lb, personId);
    }

    @Override
    public boolean save() {
        return labWorkRepository.save();
    }

    @Override
    public Set<LabWork> getCollection() {
        return labWorkRepository.getCollection();
    }

    @Override
    public boolean addIfMax(LabWork labWork, int personId) {
        long max = Integer.MIN_VALUE;

        var collection = getCollection();

        for (LabWork to : collection) {
            max = Math.max(max, to.getMinimalPoint());
        }

        if (labWork.getMinimalPoint() > max) {
            add(labWork, personId);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCollectionInfo() {
        return labWorkRepository.getCollectionInfo();
    }

    @Override
    public LinkedHashSet<LabWork> getCollectionByContainsName(String nameFilter) {
        var collection = getCollection();

        LinkedHashSet<LabWork> filteredCollection = new LinkedHashSet<>();

        for (LabWork to : collection) {
            if (to.getName().contains(nameFilter)) {
                filteredCollection.add(to);
            }
        }

        return filteredCollection;
    }

    @Override
    public LinkedHashSet<LabWork> getCollectionByGreaterMinimalPoint(int minimalPoint) {
        var collection = getCollection();

        LinkedHashSet<LabWork> filteredCollection = new LinkedHashSet<>();

        for (LabWork to : collection) {
            if (to.getMinimalPoint() > minimalPoint) {
                filteredCollection.add(to);
            }
        }

        return filteredCollection;
    }

    @Override
    public boolean updateById(LabWork labWork, int id, int personId) {
        return labWorkRepository.updateById(labWork, id, personId);
    }

    public long getSumOfAveragePoint() {
        long sum = 0;

        var collection = getCollection();

        for (LabWork to : collection) {
            sum += to.getAveragePoint();
        }

        return sum;
    }

    public boolean isExistById(int id){
        return labWorkRepository.isExistById(id);
    }

    @Override
    public int getPersonId(String name, String pass) {
        return labWorkRepository.getPersonId(name, pass);
    }

    @Override
    public boolean registration(String name, String pass) {
        return db.registration(name, pass);
    }

    @Override
    public boolean log(String name, String pass) {
        return db.log(name, pass);
    }

    @Override
    public int getPersonIdById(int id) {
        return db.getPersonIdById(id);
    }
}

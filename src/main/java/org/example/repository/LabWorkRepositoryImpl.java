package org.example.repository;

import org.example.model.LabWork;

import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 *
 * Реализация LabWorkRepository
 *
 */

public class LabWorkRepositoryImpl implements LabWorkRepository {
    private static LabWorkRepositoryImpl instance;

    private final LinkedHashSet<LabWork> labWorks;

    private final DB db;

    public static LabWorkRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LabWorkRepositoryImpl();
        }

        return instance;
    }

    private LabWorkRepositoryImpl() {
        db = DB.getInstance();
        try {
            labWorks = (LinkedHashSet<LabWork>) db.getCollection();
        } catch (SQLException e) {
            System.out.println("коллекция не может импортироваться");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(LabWork labWork, int personId) {
        int id;
        try {
            id = db.addLabWork(labWork, personId);
        } catch (SQLException e) {
            return false;
        }
        labWork.setId(id);
        labWorks.add(labWork);

        return true;
    }

    @Override
    public void clear() {
        try {
            db.clear();
            labWorks.clear();
        } catch (SQLException e){
            System.out.println("при удалении произошла ошибка");
        }
    }

    @Override
    public boolean removeById(int id, int personId) {
//        db.removeById(id);

        LabWork labWork = null;

        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                labWork = to;
                flag = true;
                break;
            }
        }

        labWorks.remove(labWork);

        return flag;
    }

    @Override
    public boolean removeGreater(LabWork labWork, int personId) {
//        db.removeGreater(labWork.getMinimalPoint());

        LinkedHashSet<LabWork> lh = new LinkedHashSet<>();

        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getMinimalPoint() > labWork.getMinimalPoint()) {
                lh.add(to);
            }
        }

        for (LabWork to : lh) {
            labWorks.remove(to);
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean save() {
        return true;
    }

    @Override
    public LinkedHashSet<LabWork> getCollection() {
        return labWorks;
    }

    @Override
    public boolean updateById(LabWork labWork, int id, int personId) {
//        db.updateById(id);

        LabWork lb = null;
        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                flag = true;
                lb = to;
                break;
            }
        }

        labWorks.remove(lb);
        labWork.setId(id);
        labWorks.add(labWork);

        return flag;
    }

    @Override
    public String getCollectionInfo() {
        return "LinkedHashSet, size: " + labWorks.size();
    }

    public boolean isExistById(int id){
        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    @Override
    public int getPersonId(String name, String pass) {
        return db.getPersonId(name, pass);
    }

}

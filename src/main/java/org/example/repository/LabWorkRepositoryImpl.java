package org.example.repository;

import org.example.model.LabWork;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Реализация LabWorkRepository
 *
 */

public class LabWorkRepositoryImpl implements LabWorkRepository {
    private static LabWorkRepositoryImpl instance;

    private final Set<LabWork> labWorks;

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
            labWorks = Collections.synchronizedSet((LinkedHashSet<LabWork>) db.getCollection());
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
            System.out.println(e);
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
        try {
            db.removeById(id, personId);
        } catch (SQLException e) {
            return false;
        }

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
        try {
            db.removeGreater(labWork.getMinimalPoint(), personId);
        } catch (SQLException e) {
            return false;
        }

        LinkedHashSet<LabWork> lh = new LinkedHashSet<>();

        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getMinimalPoint() > labWork.getMinimalPoint() && db.getPersonIdById(to.getId()) == personId) {
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
    public Set<LabWork> getCollection() {
        return labWorks;
    }

    @Override
    public boolean updateById(LabWork labWork, int id, int personId) {
        try {
            db.updateById(labWork, id);
        } catch (SQLException e) {
            return false;
        }

        LabWork lb = null;
        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id && db.getPersonIdById(to.getId()) == personId) {
                flag = true;
                lb = to;
                break;
            }
        }

        if (!flag) return false;

        labWorks.remove(lb);
        labWork.setId(id);
        labWorks.add(labWork);

        return true;
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

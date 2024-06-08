package org.example.repository;

import org.example.model.*;
import org.example.utils.Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class DB {
    private static DB instance;
    private Connection connection;

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
            try {
                instance.createConnection();
            } catch (SQLException e) {
                System.out.println("в DB.getInstance упало создание подключения");
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection(Data.url, Data.name, Data.pass);
    }

    public void removeById(long id) throws SQLException {
        String deleteProductSql = "DELETE FROM products WHERE id = ?";
        connection.setAutoCommit(false);
        try (PreparedStatement psProduct = connection.prepareStatement(deleteProductSql)) {
            psProduct.setLong(1, id);
            psProduct.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public Set<LabWork> getCollection() throws SQLException {
        Set<LabWork> result = new LinkedHashSet<>();
        String statement = "SELECT * FROM lab_work";
        PreparedStatement ps = connection.prepareStatement(statement);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            LabWork labWork = new LabWork(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    new Coordinates(
                            resultSet.getDouble("coordinates_x"),
                            resultSet.getLong("coordinates_y")
                    ),
                    LocalDateTime.now(),
                    resultSet.getLong("minimal_point"),
                    resultSet.getLong("average_point"),
                    Difficulty.valueOf(resultSet.getString("difficulty")),
                    new Person(
                            resultSet.getString("author_name"),
                            resultSet.getDate("author_birthday"),
                            resultSet.getLong("author_height"),
                            Color.valueOf(resultSet.getString("author_hairColor")),
                            Country.valueOf(resultSet.getString("author_nationality"))
                    )
                    ); // тут всё вытаскиваем
            result.add(labWork);
        }

        return result;

    }

    public int addLabWork(LabWork labWork, int personId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT INTO lab_work " +
                "(name, coordinates_x, coordinates_y, minimal_point, average_point, difficulty," +
                "author_name, author_birthday, author_height, author_hairColor, author_nationality, prog_user_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ID");

        ps.setString(1, labWork.getName());
        ps.setDouble(2, labWork.getCoordinates().getX());
        ps.setLong(3, labWork.getCoordinates().getY());
        ps.setLong(4, labWork.getMinimalPoint());
        ps.setLong(5, labWork.getAveragePoint());
        ps.setString(6, labWork.getDifficulty().name());
        ps.setString(7, labWork.getAuthor().getName());
        ps.setDate(8, new java.sql.Date(labWork.getAuthor().getBirthday().getTime()));
        ps.setLong(9, labWork.getAuthor().getHeight());
        ps.setString(10, labWork.getAuthor().getHairColor().name());
        ps.setString(11, labWork.getAuthor().getNationality().name());
        ps.setInt(12, personId);


        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public void clear() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE");
        ps.executeQuery();
    }

    public int getPersonId(String name, String pass) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM prog_user WHERE name = ? AND pass = ?");

            ps.setString(1, name);
            ps.setString(2, pass);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("id");
            }

            return -1;
        } catch (SQLException e) {
            System.out.println("Упс, при получении юзера возникла ошибка");
            return -1;
        }
    }

    public boolean registration(String name, String pass) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT * FROM prog_user WHERE name = ?");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            try {
                if (resultSet.next()) {
                    return false;
                }
            } catch (Exception ignored){}

            ps = connection.prepareStatement("INSERT INTO prog_user(name, pass) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.execute();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean log(String name, String pass) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT * FROM prog_user WHERE name = ? AND pass = ?");
            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet resultSet = ps.executeQuery();

            try {
                if (resultSet.next()) {
                    return true;
                }

                return false;
            } catch (SQLException exception){
                return false;
            }


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

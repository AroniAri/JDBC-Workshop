package se.lexicon.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static se.lexicon.JDBC.MySQLConnection.getConnection;

public class CityDaoJDBC implements CityDao {
    private Connection connection;

    public CityDaoJDBC(Connection connection) {
        this.connection = connection;
    }


@Override
    public City findById(int id) {
        String sql = "SELECT * FROM city WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM city WHERE CountryCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM city WHERE Name LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM city";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City add(City city) {
        String sql = "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, city.getName());
            stmt.setString(2, city.getCountryCode());
            stmt.setString(3, city.getDistrict());
            stmt.setInt(4, city.getPopulation());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    city.setId(keys.getInt(1));
                }
            }
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City update(City city) {
        String sql = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city.getName());
            stmt.setString(2, city.getCountryCode());
            stmt.setString(3, city.getDistrict());
            stmt.setInt(4, city.getPopulation());
            stmt.setInt(5, city.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0 ? city : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(City city) {
        String sql = "DELETE FROM city WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, city.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}





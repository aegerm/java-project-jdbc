package model.dao.impl;

import database.ConnectionFactory;
import database.exception.DatabaseException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoImpl implements SellerDAO {

    private Connection connection;

    public SellerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        String sql = "INSERT INTO sellers (name, email, birth_date, base_salary, department_id) values (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setString(i++, seller.getName());
            ps.setString(i++, seller.getEmail());
            ps.setDate(i++, new Date(seller.getBirthDate().getTime()));
            ps.setDouble(i++, seller.getBaseSalary());
            ps.setInt(i++, seller.getDepartment().getId());

            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller seller) {
        String sql = "UPDATE sellers SET name = ?, email = ?, birth_date = ?, base_salary = ?, department_id = ? WHERE id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setString(i++, seller.getName());
            ps.setString(i++, seller.getEmail());
            ps.setDate(i++, new Date(seller.getBirthDate().getTime()));
            ps.setDouble(i++, seller.getBaseSalary());
            ps.setInt(i++, seller.getDepartment().getId());
            ps.setInt(i++, seller.getId());

            ps.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
            ConnectionFactory.closeResultSet(rs);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM sellers WHERE id = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setInt(i++, id);

            ps.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
        }
    }

    @Override
    public Seller findById(Integer id) {
        String sql = "SELECT sellers.*, departments.name AS dptName FROM sellers INNER JOIN departments ON sellers.department_id = departments.id WHERE sellers.id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setInt(i++, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("department_id"));
                department.setName(rs.getString("dptName"));

                Seller seller = new Seller();
                seller.setId(rs.getInt("id"));
                seller.setName(rs.getString("name"));
                seller.setEmail(rs.getString("email"));
                seller.setBirthDate(rs.getDate("birth_date"));
                seller.setBaseSalary(rs.getDouble("base_salary"));
                seller.setDepartment(department);

                return seller;
            }

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
            ConnectionFactory.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public List<Seller> findAll() {
        String sql = "SELECT sellers.*, departments.name AS dptName FROM sellers INNER JOIN departments ON sellers.department_id = departments.id";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department department = map.get(rs.getInt("department_id"));

                if (department == null) {
                    department = new Department();
                    department.setId(rs.getInt("department_id"));
                    department.setName(rs.getString("dptName"));

                    map.put(rs.getInt("department_id"), department);
                }

                Seller seller = new Seller();
                seller.setId(rs.getInt("id"));
                seller.setName(rs.getString("name"));
                seller.setEmail(rs.getString("email"));
                seller.setBirthDate(rs.getDate("birth_date"));
                seller.setBaseSalary(rs.getDouble("base_salary"));
                seller.setDepartment(department);

                sellers.add(seller);
            }

            return sellers;

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
            ConnectionFactory.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        String sql = "SELECT sellers.*, departments.name AS dptName FROM sellers INNER JOIN departments ON sellers.department_id = departments.id WHERE sellers.department_id = ? ORDER BY sellers.name";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setInt(i++, department.getId());

            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department _department = map.get(rs.getInt("department_id"));

                if (_department == null) {
                    _department = new Department();
                    _department.setId(rs.getInt("department_id"));
                    _department.setName(rs.getString("dptName"));

                    map.put(rs.getInt("department_id"), _department);
                }

                Seller seller = new Seller();
                seller.setId(rs.getInt("id"));
                seller.setName(rs.getString("name"));
                seller.setEmail(rs.getString("email"));
                seller.setBirthDate(rs.getDate("birth_date"));
                seller.setBaseSalary(rs.getDouble("base_salary"));
                seller.setDepartment(_department);

                sellers.add(seller);
            }

            return sellers;

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
            ConnectionFactory.closeResultSet(rs);
        }
    }
}

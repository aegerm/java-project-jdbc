package model.dao.impl;

import database.ConnectionFactory;
import database.exception.DatabaseException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDAO {

    private Connection connection;

    public DepartmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        String sql = "INSERT INTO departments (name) values (?)";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setString(i++, department.getName());

            ps.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
        }
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE departments SET name = ? WHERE id = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setString(i++, department.getName());
            ps.setInt(i++, department.getId());

            ps.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM departments WHERE id = ?";

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
    public Department findById(Integer id) {
        String sql = "SELECT * FROM departments WHERE id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);

            int i = 1;

            ps.setInt(i++, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));

                return department;
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
    public List<Department> findAll() {
        String sql = "SELECT * FROM departments";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            List<Department> departments = new ArrayList<>();

            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));

                departments.add(department);
            }

            return departments;

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        finally {
            ConnectionFactory.closeStatement(ps);
            ConnectionFactory.closeResultSet(rs);
        }
    }
}

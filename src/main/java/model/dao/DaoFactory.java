package model.dao;

import database.ConnectionFactory;
import model.dao.impl.DepartmentDaoImpl;
import model.dao.impl.SellerDaoImpl;

public class DaoFactory {

    public static SellerDAO createSellerDao() {
        return new SellerDaoImpl(ConnectionFactory.getConnection());
    }

    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentDaoImpl(ConnectionFactory.getConnection());
    }
}

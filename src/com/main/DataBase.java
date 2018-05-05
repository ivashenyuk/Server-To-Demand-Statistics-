package com.main;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

public class DataBase {
    private static Connection con = null;
    private static String URL = "jdbc:sqlserver://127.0.0.1\\SQLEXPRESS:1433;database=DemandStatistics;integratedSecurity=true";
    private static Statement st = null;
    private ArrayList<DataProduct> productList = new ArrayList<>();

    public DataBase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL);
            if (con != null) System.out.println("Connection Successful!\n");
            if (con == null) return;
            st = con.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        GetStatistic();
    }


    public void GetStatistic() {
        try {
            String queryGetNumberOfTimes = "SELECT * FROM [dbo].[Statistics_table]";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);
            while (rs.next()) {

                DataProduct product = new DataProduct(
                        rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getString(7), rs.getInt(8));
                productList.add(product);
                productList.add(product.GetClone());
            }
            for (int i =0 ; i<productList.size(); i++){
                productList.get(i)._id=i;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public ArrayList<DataProduct> getProductList() {
        return productList;
    }
}

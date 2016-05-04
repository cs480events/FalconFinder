package com.company;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Mark on 3/14/2016.
 */
public class SQLHelper {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://frodo.bentley.edu:3306/cs460teamc";
    private static final String USER = "cs460teamc";
    private static final String PASSWORD = "cs460teamc";
    public static final String ALL = "ALL";
    public static final String ATHLETICS = "ATHLETICS";
    public static final String CAREERS = "CAREERS";
    public static final String OTHER = "OTHER";
    private static final String ALLSQL = "SELECT * FROM eventlist;";

    private static final String ATHLETICSSQL = "SELECT * FROM eventlist " +
            "WHERE MONTH(DATE) = MONTH(now())" +
            " AND YEAR(DATE)=YEAR(now()) " +
            " AND DAY(DATE)>=DAY(now())" +
            " AND CATEGORY='Athletics' ORDER BY DATE ASC; ";

    private static final String CAREERSSQL = "SELECT * FROM eventlist " +
            "WHERE MONTH(DATE) = MONTH(now())" +
            " AND YEAR(DATE)=YEAR(now()) " +
            " AND DAY(DATE)>=DAY(now())" +
            " AND CATEGORY='Careers' ORDER BY DATE ASC; ";

    private static final String OTHERSQL = "SELECT * FROM eventlist " +
            "WHERE MONTH(DATE) = MONTH(now())" +
            " AND YEAR(DATE)=YEAR(now()) " +
            " AND DAY(DATE)>=DAY(now())" +
            " AND CATEGORY!='Athletics' " +
            "AND CATEGORY !='Careers'" +
            "ORDER BY DATE ASC; ";


    /**
     * Constructor
     */
    public SQLHelper() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static String createSQL(String[] sql) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO `cs460teamc`.")
                .append(sql[0])
                .append(" VALUES(");
        for (int x = 1; x < sql.length - 1; x++) {
            if (sql[x] != null) {
                String line = sql[x];
                if (line.contains("'")) {
                    line = line.replace("'", "''");
                }
                builder.append("'" + line + "'" + ",");
            } else {
                builder.append(sql[x] + ",");
            }
        }
        if (sql[sql.length - 1] != null) {
            builder.append("'" + sql[sql.length - 1] + "'");
        } else {
            builder.append(sql[sql.length - 1]);
        }
        builder.append(");");
        return builder.toString();

    }

    /**
     * This method inserts data into the database
     *
     * @param sql
     */
    public static void insertData(String sql) {

        try (
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            int num = statement.executeUpdate(sql);
            System.out.println("You have inserted " + num + " row to the database");
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public static ArrayList<DatabaseEvent> getEvent(String EventType) throws SQLException {
        ArrayList<DatabaseEvent> eventList = new ArrayList<>();
        String sql = "";
        DatabaseEvent dbEvent;
        String summary;
        String category;
        String description;
        String date;
        String start_time;
        String end_time;
        String location;

        switch (EventType) {
            case "ALL":
                sql = SQLHelper.CAREERSSQL;
                break;
            case "ATHLETICS":
                sql = SQLHelper.ATHLETICSSQL;
                break;
            case "CAREERS":
                sql = SQLHelper.CAREERSSQL;
                break;
            case "OTHER":
                sql = SQLHelper.OTHERSQL;
                break;
        }
        System.out.println(sql);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ) {

            while (rs.next()) {
                summary = rs.getString("SUMMARY") != null ? rs.getString("SUMMARY").toString() : "NULL";
                category = rs.getString("CATEGORY") != null? rs.getString("CATEGORY").toString():"NULL";
                description = rs.getString("DESCRIPTION") != null ? rs.getString("DESCRIPTION").toString() : "NULL";
                date = rs.getDate("DATE") != null ? rs.getDate("DATE").toString() : "NULL";
                start_time = rs.getTime("START_TIME")!=null ? rs.getTime("START_TIME").toString() : "NULL";
                end_time = rs.getTime("END_TIME")!=null?rs.getTime("END_TIME").toString():"NULL";
                location = rs.getString("LOCATION")!=null?rs.getString("LOCATION").toString():"NULL";

                eventList.add(new DatabaseEvent(summary, category, description, date, start_time, end_time, location));
            }
        }
        return eventList;
    }
}

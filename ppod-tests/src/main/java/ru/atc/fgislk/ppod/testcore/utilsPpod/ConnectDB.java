package ru.atc.fgislk.ppod.testcore.utilsPpod;


import java.sql.*;


public class ConnectDB {

    private final String pathBD;

    private final String login;

    private final String password;

    public Connection connection;

    public Statement statement;

    /**
     * Подключение к БД с указанием урла и схемы
     *
     * @param pathBD   path
     * @param login    login
     * @param password password
     * @param schema   schema
     */
    public ConnectDB(String pathBD, String login, String password, String schema) throws SQLException {
        this.pathBD = pathBD + String.format("?currentSchema=%s", schema);
        this.login = login;
        this.password = password;

        connection = DriverManager.getConnection(this.pathBD, login, password);
        connection.setAutoCommit(false);

        statement = null;
        statement = connection.createStatement();
    }

    public ConnectDB(String pathBD, String login, String password) throws SQLException {
        this.pathBD = pathBD + String.format("?currentSchema=%s", "public");
        this.login = login;
        this.password = password;

        connection = DriverManager.getConnection(this.pathBD, login, password);
        connection.setAutoCommit(false);

        statement = null;
        statement = connection.createStatement();
    }


    public String getTheLastFileSetId() throws SQLException, InterruptedException {

        ResultSet resultSet = statement.executeQuery("select * from fct_file_set where file_set_id =" +
                " (select file_set_id from fct_file_set_file where file_name = 'package.xml'" +
                " order by create_dttm desc limit 1)");
        resultSet.next();
        return resultSet.getString("file_set_no");
    }

    /**
     * @param key key in message request in kafka
     * @return
     * @throws SQLException
     * @throws InterruptedException
     */
    public Boolean checkInputReceive(String key) throws SQLException, InterruptedException {
        ResultSet resultSet = statement.executeQuery(String.format("select * from fct_request_receive where" +
                " request_key = '%s'", key));
        return resultSet.next();
    }

    public void close() throws SQLException {
        connection.close();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    /**
     * @param query update query (delete, insert, update)
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }
}

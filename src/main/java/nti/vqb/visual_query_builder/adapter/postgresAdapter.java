package nti.vqb.visual_query_builder.adapter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class postgresAdapter implements Adapter{

    @Override
    public Connection openConnection(Map<String, Object> connection) {
        Connection conn = null;
        String host = (String) connection.get("hostname");
        String port = (String) connection.get("port");
        String username = (String) connection.get("username");
        String password = (String) connection.get("password");
        String database = (String) connection.get("database");
        try {
            conn =  DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, username, password);
            if(conn != null){
                System.out.println("Database Connected");
            } else {
                System.out.println("Connection null");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public Map<String, Object> getData(Connection conn, String query) {
        Map<String, Object> results = new HashMap<String, Object>();
        List<HashMap<String, Object>> data = new ArrayList<>();
        List<HashMap<String, Object>> fields = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultMetadata = resultSet.getMetaData();
            for (int i = 1 ; i <= resultMetadata.getColumnCount(); i++){
                HashMap<String, Object> field = new HashMap<>();
                field.put("name", resultMetadata.getColumnName(i));
                field.put("type", resultMetadata.getColumnTypeName(i));
                fields.add(field);
            }
            while (resultSet.next()) {
                HashMap<String, Object> user = new HashMap<>();
                for (int i = 1 ; i <= resultMetadata.getColumnCount(); i++){
                    user.put(resultMetadata.getColumnName(i), resultSet.getString(resultMetadata.getColumnName(i)));
                }
                data.add(user);

            }
            results.put("fields", fields);
            results.put("data", data);
            preparedStatement.close();
//            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<Map<String, Object>> getFields(Connection conn, String table) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getSchema(Connection conn, String catalog) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT schema_name AS name FROM information_schema.schemata WHERE catalog_name = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, catalog);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> field = new HashMap<>();
                field.put("name", resultSet.getString("name"));
                results.add(field);
            }
            preparedStatement.close();
//            conn.close();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public List<Map<String, Object>> getTables(Connection conn, String catalog, String schema) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT table_name AS name FROM information_schema.tables WHERE table_catalog = ?";
        if(schema != null) query = query + " AND table_schema = ? ";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, catalog);
            if(schema != null) preparedStatement.setString(2, schema);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> field = new HashMap<>();
                field.put("name", resultSet.getString("name"));
                results.add(field);
            }
            preparedStatement.close();
//            conn.close();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

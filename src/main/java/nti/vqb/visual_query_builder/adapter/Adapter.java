package nti.vqb.visual_query_builder.adapter;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Adapter {
    public Connection openConnection(Map<String, Object> connection);

    public Map<String, Object> getData(Connection conn, String query);

    public List<Map<String, Object>> getFields(Connection conn, String table);

    public List<Map<String, Object>> getSchema(Connection conn, String catalog);

    public List<Map<String, Object>> getTables(Connection conn, String catalog, String schema);

    public void closeConnection(Connection conn);
}

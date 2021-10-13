package nti.vqb.visual_query_builder.service;

import lombok.Getter;
import lombok.Setter;
import nti.vqb.visual_query_builder.adapter.Adapter;
import nti.vqb.visual_query_builder.adapter.postgresAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AdapterService {
    private static final Logger log = LoggerFactory.getLogger(AdapterService.class);
    private String type;
    private Adapter adapter;

    public AdapterService(String type) {
        this.type = type;
        switch (type){
            case "Postgres" : this.adapter = new postgresAdapter();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public Connection openConnection(Map<String, Object> connection){
        return this.adapter.openConnection(connection);
    };

    public Map<String, Object> getData(Connection conn, String query){
        return this.adapter.getData(conn, query);
    };

    public List<Map<String, Object>> getFields(Connection conn, String table){
        return this.adapter.getFields(conn, table);
    }

    public List<Map<String, Object>> getSchema(Connection conn, String catalog){
        return this.adapter.getSchema(conn, catalog);
    }

    public List<Map<String, Object>> getTables(Connection conn, String catalog, String schema){
        return this.adapter.getTables(conn, catalog, schema);
    }

    public void closeConnection(Connection conn){
        this.adapter.closeConnection(conn);
    };
}

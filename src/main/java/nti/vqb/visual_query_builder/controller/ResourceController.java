package nti.vqb.visual_query_builder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nti.vqb.visual_query_builder.entity.Resource;
import nti.vqb.visual_query_builder.service.AdapterService;
import nti.vqb.visual_query_builder.service.ConnectionService;
import nti.vqb.visual_query_builder.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.sql.Connection;
import java.util.*;

@RestController()
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService service;
    private ConnectionService connService;

    String[] values = {"CSV","JSON"};

    @GetMapping()
    public ResponseEntity<List<Resource>> findAll() {
        try{
            List<Resource> resource = new ArrayList<Resource>();
//            System.out.print(resource);
//            Resource resource1 = new Resource();
//            String[] groupArray = resource.get().getGroup_name() = (List<String>);
            service.findAll().forEach(resource::add);
            if (resource.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }catch(Exception e){
//          return service.findAll();a
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/hello")
    public String helloworld(){
        return "Hello Resource";
    }

    @GetMapping("/{id}")
    public Resource findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping()
    public int insert(@RequestBody Map<String, Object> body) {
        Resource resource = new Resource();
        resource.setName((String) body.get("name"));
        resource.setConnection_id((Integer) body.get("connection_id"));
        resource.setConnection_name((String) body.get("connection_name"));
        resource.setQuery((String) body.get("query"));
        resource.setType((String) body.get("type"));
        resource.setDate_field((String) body.get("date_field"));
        resource.setCreated_at((String) body.get("created_at"));
        resource.setCreated_by((String) body.get("created_by"));

        List<String> group_name = (List<String>) body.get("group_name");
        String groupName = String.join(";", group_name);
        resource.setGroup_name(groupName);

        return service.insert(resource);
    }

    @PutMapping("/{id}")
    public Resource update(@PathVariable int id, @RequestBody Resource resource) {
        Resource dataResource = service.findById(id);
        if(dataResource == null ) throw new NoSuchElementException("Connection Not Found");
        service.update(dataResource);
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Resource dataResource = service.findById(id);
        if(dataResource == null ) throw new NoSuchElementException("Connection Not Found");
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/query")
    public ResponseEntity<StreamingResponseBody> queryData(@RequestBody Map<String, Object> body) {
        int id = Math.toIntExact(Long.parseLong(body.get("id").toString()));
        String query = (String) body.get("query");
        Resource dataResource = service.findById(id);
        nti.vqb.visual_query_builder.entity.Connection dataConnection = findById(dataResource.getConnection_id());
        Integer idConnection = Math.toIntExact(Integer.parseInt(dataResource.getConnection_id().toString()));
        Map<String, Object> connection = (Map<String, Object>) connService.findById(idConnection);
        System.out.println(connection);
        Map<String, Object> result = new HashMap<String, Object>();
        AdapterService adapter = new AdapterService(dataConnection.getDriver());
        Connection conn = adapter.openConnection(connection);
        result = adapter.getData(conn, query);
        adapter.closeConnection(conn);

        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        StreamingResponseBody responseBody = response -> {
            ObjectMapper mapper = new ObjectMapper();
            for (Map<String, Object> row : data) {
                String dataString = mapper.writeValueAsString(row) +"\n";
                response.write(dataString.getBytes());
                response.flush();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(responseBody);
    }
}

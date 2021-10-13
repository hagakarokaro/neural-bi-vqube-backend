package nti.vqb.visual_query_builder.controller;

import nti.vqb.visual_query_builder.entity.Connection;
import nti.vqb.visual_query_builder.service.ConnectionService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


//CRUD
//R GET : / -> Findall /{id} -> Find One
//C POST: /
//UD (PUT, DELETE) : /{id}

@RestController()
@RequestMapping("/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService service;

    @GetMapping()
    public ResponseEntity<List<Connection>> findAll() {
        try{
            List<Connection> koneksi = new ArrayList<Connection>();
            service.findAll().forEach(koneksi::add);
            if (koneksi.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(koneksi, HttpStatus.OK);
        }catch(Exception e){
//          return service.findAll();a
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/hello")
    public String helloworld(){
        return "Hello Connection";
    }

    @GetMapping("/{id}")
    public Connection findById(@PathVariable int id) {

        return service.findById(id);
    }

    @GetMapping("/tree")
    public List<Map<String, Object>> getTree(@RequestBody Map<String, Object> body){
        Long id = Long.parseLong(body.get("resource_id").toString());

        return null;
    }

    @PostMapping()
    public int insert(@RequestBody Connection connection) {
        return service.insert(connection);
//        JSONObject response = new JSONObject();
//        response.put("message", "Successfully Add Data Source");
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Connection update(@PathVariable int id, @RequestBody Connection connection) {
        Connection dataConnection = service.findById(id);
        if(dataConnection == null ) throw new NoSuchElementException("Connection Not Found");
        service.update(dataConnection);
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Connection dataConnection = service.findById(id);
        if(dataConnection == null ) throw new NoSuchElementException("Connection Not Found");
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

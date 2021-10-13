package nti.vqb.visual_query_builder.service;


import nti.vqb.visual_query_builder.entity.Connection;
import nti.vqb.visual_query_builder.exception.ResourceNotFoundException;
import nti.vqb.visual_query_builder.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<Connection> findAll(){
        return connectionRepository.findAll();
    }

    public Connection findById(int id){
        return connectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Connection not found!"));
    }

    public int insert(Connection connection){
        return connectionRepository.save(connection).getId();
    }

    public void delete(int id){
        connectionRepository.deleteById(id);
    }

    public int update(Connection connection) {
        return connectionRepository.save(connection).getId();
    }
}

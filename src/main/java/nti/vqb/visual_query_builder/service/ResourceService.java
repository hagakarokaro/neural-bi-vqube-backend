package nti.vqb.visual_query_builder.service;

import nti.vqb.visual_query_builder.entity.Connection;
import nti.vqb.visual_query_builder.entity.Resource;
import nti.vqb.visual_query_builder.exception.ResourceNotFoundException;
import nti.vqb.visual_query_builder.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> findAll(){

        return resourceRepository.findAll();
    }

    public Resource findById(int id){
        return resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Connection not found!"));
    }

    public int insert(Resource resource){
        return resourceRepository.save(resource).getId();
    }

    public void delete(int id){
        resourceRepository.deleteById(id);
    }

    public int update(Resource resource) {
        return resourceRepository.save(resource).getId();
    }
}

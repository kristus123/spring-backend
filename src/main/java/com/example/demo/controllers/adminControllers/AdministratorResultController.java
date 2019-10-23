package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.ResultResourceAssembler;
import com.example.demo.dtos.ResultDTO;
import com.example.demo.models.ResultModel;
import com.example.demo.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorResultController {

    @Autowired
    ResultService resultService;

    @Autowired
    ResultResourceAssembler assembler;



    @PostMapping("/post/result")
    public ResponseEntity<Resource<ResultModel>> addResult(@RequestBody ResultDTO result) throws URISyntaxException {
        ResultModel resultModel = resultService.create(result);
        Resource<ResultModel> resource = assembler.toResource(resultModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/result/{id}")
    public ResponseEntity<Resource> updateResult(@PathVariable Integer id, @RequestBody ResultDTO result) throws URISyntaxException {
        /*
        if (team == null)
            throw new ElementBadRequestException("Empty JSON object provided");
         */

        ResultModel updated = resultService.update(id, result);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/result/{id}")
    public ResponseEntity<ResultModel> deleteResult(@PathVariable Integer id) {
        ResultModel result = resultService.deleteById(id);
        return ResponseEntity.ok(result);
    }


}

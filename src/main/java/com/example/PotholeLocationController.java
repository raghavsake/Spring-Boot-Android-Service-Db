package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PotholeLocationController {

    private PotholeRepository repository;

    @Autowired
    public PotholeLocationController(PotholeRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PotholeLocation> get(@PathVariable("id") Long id) {
        PotholeLocation pl = repository.findOne(id);
        if (null == pl) {
            return new ResponseEntity<PotholeLocation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PotholeLocation>(pl, HttpStatus.OK);
    }

    @RequestMapping(value = "/pothole/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PotholeLocation> add(@RequestBody PotholeLocation pl) {
        repository.save(pl);
        return get(pl.getId());
    }

    @RequestMapping(value ="/all/potholes", method = RequestMethod.GET)
    public List<PotholeLocation> all() {
        return repository.findAll();
    }
}
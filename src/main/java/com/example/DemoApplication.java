package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@SpringBootApplication
public class DemoApplication {

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Hello!";
  }

  @RequestMapping("/hello")
  @ResponseBody
  String hello() {
    return "Hello from Heroku!";
  }

  private PotholeRepository repository;

  @Autowired
  public DemoApplication(PotholeRepository repository) {
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

  @RequestMapping(value = "/add/{latitude},{longitde}")
  @ResponseBody
  public ResponseEntity<PotholeLocation> add(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) {
    PotholeLocation pl = new PotholeLocation();
    pl.setLatitude(latitude);
    pl.setLongitude(longitude);
    repository.save(pl);
    return new ResponseEntity<PotholeLocation>(pl, HttpStatus.OK);
  }

  @RequestMapping(value="/all")
  public List<PotholeLocation> all() {
    return repository.findAll();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

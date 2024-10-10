package com.csc340.Assignment_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AnimalController.java.
 * Includes all REST API endpoint mappings for the Animal object.
 */
@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService service;

    /**
     * Get all animals.
     * http://localhost:8080/api/animals
     *
     * @return a list of Animal objects.
     */
    @GetMapping
    public List<Animal> getAllAnimals() {
        return service.getAllAnimals();
    }

    /**
     * Get an animal by its ID.
     * http://localhost:8080/api/animals/2
     *
     * @param animalId the unique Id for an Animal.
     * @return One Animal object.
     */
    @GetMapping("/{animalId}")
    public Animal getAnimalById(@PathVariable int animalId) {
        return service.getAnimalById(animalId);
    }

    /**
     * Add a new animal.
     * http://localhost:8080/api/animals --data '{ "name": "Lion", "scientificName": "Panthera leo", "species": "Mammal", "habitat": "Savannah", "description": "Large carnivorous mammal"}'
     *
     * @param animal the new Animal object.
     * @return the new Animal object.
     */
    @PostMapping
    public Animal addNewAnimal(@RequestBody Animal animal) {
        return service.addAnimal(animal);
    }

    /**
     * Update an existing animal.
     * http://localhost:8080/api/animals/2 --data '{ "name": "Lion", "scientificName": "Panthera leo", "species": "Mammal", "habitat": "Savannah", "description": "Large carnivorous mammal"}'
     *
     * @param animalId the unique Animal Id.
     * @param animal   the new update Animal details.
     * @return the updated Animal object.
     */
    @PutMapping("/{animalId}")
    public Animal updateAnimal(@PathVariable int animalId, @RequestBody Animal animal) {
        return service.updateAnimal(animalId, animal);
    }

    /**
     * Delete an existing animal.
     * http://localhost:8080/api/animals/2
     *
     * @param animalId the unique Animal Id.
     * @return the updated list of Animals.
     */
    @DeleteMapping("/{animalId}")
    public void deleteAnimalById(@PathVariable int animalId) {
        service.deleteAnimal(animalId);
    }

    /**
     * Get all animals of a given class.
     * http://localhost:8080/api/animals/class/mammal
     *
     * @param className the class name.
     * @return a list of Animal objects.
     */
    @GetMapping("/class/{className}")
    public List<Animal> getAnimalsByClass(@PathVariable String className) {
        return service.getAnimalsByClass(className);
    }

    /**
     * Get animals whose name contains a string.
     * http://localhost:8080/api/animals/search/blue
     *
     * @param searchKey the search key.
     * @return a list of Animal objects.
     */
    @GetMapping("/search/{searchKey}")
    public List<Animal> searchAnimals(@PathVariable String searchKey) {
        return service.searchAnimals(searchKey);
    }
}
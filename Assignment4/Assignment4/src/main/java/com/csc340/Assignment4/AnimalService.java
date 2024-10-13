package com.csc340.Assignment4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AnimalService.java
 * Centralizes data access to the Animal Database.
 */
@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    /**
     * Fetch all Animals.
     *
     * @return the list of all Animals.
     */
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Fetch a unique animal.
     *
     * @param animalId the unique Animal id.
     * @return a unique Animal object.
     */
    public Animal getAnimalById(int animalId) {
        return animalRepository.findById(animalId).orElse(null);
    }

    /**
     * Fetch all animals of a given class.
     *
     * @param className the class name.
     * @return the list of matching Animals.
     */
    public List<Animal> getAnimalsByClass(String className) {
        return animalRepository.getAnimalsByClass(className);
    }

    /**
     * Fetch all animals whose name contains a string.
     *
     * @param searchKey the search key.
     * @return the list of matching Animals.
     */
    public List<Animal> searchAnimals(String searchKey) {
        return animalRepository.searchAnimals(searchKey);
    }

    /**
     * Add a new Animal to the database.
     *
     * @param animal the new Animal to add.
     * @return the new Animal object.
     */
    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Update an existing Animal.
     *
     * @param animalId the unique Animal Id.
     * @param animal   the new Animal details.
     * @return the updated Animal object.
     */
    public Animal updateAnimal(int animalId, Animal animal) {
        Animal existing = getAnimalById(animalId);
        existing.setName(animal.getName());
        existing.setScientificName(animal.getScientificName());
        existing.setSpecies(animal.getSpecies());
        existing.setHabitat(animal.getHabitat());
        existing.setDescription(animal.getDescription());

        //Technically the 5 lines above are not necessary because the save method merges by default.
        return animalRepository.save(existing);
    }

    /**
     * Delete a unique Animal.
     *
     * @param animalId the unique Animal Id.
     */
    public void deleteAnimal(int animalId) {
        animalRepository.deleteById(animalId);
    }
}
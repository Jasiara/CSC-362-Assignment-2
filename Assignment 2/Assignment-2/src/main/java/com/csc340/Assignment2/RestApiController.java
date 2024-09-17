package com.csc340.Assignment2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestApiController {
    // Define the database as a map of Pokemon objects
    Map<Integer, pokemon> pokemonDatabase = new HashMap<>();

    /**
     * Hello World API endpoint.
     *
     * @return response string.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hey luv!";
    }

    /**
     * Get a list of pokemon from the official pokemon database and make them available at our own API endpoint.
     *
     * @return the list of pokemon.
     */
    @GetMapping("/pokeapi")
    public Object getAllPokemon() {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon?limit=100";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            // Fetch the list of pokemon
            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            JsonNode results = root.get("results");
            List<Map<String, Object>> pokemonList = new ArrayList<>();

            for (JsonNode rt : results) {
                String pokemonUrl = rt.get("url").asText();
                String pokemonName = rt.get("name").asText();

                // Fetch individual Pokemon details
                String pokemonDetails = restTemplate.getForObject(pokemonUrl, String.class);
                JsonNode pokemonNode = mapper.readTree(pokemonDetails);

                int id = pokemonNode.get("id").asInt();

                JsonNode typesNode = pokemonNode.get("types");
                List<String> types = new ArrayList<>();
                for (JsonNode typeNode : typesNode) {
                    String type = typeNode.get("type").get("name").asText();
                    types.add(type);
                }

                Map<String, Object> pokemonData = new HashMap<>();
                pokemonData.put("name", pokemonName);
                pokemonData.put("id", id);
                pokemonData.put("types", types);

                pokemonList.add(pokemonData);
            }

            return pokemonList;
        } catch (Exception ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, "Error fetching Pokémon data", ex);
            return "Error fetching Pokémon data.";
        }
    }

    /**
     * List all pokemon.
     *
     * @return the list of pokemon.
     */
    @GetMapping("/pokemon/all")
    public Object getPokemon() {
        if (pokemonDatabase.isEmpty()) {
            pokemonDatabase.put(1, new pokemon(1, "pika", "lightning"));
        }
        return pokemonDatabase.values();
    }

    /**
     * Get one pokemon by Id.
     *
     * @param id the unique pokemon id.
     * @return the pokemon.
     */
    @GetMapping("/pokemon/{id}")
    public pokemon getPokemonById(@PathVariable int id) {
        return pokemonDatabase.get(id);
    }

    /**
     * Create a new pokemon entry.
     *
     * @param pokemon the new pokemon
     * @return the list of pokemon.
     */
    @PostMapping("/pokemon/create")
    public Object createPokemon(@RequestBody pokemon pokemon) {
        pokemonDatabase.put(pokemon.getId(), pokemon);
        return pokemonDatabase.values();
    }

    /**
     * Delete a pokemon by id.
     *
     * @param id the id of pokemon to be deleted.
     * @return the list of pokemon.
     */
    @DeleteMapping("/pokemon/delete/{id}")
    public Object deletePokemon(@PathVariable int id) {
        pokemonDatabase.remove(id);
        return pokemonDatabase.values();
    }
}

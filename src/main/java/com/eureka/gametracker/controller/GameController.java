package com.eureka.gametracker.controller;

import com.eureka.gametracker.entity.Game;
import com.eureka.gametracker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "game/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("game", new Game());
        return "game/add";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute Game game) {

        gameService.saveGame(game);
        return "redirect:/games";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Game game = gameService.getGameById(id);
        model.addAttribute("game", game);
        return "game/edit";
    }

    @PostMapping("/edit")
    public String editGame(@ModelAttribute Game game) {
        gameService.saveGame(game);
        return "redirect:/games";
    }

    @GetMapping("/delete/{id}")
    public String deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return "redirect:/games";
    }

    @GetMapping("/steamApps")
    public ResponseEntity<String> getSteamApps() {
        String url = "https://api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/steamAppsIcon/{appid}")
    public ResponseEntity<String> getSteamAppsIcon(@PathVariable String appid) {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appid;
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}
package com.roxanna.clienteservidor.RESTcontrollers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {

    @PostMapping("/location")
    public void saveLocation(@RequestBody Map<String, Object> data, HttpSession session) {
        session.setAttribute("lat", data.get("lat"));
        session.setAttribute("lng", data.get("lng"));
        session.setAttribute("address", data.get("address"));
    }

    @GetMapping("/location")
    public Map<String, Object> getLocation(HttpSession session) {
        return Map.of(
            "lat", session.getAttribute("lat"),
            "lng", session.getAttribute("lng"),
            "address", session.getAttribute("address")
        );
    }
}
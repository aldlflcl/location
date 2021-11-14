package com.aldlflcl.location.web.location;

import com.aldlflcl.location.domain.location.Location;
import com.aldlflcl.location.domain.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LocationRepository repository;

    @GetMapping("/")
    public String home(Model model) {

        List<Location> list = repository.locationList();

        model.addAttribute("list", list);

        return "main/home";
    }

}

package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.service.StoreService;
import me.alex.store.rest.dto.StoreDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {
    private final StoreService storeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createStore(Principal principal, @RequestBody @Valid StoreDetails storeDetails) {
        storeService.createProductStore(principal.getName(), storeDetails);

        return "Store created successfully";
    }

    @GetMapping
    public List<StoreDto> findStores(Principal principal) {
        return storeService.findStores(principal.getName());
    }

    @GetMapping("/all")
    public List<StoreDto> findAll() {
        return storeService.findAll();
    }
}

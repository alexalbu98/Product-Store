package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.service.StoreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store", consumes = MediaType.APPLICATION_JSON_VALUE)
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public String createStore(Principal principal, @RequestBody @Valid StoreDetails storeDetails) {
        storeService.createProductStore(principal.getName(), storeDetails);

        return "Store created successfully";
    }
}

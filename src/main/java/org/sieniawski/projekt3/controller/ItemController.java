package org.sieniawski.projekt3.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.sieniawski.projekt3.dto.ItemDto;
import org.sieniawski.projekt3.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final Validator validator;

    public ItemController(ItemService itemService, Validator validator) {
        this.itemService = itemService;
        this.validator = validator;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createItems(@RequestBody List<ItemDto> itemDtos) {
        List<ItemDto> validItems = itemDtos.stream()
                .filter(item -> {
                    Set<ConstraintViolation<ItemDto>> violations = validator.validate(item);
                    return violations.isEmpty();
                })
                .collect(Collectors.toList());

        List<String> errorMessages = itemDtos.stream()
                .flatMap(item -> validator.validate(item).stream()
                        .map(violation -> item.getName() + ": " + violation.getMessage()))
                .collect(Collectors.toList());

        return itemService.saveAll(validItems)
                .map(savedCount -> {
                    String responseMessage =  savedCount + " correct records saved";
                    if (!errorMessages.isEmpty()) {
                        responseMessage += " incorrect data: " + String.join("; ", errorMessages);
                    }
                    return ResponseEntity.ok(responseMessage);
                })
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("server error occurred: " + ex.getMessage())));
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> testEndpoint() {
        System.out.println(">>> Endpoint /api/items/test has been called out!");
        return Mono.just(ResponseEntity.ok("The service works correctly on port 8081"));
    }

}

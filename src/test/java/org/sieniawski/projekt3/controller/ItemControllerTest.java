package org.sieniawski.projekt3.controller;

import org.junit.jupiter.api.Test;
import org.sieniawski.projekt3.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ItemControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateItems() {
        List<ItemDto> itemDtos = List.of(
                new ItemDto("Item1", 100),
                new ItemDto("Item2", 200),
                new ItemDto("Item3", 300)
        );

        webTestClient.post().uri("/api/items")
                .bodyValue(itemDtos)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("3 correct records saved");
    }

    @Test
    void testInvalidItemName() {
        ItemDto invalidItemDto = new ItemDto( "", 100);

        webTestClient.post()
                .uri("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(invalidItemDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("0 correct records saved incorrect data: : Name cannot be empty");

    }

    @Test
    void testInvalidItemValue() {
        ItemDto invalidItemDto = new ItemDto( "test", -100);

        webTestClient.post()
                .uri("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(invalidItemDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("0 correct records saved incorrect data: test: Value must be greater than zero");

    }

    @Test
    void testMixBeadAndGodItems(){
        List<ItemDto> itemDtos = List.of(
                new ItemDto("Item1", 100),
                new ItemDto("ItemEror", -200),
                new ItemDto("", 500),
                new ItemDto("Item3", 300)
        );

        webTestClient.post().uri("/api/items")
                .bodyValue(itemDtos)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("2 correct records saved incorrect data: ItemEror: Value must be greater than zero; : Name cannot be empty");
    }


}

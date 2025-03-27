package org.sieniawski.projekt3.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sieniawski.projekt3.dto.ItemDto;
import org.sieniawski.projekt3.model.Item;
import org.sieniawski.projekt3.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository repository;


    @Transactional
    public Mono<Long> saveAll(List<ItemDto> itemDtos) {

        List<Item> items = itemDtos.stream()
                .map(dto -> new Item(null,dto.getName(), dto.getValue()))
                .collect(Collectors.toList());

        return Flux.fromIterable(items)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::saveItem)
                .doOnNext(e -> log.info("Saved item: {}", e))
                .sequential()
                .count();
    }


    private Mono<Item> saveItem(Item item) {
        return Mono.fromCallable(() ->
                repository.save(item)
        ).subscribeOn(Schedulers.boundedElastic());
    }
}

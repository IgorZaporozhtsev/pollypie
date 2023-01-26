package com.zeecoder.recipient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeecoder.domains.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/video-store")
public class OrderController {

    private final OrderRepo repo;

    @PostMapping
    public void saveOrder(@RequestBody Order order) throws JsonProcessingException {

       /* Movie scaryMovie = Movie.builder()
                .title("Scary Movie")
                .build();

        VideoStore videoStore1 = VideoStore.builder()
                .name("second order")
                .videoInventory(Map.of(scaryMovie, 1))
                .build();

        JSONPObject jsonpObject = new JSONPObject("", scaryMovie);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ow.writeValueAsString(scaryMovie);*/

        repo.save(order);
    }
}

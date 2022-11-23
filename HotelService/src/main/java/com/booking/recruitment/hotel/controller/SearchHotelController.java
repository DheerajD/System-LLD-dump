package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchHotelController {
    private final HotelService hotelService;

    @Autowired
    public SearchHotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @RequestMapping(value = "{cityId}/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getClosestCityHotels(@PathVariable Long cityId, @RequestParam("sortBy") String sortBy) {
        return hotelService.getClosestHotels(cityId, sortBy);
    }

}

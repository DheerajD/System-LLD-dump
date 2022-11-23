package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Optional<Hotel> getHotel(Long hotelId);

  void deleteHotel(Long hotelId);

  List<Hotel> getClosestHotels(Long cityId, String sortBy);

  Hotel createNewHotel(Hotel hotel);
}

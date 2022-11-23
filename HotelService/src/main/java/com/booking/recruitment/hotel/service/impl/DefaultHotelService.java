package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Hotel> getHotel(Long hotelId) {
    Optional<Hotel> hotel = hotelRepository.findById(hotelId);
    if(!hotel.isPresent()){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return hotel;
  }

  @Override
  public void deleteHotel(Long hotelId) {
    try {
      hotelRepository.deleteById(hotelId);
    } catch(EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public List<Hotel> getClosestHotels(Long cityId, String sortBy) {
    if(!sortBy.equalsIgnoreCase("distance")){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    List<Hotel> hotelsInSameCity = getHotelsByCity(cityId);
    HashMap<Long, Double> hotelDistanceMap = new HashMap<>();
    List<Double> distanceList = hotelsInSameCity.stream().map(hotel -> {
      Double d = getHaversineDist(hotel.getCity().getCityCentreLatitude(), hotel.getCity().getCityCentreLongitude(),
              hotel.getLatitude(), hotel.getLongitude());
      hotelDistanceMap.put(hotel.getId(),d);
      return d;
    }).collect(Collectors.toList());

    Set set = hotelDistanceMap.entrySet();
    List<Map.Entry<Long, Double>> list = new ArrayList<Map.Entry<Long, Double>>(set);
    Collections.sort(list, new Comparator<Map.Entry<Long, Double>>() {
      @Override
      public int compare(Map.Entry<Long, Double> o1, Map.Entry<Long, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
      }
    });

    List<Hotel> closestHotels = new ArrayList<>();
    int count = 0;
    for(Map.Entry<Long, Double> entry : list) {
      closestHotels.add(getHotel(entry.getKey()).get());
      count++;
      if(count>2)
        break;
    }
    return closestHotels;
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  double getHaversineDist(double lat1, double lon1, double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);

    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double temp = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
    double rad = 6371;
    double res2 = 2 * Math.asin(Math.sqrt(temp));
    return rad * res2;
  }

  /*private void printMap(HashMap<Long, Double> map) {
    System.out.println("Printing hashMap below: ");
    map.entrySet().forEach(entry -> {
      System.out.println("Hello123 : " + entry.getKey() + ", " + entry.getValue());
    });
  }*/
}

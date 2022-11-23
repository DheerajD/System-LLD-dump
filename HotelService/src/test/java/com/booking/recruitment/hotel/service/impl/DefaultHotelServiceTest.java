package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHotelServiceTest {
    public static final String HOTEL_1_ADDRESS = "Weesperbuurt en Plantage";
    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 99L;

    @InjectMocks
    DefaultHotelService hotelService;

    @Mock
    HotelRepository hotelRepository;

    @Test
    public void getHotel_hotelId_Success_test(){
        Hotel hotel = new Hotel();
        hotel.setAddress(HOTEL_1_ADDRESS);
        Mockito.when(hotelRepository.findById(VALID_ID)).thenReturn(Optional.of(hotel));
        assertThat(hotelService.getHotel(VALID_ID).get().getAddress()).isEqualTo(hotel.getAddress());
    }

    @Test(expected = ResponseStatusException.class)
    public void getHotel_hotelId_ResponseStatusException_test() {
        Hotel hotel = new Hotel();
        Mockito.when(hotelRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        hotelService.getHotel(INVALID_ID);
    }
}
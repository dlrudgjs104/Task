package com.nhnacademy.exam.hotel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.exam.hotel.dto.request.RoomCreateRequest;
import com.nhnacademy.exam.hotel.dto.response.RoomCreateResponse;
import com.nhnacademy.exam.hotel.dto.response.RoomGetResponse;
import com.nhnacademy.exam.hotel.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomController {
	private final RoomService roomService;

	@GetMapping("/v1/hotel-api/hotels/{hotel-id}/rooms")
	public ResponseEntity<List<RoomGetResponse>> hotelRooms(@PathVariable("hotel-id") Long hotelId) {
		return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomsByHotelId(hotelId));
	}

	@PostMapping("/v1/hotel-api/hotels/{hotel-id}/rooms")
	public ResponseEntity<RoomCreateResponse> createRoom(@PathVariable("hotel-id") Long hotelId,
		@RequestBody RoomCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(hotelId, request));
	}

}
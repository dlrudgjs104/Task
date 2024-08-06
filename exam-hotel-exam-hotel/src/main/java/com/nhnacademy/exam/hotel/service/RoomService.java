package com.nhnacademy.exam.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.exam.hotel.domain.Hotel;
import com.nhnacademy.exam.hotel.domain.Room;
import com.nhnacademy.exam.hotel.dto.request.RoomCreateRequest;
import com.nhnacademy.exam.hotel.dto.response.RoomCreateResponse;
import com.nhnacademy.exam.hotel.dto.response.RoomGetResponse;
import com.nhnacademy.exam.hotel.exception.HotelNotFoundException;
import com.nhnacademy.exam.hotel.repository.HotelRepository;
import com.nhnacademy.exam.hotel.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
	private final RoomRepository roomRepository;
	private final HotelRepository hotelRepository;

	// RoomResponse 클래스는 Room Entity 객체를 클라이언트에게 응답하기 위한 DTO 입니다.
	// 객실 정보 조회 API 명세서의 Response 양식을 보시고 적절한 형태로 RoomResponse 클래스를 만들어주세요.
	// JSON message 의 viewType 속성은 미리 제공한 ViewType enum의 parameter 값을 사용해야 합니다.
	// Hint. javax.persistence.AttributeConverter 인터페이스와 @Convert 애너테이션을 사용하면 됩니다.

	@Transactional(readOnly = true)
	public List<RoomGetResponse> getRoomsByHotelId(Long hotelId) {
		return roomRepository.findAllByHotelHotelId(hotelId).stream().map(RoomGetResponse::fromEntity).toList();
	}

	public RoomCreateResponse createRoom(Long hotelId, RoomCreateRequest request) {
		Hotel hotel = hotelRepository.findById(hotelId)
			.orElseThrow(() -> new HotelNotFoundException("존재 하지 않는 호텔입니다."));
		Room room = roomRepository.save(Room.toEntity(request, hotel));

		return RoomCreateResponse.fromEntity(room);
	}
}

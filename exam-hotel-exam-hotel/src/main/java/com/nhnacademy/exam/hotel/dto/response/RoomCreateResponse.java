package com.nhnacademy.exam.hotel.dto.response;

import com.nhnacademy.exam.hotel.domain.Room;

import lombok.Builder;

@Builder
public class RoomCreateResponse {
	Long id;

	public static RoomCreateResponse fromEntity(Room room) {
		return RoomCreateResponse.builder()
			.id(room.getRoomId())
			.build();
	}
}

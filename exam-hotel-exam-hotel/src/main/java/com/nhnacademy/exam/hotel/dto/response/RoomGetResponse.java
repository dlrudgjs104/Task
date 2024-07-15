package com.nhnacademy.exam.hotel.dto.response;

import java.time.LocalDateTime;

import com.nhnacademy.exam.hotel.domain.Room;
import com.nhnacademy.exam.hotel.domain.ViewType;

import lombok.Builder;

@Builder
public class RoomGetResponse {
	Long id;
	String name;
	Integer capacity;
	Integer floor;
	boolean hasBathtub;
	String viewType;
	LocalDateTime createdAt;

	public static RoomGetResponse fromEntity(Room room) {
		return RoomGetResponse.builder()
			.id(room.getRoomId())
			.name(room.getRoomName())
			.capacity(room.getRoomCapacity())
			.floor(room.getRoomFloor())
			.hasBathtub(room.getRoomBathtubFlag() == 1)
			.viewType(ViewType.fromDbValue(room.getRoomViewType()).toString())
			.createdAt(room.getRoomCreatedAt())
			.build();
	}
}

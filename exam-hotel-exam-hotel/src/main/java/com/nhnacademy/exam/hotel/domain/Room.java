package com.nhnacademy.exam.hotel.domain;

import java.time.LocalDateTime;

import com.nhnacademy.exam.hotel.dto.request.RoomCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "rooms")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long roomId;

	@Column(name = "name", nullable = false)
	private String roomName;

	@Column(name = "capacity", nullable = false)
	private Integer roomCapacity;

	@Column(name = "floor", nullable = false)
	private Integer roomFloor;

	@Column(name = "bathtub_flag", nullable = false)
	private Integer roomBathtubFlag;

	@Column(name = "view_type", nullable = false)
	private Integer roomViewType;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime roomCreatedAt = LocalDateTime.now();

	@ManyToOne(optional = false)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@Builder
	public Room(String roomName, Integer roomCapacity, Integer roomFloor, Integer roomViewType,
		Integer roomBathtubFlag, Hotel hotel) {
		this.roomName = roomName;
		this.roomCapacity = roomCapacity;
		this.roomFloor = roomFloor;
		this.roomBathtubFlag = roomBathtubFlag;
		this.roomViewType = roomViewType;
		this.roomCreatedAt = LocalDateTime.now();
		this.hotel = hotel;
	}

	public static Room toEntity(RoomCreateRequest request, Hotel hotel) {
		return Room.builder()
			.roomName(request.getName())
			.roomCapacity(request.getCapacity())
			.roomFloor(request.getFloor())
			.roomBathtubFlag(request.isHasBathtub() ? 1 : 0)
			.roomViewType(ViewType.fromParameter(request.getViewType()).getDbValue())
			.hotel(hotel)
			.build();
	}

}

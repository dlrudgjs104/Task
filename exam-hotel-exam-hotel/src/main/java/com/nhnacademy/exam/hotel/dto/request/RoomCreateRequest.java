package com.nhnacademy.exam.hotel.dto.request;

import lombok.Getter;

@Getter
public class RoomCreateRequest {
	String name;
	Integer capacity;
	Integer floor;
	boolean hasBathtub;
	String viewType;
}

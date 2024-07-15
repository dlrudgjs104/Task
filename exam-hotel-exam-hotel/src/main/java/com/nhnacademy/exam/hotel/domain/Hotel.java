package com.nhnacademy.exam.hotel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "hotels")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private Long hotelId;

	@Column(name = "hotel_name", nullable = false)
	private String hotelName;

	public Hotel(String hotelName) {
		this.hotelName = hotelName;
	}
}

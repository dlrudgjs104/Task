package com.nhnacademy.exam.hotel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "rooms_and_images")
public class RoomImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_image_id")
	private Long roomImageId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToOne(optional = false)
	@JoinColumn(name = "image_id")
	private Image image;
}

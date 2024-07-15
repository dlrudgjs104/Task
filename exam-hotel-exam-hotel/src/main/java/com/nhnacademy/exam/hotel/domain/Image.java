package com.nhnacademy.exam.hotel.domain;

import java.time.LocalDateTime;

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
@Table(name = "images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "image_name", nullable = false)
	private String imageName;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@Column(name = "image_created_at", nullable = false)
	private LocalDateTime imageCreatedAt = LocalDateTime.now();
}

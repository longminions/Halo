package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users")
public class User implements UserDetails {


	@Id // Đánh dấu trường này là khóa chính
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
	@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "NEEDS_RELAX_NOTIFICATION")
	private boolean needsRelaxNotification;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	@Override
	public String getPassword() {
		return null;
	}
	
	@Override
	public String getUsername() {
		return null;
	}
}

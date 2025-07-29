package com.longtv.halo.serviceimpl;

import com.longtv.halo.entity.User;
import com.longtv.halo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// Hàm loadUserByUsername là bắt buộc phải override
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Tìm user theo username (hoặc email,...)
		User user = userRepository.findByFirstName(username)
				            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		
		// Trả về một implementation của UserDetails
		return build(user);
	}
	
	public static UserDetails build(User user) {
		return new User();
	}
}

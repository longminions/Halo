package com.longtv.halo.component;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StandardOfDatatypeService {
	private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
	

}

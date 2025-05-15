package com.users.app.service;

import java.util.Optional;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.users.app.dto.UserDto;
import com.users.app.enums.Gender;
import com.users.app.enums.Role;
import com.users.app.enums.Specialization;
import com.users.app.exceptions.EmailAlreadyExistsException;
import com.users.app.exceptions.PhoneNumberAlreadyExistsException;
import com.users.app.exceptions.UserNotFoundException;
import com.users.app.feignClient.AvailabilityFeignClient;
import com.users.app.model.Doctor;
import com.users.app.model.Patient;
import com.users.app.model.User;
import com.users.app.model.UserPrincipal;
import com.users.app.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private JWTService jwtService;
    
    @Lazy
	@Autowired
	private AuthenticationManager authManager;
 
	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AvailabilityFeignClient availability;
	
    @Transactional
    public void signUp(UserDto userDto) {
        User user = new User();
        user.setPhoneNumber(userDto.getPhoneNumber());
        
        if(!userDto.getRole().equals(Role.DOCTOR) && !userDto.getRole().equals(Role.PATIENT)) {
        	throw new IllegalArgumentException("Role Does not Exists");
        }
        if(userDto.getRole().equals(Role.DOCTOR) && userDto.getDoctor() == null){
        	throw new IllegalArgumentException("Please Provide Doctor Details");
        }
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        if (userDto.getRole().equals(Role.DOCTOR) && userDto.getDoctor() != null) {
        		if(userDto.getDoctor().getName() == null) {
        			throw new IllegalArgumentException("Doctor name Should not be NULL");
        		}
        		if(userDto.getDoctor().getSpecialization() == null) {
        			throw new IllegalArgumentException("Doctor Specialization Should not be NULL");
        		}
        		boolean isValidSpecialization = Arrays.stream(Specialization.values())
                        .anyMatch(s -> s.name().equalsIgnoreCase(userDto.getDoctor().getSpecialization().toString()));

				if (!isValidSpecialization) {
				throw new IllegalArgumentException("Invalid Specialization: " + userDto.getDoctor().getSpecialization());
				}
            user.setDoctor(userDto.getDoctor());
        }
        if(userDto.getRole().equals(Role.PATIENT) && userDto.getPatient() == null){
        	throw new IllegalArgumentException("Please Provide Patient Details");
        }
        if (userDto.getRole().equals(Role.PATIENT) && userDto.getPatient() != null) {
        	
        	if(userDto.getPatient().getName() ==null)
        	{
        		throw new IllegalArgumentException("Patient Name Should not be NULL");
        	}
        	if(userDto.getPatient().getAge() ==null) {
        		throw new IllegalArgumentException("Patient Age Should not be NULL");
        	}
        	if(userDto.getPatient().getGender() ==null) {
        		throw new IllegalArgumentException("Patient Gender Should not be NULL");
        	}
        	if(!userDto.getPatient().getGender().equals(Gender.MALE) && !userDto.getPatient().getGender().equals(Gender.FEMALE)) {
            	throw new IllegalArgumentException("Gender Does not Exists");
            }
            user.setPatient(userDto.getPatient());
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number " + user.getPhoneNumber() + " already exists");
        }
        if (user.getRole().equals(Role.DOCTOR)) {
            Doctor doctor = user.getDoctor();
            doctor.setUserDoctor(user);
            user.setDoctor(doctor);
            userRepository.save(user);
            
        } else if (user.getRole().equals(Role.PATIENT)) {
            Patient patient = user.getPatient();
            patient.setUserPatient(user);
            user.setPatient(patient);
            userRepository.save(user);
        }
    }

    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean phoneNumberExist(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

//    public Optional<User> signIn(String identifier, String password) {
//        Optional<User> user = userRepository.findByEmail(identifier);
//        if (!user.isPresent()) {
//            user = userRepository.findByPhoneNumber(identifier);
//        }
//        if (user.isEmpty()) {
//            throw new UserNotFoundException("No user found with " + identifier);
//        }
//        if (!user.get().getPassword().equals(password)) {
//            throw new PasswordMismatchException("Incorrect password");
//        }
//        return user;
//    }
    
    public String verify(String email, String password, Role role) {
	    Authentication authentication = authManager
	            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
	    
	    if (!authentication.isAuthenticated()) {
	        throw new UserNotFoundException("Invalid Login Credentials!");
	    }
	    User authUser = userRepository.findByEmail(email).get();
 
	    if (authUser.getRole() != role) {
	        throw new UserNotFoundException("Forbidden! You don't have access!");
	    }
	    
	    return jwtService.getToken(authUser.getUserId(), role, email);
	}

    public Optional<User> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    @Override
	public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(useremail);
 
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found....");
		}
		
		return new UserPrincipal(user.get());
	}
}
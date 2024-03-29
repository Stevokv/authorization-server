package spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.model.AuthUserDetail;
import spring.model.User;
import spring.repository.UserDetailRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userDetailRepository.findByUsername(username);
		
		optionalUser.orElseThrow(()-> new UsernameNotFoundException("Username or password wrong"));
		
		UserDetails userDetails = new AuthUserDetail(optionalUser.get());
	    System.out.print("Korisnik: " +  userDetails.getUsername() + " " + userDetails.getPassword());
		
		new AccountStatusUserDetailsChecker().check(userDetails);
		
		return userDetails;
	}
}

package com.example.springauthen;

import com.example.springauthen.user.api.RoleRepository;
import com.example.springauthen.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * -------------- AUTHENTICATION --------------
 *
 * - JwtTokenUtil:
 *
 *	    + create secret key (put in application.properties file), expire duration (24h)
 *
 *	    + create 3 methods:
 *		              -> String generateAccessToken(User user)
 *		              -> boolean validateAccessToken(String token): parse token and catch exception
 *		              -> String getSubject(String token): get info from token
 *
 *
 * - JwtTokenFilter -> extends OncePerRequestFilter:
 *
 *	    + inject JwtTokenUtil
 *
 *	    + override method:
 *		                doFilterInternal(request, response, filterchain):
 *                                             - check given token is not empty or bearer token
 *											   - get access token
 *											   - validate token (use method in jwtTokenUtil)
 *											   - set authentication for current user, add it to Spring security context
 *
 *
 * - ApplicationSecurityConfig -> extends WebSecurityConfigurerAdapter: @EnableWebSecurity
 *
 *	    + inject jwtTokenFilter, userService
 *
 *	    + override 2 config methods:
 *                                  -> configure (HttpSecurity http): manage http request, allow permission for each one
 *					                -> configure (AuthenticationManagerBuilder auth): provide UserService for Spring Security
 *
 *	    + override method: AuthenticationManager authenticationManagerBean() -> get authentication bean
 *
 *
 * - User -> implement UserDetails:
 *	    + override methods: return true / null;
 *
 *
 * --------------- AUTHORIZATION ----------------
*
* - User:
*	    + modify method getAuthorities(): set a List<SimpleGrantedAuthority> from user roles and return it
*
* - Role:
*	    + @ManyToMany relationship to User
*	    + role name: follow a convention: [ ROLE_yourCustomRoleHere ]
*
* - JwtTokenUtil:
*	    + generateAccessToken(User user): add more role claim to token
*
* - JwtTokenFilter:
*	    + getUserDetails(String accessToken): set role to user that we get from token
*	    + setAuthenticationContext(accessToken, request): set user authorities to authentication
*
* - ApplicationSecurityConfig:
*	    + add more @EnableGlobalMethodSecurity (jsr250Enabled = true)
*
* - ProductController (API):
*	    + add @RolesAllowed({suitable_role_here}) to each api
*
*/


@SpringBootApplication
public class SpringAuthenApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        Role admin = new Role("Admin");
//        Role user = new Role("User");
//        roleRepository.saveAll(List.of(admin,user));

//		encoder = passwordEncoder();
//		String rawPassword = "12345";
//		String encodedPass = encoder.encode(rawPassword);
//		userRepository.save(new User("tony",encodedPass));

//        User user = userRepository.findById(2).get();
//        user.getRoles().add(new Role(2));
//        userRepository.save(user);

    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

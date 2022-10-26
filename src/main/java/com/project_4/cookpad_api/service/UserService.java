package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Role;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.dto.CredentialDto;
import com.project_4.cookpad_api.entity.dto.RegisterDto;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.RoleRepository;
import com.project_4.cookpad_api.repository.UserRepository;
import com.project_4.cookpad_api.search.UserSpecification;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.search.SearchCriteria;
import com.project_4.cookpad_api.util.ConvertDateHelper;
import com.project_4.cookpad_api.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.project_4.cookpad_api.search.SearchCriteriaOperator.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public boolean matchPassword(String rawPassword, String hashPassword){
        return passwordEncoder.matches(rawPassword, hashPassword);
    }

    public String encodePassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(username, Status.ACTIVE);
        User user = userOptional.orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public CredentialDto generateCredential(UserDetails userDetail, HttpServletRequest request) {
        String accessToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 7);

        String refreshToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 14);
        return new CredentialDto(accessToken, refreshToken);
    }

    public User register(RegisterDto registerDto){
        Role role = roleRepository.findByName("USER");
        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .fullName(registerDto.getFullName())
                .address(null)
                .phone(null)
                .email(null)
                .detail(null)
                .avatar(null)
                .followNumber(0)
                .role(role)
                .status(Status.ACTIVE)
                .build();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedBy(registerDto.getFullName());
        user.setUpdatedBy(registerDto.getFullName());
        return userRepository.save(user);
    }


    public Optional<User> findByNameActive(String username){
        return userRepository.findByUsernameAndStatus(username, Status.ACTIVE);
    }

    public Optional<User> findByIdActive(Long id){
        return userRepository.findByIdAndStatus(id, Status.ACTIVE);
    }

//    public Page<User> findAll(int page, int limit){
//        return userRepository.findAll(PageRequest.of(page, limit));
//    }

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getUsername() != null && searchBody.getUsername().length() > 0 ){
            specification = specification.and(new UserSpecification(new SearchCriteria("username", EQUALS, searchBody.getUsername())));
        }
        if (searchBody.getFullName() != null && searchBody.getFullName().length() > 0 ){
            specification = specification.and(new UserSpecification(new SearchCriteria("fullName", EQUALS, searchBody.getFullName())));
        }
        if (searchBody.getEmail() != null && searchBody.getEmail().length() > 0 ){
            specification = specification.and(new UserSpecification(new SearchCriteria("email", EQUALS, searchBody.getEmail())));
        }
        if (searchBody.getPhone() != null && searchBody.getPhone().length() > 0){
            specification = specification.and(new UserSpecification(new SearchCriteria("phone",EQUALS, searchBody.getPhone())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            log.info("check start: " + orderSearchBody.getStart() );
//            log.info("Check Start begin" + searchBody.getStart());

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
//            log.info("check start convert date: " + date );
            specification = specification.and(new UserSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new UserSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<User> pageUser = userRepository.findAll(specification,pageable);
        List<User> orderList = pageUser.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageUser.getNumber() + 1);
        responses.put("totalItems",pageUser.getTotalElements());
        responses.put("totalPage",pageUser.getTotalPages());
        return responses;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmailAndStatus(email, Status.ACTIVE);
    }

}

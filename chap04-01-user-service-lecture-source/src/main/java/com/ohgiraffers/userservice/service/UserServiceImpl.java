package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.aggregate.UserEntity;
import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registUser(UserDTO userDTO) {
        /* 설명. userId가 비어있는 상태인데 UUID 를 활용하여 서버 측에서 최원의 고유한 아이디를 생성한다. */
        userDTO.setUserId(UUID.randomUUID().toString());

        /* 설명. 필드값이 정확히 일치할 때만 매칭하기 위해 STRICT 모드 상태로 modelMapper 를 설정한다. (STANDARD -> STRICT */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

        /* 설명. spring security 모듈 추가 후 진행하므로 security 설정도 추가한다. */
        /* 설명. 사용자가 입력했던 암호를 꺼내와서 encrypt 한다 */
//        userEntity.setEncryptedPwd("암호화된 비밀번호");
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);
    }

    /* 설명. UserDetailsService 인터페이스 상속 이후
     *  DB에서 로그인 사용자 정보 조회 후 UserDetails 타입으로 반환하는 기능 구현
    * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 일단 조회해온다.
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email + "아이디의 유저는 존재하지 않음");
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());

        // UserDetails 타입으로 맞춰서 반환해주기만 하면
        // 그 다음 이어질 raw/encrypted pwd 대조 작업은 내부적으로 해준다.
    }
}

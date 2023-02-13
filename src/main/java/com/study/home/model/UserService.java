package com.study.home.model;

import com.study.home.dto.UserRequestDto;
import com.study.home.entity.User;
import com.study.home.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public int selectUserId(final UserRequestDto params) {
        return userRepository.findByUserId(params.getUserId());
    }

    @Transactional
    public Long saveUser(final UserRequestDto params) {
        User entity = userRepository.save(params.toEntity());
        return entity.getUserSeq();
    }
}

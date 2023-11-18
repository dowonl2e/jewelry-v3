package com.jewelry.user.service.impl;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.user.domain.UserTO;
import com.jewelry.user.domain.UserVO;
import com.jewelry.user.mapper.UserMapper;
import com.jewelry.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;

	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public Map<String, Object> findAllUser(UserTO to) {
		Map<String, Object> response = new HashMap<>();

		to.setTotalcount(userMapper.selectUserListCount(to));
		response.put("list", userMapper.selectUserList(to));
		response.put("params", to);

		return response;
	}
	
	@Transactional
	@MenuAuthAnt
	@Override
	public String insertUser(UserTO to) {
		String result = "fail";
		try {
			result = userMapper.insertUser(to) > 0 ? "success" : "fail";
		}
		catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result = "fail";
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public UserVO findUser(UserTO to) {
		return userMapper.selectUser(to.getUser_id());
	}

	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public UserVO findUser(String userId) {
		return userMapper.selectUser(userId);
	}

	@Transactional
	@MenuAuthAnt
	@Override
	public String updateUser(UserTO to) {
		String result = "fail";
		try {
			result = userMapper.updateUser(to) > 0 ? "success" : "fail";
		}
		catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result = "fail";
		}
		return result;
	}
}

package com.wwc.project;

import com.wwc.project.bean.po.UserPo;
import com.wwc.project.dao.imp.CbsDaoImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectApplicationTests {
	@Autowired
    CbsDaoImp getUsersDaoImp;

	@Test
	void abc() throws Exception {
		System.out.println(getUsersDaoImp.queryUsers().get(0).getUserName());
	}

	@Test
	void abc2() throws Exception {
		List<UserPo> userList = getUsersDaoImp.queryUsers();
		//System.out.println(getUsersDaoImp.queryUser(userList.get(1)).get(0));
	}

}

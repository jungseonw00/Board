package com.study.pmis;

import com.study.pmis.dao.ConfmProcessManageDao;
import com.study.pmis.vo.ConfmProcessManageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PmisTest {

	@Autowired
	private ConfmProcessManageDao confmProcessManageDao;

	@Test
	void problemPerson() {
		ConfmProcessManageVo confmProcessManageVo = new ConfmProcessManageVo();
		confmProcessManageVo.setNm("정선우");
		confmProcessManageVo.setIhidnum("[cmgCFbqMd8aeNMz5eIL5Yg==");
		List<ConfmProcessManageVo> list = confmProcessManageDao.selectProblemPerson(confmProcessManageVo);
		for (ConfmProcessManageVo vo: list) {
			System.out.println("vo = " + vo);
		}
	}
}

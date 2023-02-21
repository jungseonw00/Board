package com.study.pmis.dao;

import com.study.pmis.vo.ConfmProcessManageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfmProcessManageDao {
	List<ConfmProcessManageVo> selectProblemPerson(ConfmProcessManageVo confmProcessManageVo);
	int updateProblemPerson(ConfmProcessManageVo confmProcessManageVo);
}

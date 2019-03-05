package com.es.sys.mapper ;

import com.es.sys.pojo.Holiday;
import com.es.sys.pojo.HolidayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HolidayMapper {
	int countByExample(HolidayExample example);
	int deleteByExample(HolidayExample example);
	int deleteByPrimaryKey(Integer holidayId);
	int insert(Holiday record);
	int insertSelective(Holiday record);
	List<Holiday> selectByExample(HolidayExample example);
	Holiday selectByPrimaryKey(Integer holidayId);
	int updateByExampleSelective(@Param("record") Holiday record,@Param("example") HolidayExample example);
	int updateByPrimaryKeySelective(Holiday record);
	int updateByPrimaryKey(Holiday record);
}
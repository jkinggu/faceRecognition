package com.dx.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.batik.ext.awt.image.renderable.MorphologyRable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fang
 *
 * @Date 2019年4月29日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */

@Getter @Setter @ToString
public class StatisticsResult {
	
	private StatisticsCon statisticsCon;
	
	private Integer moralPass;
	private Integer moralFail;
	private Integer moralUnauthorized;
	
	private Integer abilityPass;
	private Integer abilityFail;
	private Integer abilityUnauthorized;
	
	private Integer skillsPass;
	private Integer skillsFail;
	private Integer skillsUnauthorized;
	
	

}

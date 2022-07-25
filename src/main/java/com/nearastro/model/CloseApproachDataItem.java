package com.nearastro.model;

import lombok.Data;

@Data
public class CloseApproachDataItem{
	private RelativeVelocity relativeVelocity;
	private String orbitingBody;
	private String closeApproachDate;
	private long epochDateCloseApproach;
	private String closeApproachDateFull;
	private MissDistance missDistance;
}
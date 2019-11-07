package com.major13.collector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanC {

    private Integer creditorId;
    private Integer projectId;
    private String paramCode;
    private Integer pariod;
    private Double valueOrg;
    private Double valueUsd;

}

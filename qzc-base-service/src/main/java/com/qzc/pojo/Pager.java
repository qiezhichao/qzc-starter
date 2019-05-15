package com.qzc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pager {

    private int pageNum = 1;

    private int pageSize = 50;

    private boolean isQueryTotalCount = false; // 是否查询总条数
}

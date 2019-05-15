package com.qzc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Sorter {

    private String orderField = "id"; // 排序字段

    private Sort.Direction direction = Sort.Direction.DESC; // 排序方向（升序/降序）
}

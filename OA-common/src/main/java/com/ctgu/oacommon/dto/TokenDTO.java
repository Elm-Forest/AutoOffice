package com.ctgu.oacommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhang Jinming
 * @create 17/6/2022 下午5:08+
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private Long userId;

    private String username;

    private Integer role;

    private Integer rights;
}

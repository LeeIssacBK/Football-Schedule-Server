package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SeasonDto extends ApiResponse {

    List<Integer> response;

}

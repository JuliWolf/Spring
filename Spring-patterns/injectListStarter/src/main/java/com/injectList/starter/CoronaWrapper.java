package com.injectList.starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoronaWrapper {
  private Object value;
  private Boolean corona;
}

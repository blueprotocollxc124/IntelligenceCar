package org.linkworld.persist.emtity;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserPattern {

    private BigInteger userId;

    private String patternName;
}

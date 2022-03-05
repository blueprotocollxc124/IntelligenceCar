package org.linkworld.check.sequence;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

import org.linkworld.check.BeginTimeCheck;
import org.linkworld.check.DatetimeChoiceCheck;
import org.linkworld.check.PatternNameCheck;
import org.linkworld.check.TagCheck;

import javax.validation.GroupSequence;

@GroupSequence({PatternNameCheck.class, DatetimeChoiceCheck.class, BeginTimeCheck.class, TagCheck.class})
public interface PatternDTOSequence {
}

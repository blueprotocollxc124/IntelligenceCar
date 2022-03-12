package org.linkworld.persist.emtity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId("id")
    BigInteger id;

    @TableField("password")
    String password;
}

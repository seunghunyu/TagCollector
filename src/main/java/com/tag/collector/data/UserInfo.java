package com.tag.collector.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    private String password;


}

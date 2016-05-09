package com.dkd.framework.dao;

import com.dkd.framework.entity.NoNoBank;
import com.dkd.framework.entity.NonoUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NonoUserDao {
    public List<NonoUser> select(Map<String, Object> reqMap);

    public NonoUser selectByUserId(String userId);

    public void save(NonoUser nonoUser);

    public int selectMaxUserId();
}

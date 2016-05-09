package com.dkd.framework.dao;

import com.dkd.framework.entity.BirdProject;
import com.dkd.framework.entity.BirdUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BirdUserDao {
    public void saveBirdUser(BirdUser birdUser);

    public BirdUser getBirdUserByUserId(BirdUser birdUser);

    public List<BirdUser> getList(Map<String, Object> reqMap);

    public void updateBirdUser(BirdUser birdUser);
}

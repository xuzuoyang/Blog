package com.dkd.framework.dao;

import com.dkd.framework.entity.BirdProject;
import com.dkd.framework.entity.NonoUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BirdProjectDao {
    public void saveBirdProject(BirdProject birdProject);

    public BirdProject getBirdProjectByProjectNo(BirdProject birdProject);

    public List<BirdProject> getList(Map<String, Object> reqMap);

    public void updateBirdProject(BirdProject birdProject);
}

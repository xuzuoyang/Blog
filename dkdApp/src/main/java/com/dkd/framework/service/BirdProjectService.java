package com.dkd.framework.service;

import com.dkd.framework.entity.BirdProject;
import net.sf.json.JSONArray;

public interface BirdProjectService {
    public void saveBirdproject(BirdProject birdProject);

    public void synBirdDetail();

    public void synBirdUserDetail();

    void saveBirdproject(JSONArray birdListByApi);
}

package com.dkd.schedule;

import com.dkd.framework.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ScheduleService {
    @Autowired
    private SpiderService spiderService;

    public void getSynNonoBankData() {
        //spiderService.getSynNonoBankData();
    }

    public void getSynNonoBankDetailData() {
        //spiderService.updateNonoBankDetail();
    }

    public void synUser() {
        spiderService.synUser();
    }

    public void synBird() {
        //spiderService.getBirdList();
        //spiderService.synBirdDetail();
        spiderService.synBird();
    }
}
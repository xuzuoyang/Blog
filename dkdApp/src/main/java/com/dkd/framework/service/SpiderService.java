package com.dkd.framework.service;

public interface SpiderService {
    public void getSynNonoBankData(int startPageNo, int endPageNo, int pageSize);

    public void updateNonoBankDetail(int startPageNo, int endPageNo, int pageSize);

    public void synUser();

    public void getBirdList();

    public void synBirdDetail();

    public void synBirdUserDetail();

    public void synBird();

    public void getBirdListByApi();

}

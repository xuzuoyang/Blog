package com.dkd.framework.dao;

import com.dkd.framework.entity.NoNoBank;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NonoBankDao {
    public List<NoNoBank> select(Map<String, Object> reqMap);

    public Long selectCount(Map<String, Object> selectMap);

    public NoNoBank selectByProjectNo(Map<String, Object> map);

    public void saveNonoBank(NoNoBank noNoBank);

    public void update(NoNoBank nonoBank);
}

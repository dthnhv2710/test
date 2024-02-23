package com.investing.springkafka.service.impl;


import com.investing.springkafka.dao.IMstSecDao;
import com.investing.springkafka.dto.request.MstSec;
import com.investing.springkafka.service.IMstService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class MstServiceImpl implements IMstService {

    private IMstSecDao mstSecDao;

    @Override
    public List<MstSec> mstSecGetList(MstSec body) throws Exception {
        return mstSecDao.getList(body);
    }
}

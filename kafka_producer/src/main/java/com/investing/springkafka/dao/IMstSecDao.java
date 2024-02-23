package com.investing.springkafka.dao;



import com.investing.springkafka.dto.request.MstSec;

import java.util.List;

public interface IMstSecDao {
    List<MstSec> getList(MstSec obj) throws Exception;
}
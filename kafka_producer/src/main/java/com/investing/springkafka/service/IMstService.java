package com.investing.springkafka.service;

import com.investing.springkafka.dto.request.MstSec;

import java.util.List;


public interface IMstService {

    List<MstSec> mstSecGetList(MstSec body) throws Exception;

}

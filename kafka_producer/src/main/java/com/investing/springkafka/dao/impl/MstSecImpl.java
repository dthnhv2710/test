package com.investing.springkafka.dao.impl;

import com.investing.springkafka.dao.IMstSecDao;
import com.investing.springkafka.dto.request.MstSec;
import com.investing.springkafka.util.CompareUtil;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class MstSecImpl implements IMstSecDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MstSec> getList(MstSec mstSec) {
        List<MstSec> resultsData = new ArrayList<>();
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
        simpleJdbcCall.withProcedureName("MstSecGetList")
                .returningResultSet("MstSec", BeanPropertyRowMapper.newInstance(MstSec.class));
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("UserId", "")
                .addValue("SecCd", mstSec.getSecCd() == null ? "": mstSec.getSecCd())
                .addValue("SecType", mstSec.getSecType() == 0 ? 0: mstSec.getSecType())
                .addValue("SecSName", mstSec.getSecSName() == null ? "": mstSec.getSecSName())
                .addValue("SecName", mstSec.getSecName() == null ? "": mstSec.getSecName())
                .addValue("MarketCd", mstSec.getMarketCd() == null ? "": mstSec.getMarketCd())
                .addValue("Status", mstSec.getStatus() == 0 ? 0: mstSec.getStatus());

        Map<String, Object> out = simpleJdbcCall.execute(params);

        List<MstSec> mstSecs = (List<MstSec>) out.get("MstSec");
        if (!CompareUtil.isEqualsNull(mstSecs) && mstSecs.size() > 0) {
            resultsData = mstSecs;
        }
        return resultsData;
    }
}

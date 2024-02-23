package com.investing.springkafka.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MstSec {

    private String secCd;

    private int secType;

    private String secSName;

    private String secName;

    private Integer secClass;

    private Double capitalValue;

    private Double listedQty;

    private Double foreignMaxQty;

    private String marketCd;

    private int tradingLot;

    private int status;

    private String remarks;

    private String createdUserId;

    private Date createdTime;

    private String updatedUserId;

    private Date updatedTime;

    private Double parValue;

    private Double maxRoom;

    private Double CalAssetRate;
}

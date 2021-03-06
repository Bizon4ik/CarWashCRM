package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.view.GeneralStatInCarWash;
import biz.podoliako.carwash.view.owner.statistic.StatisticForAdminMainPage;
import biz.podoliako.carwash.view.statistic.OrderForDetailStatisticInBox;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticService {

    Date addToDateTime(Date date, Integer hour, Integer min);

    GeneralStatInCarWash getGeneralStatForCarWash(Date from, Date to, Integer washId);

    void validateBoxNumber(Integer washId, Integer boxNumber);

    List<OrderForDetailStatisticInBox> getDetailStatForBox(Integer washId, Integer boxNumber, Date from, Date to);

    Map<CarWash,StatisticForAdminMainPage> getStatisticForAdminMainPage(Integer ownerId);
}

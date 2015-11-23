package biz.podoliako.carwash.view.owner;


import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.view.owner.statistic.StatisticForAdminMainPage;

import java.util.Map;

public class AdminMainPage {
    private Map<CarWash, StatisticForAdminMainPage> carWashesStatistic;

    public Map<CarWash, StatisticForAdminMainPage> getCarWashesStatistic() {
        return carWashesStatistic;
    }

    public void setCarWashesStatistic(Map<CarWash, StatisticForAdminMainPage> carWashesStatistic) {
        this.carWashesStatistic = carWashesStatistic;
    }
}

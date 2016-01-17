package biz.podoliako.carwash.view.owner.statistic;


public class StatisticForAdminMainPage {
    private Integer CarWashId;
    private Integer quantityCurrentOrders;

    public Integer getCarWashId() {
        return CarWashId;
    }

    public void setCarWashId(Integer carWashId) {
        CarWashId = carWashId;
    }

    public Integer getQuantityCurrentOrders() {
        return quantityCurrentOrders;
    }

    public void setQuantityCurrentOrders(Integer quantityCurrentOrders) {
        this.quantityCurrentOrders = quantityCurrentOrders;
    }
}

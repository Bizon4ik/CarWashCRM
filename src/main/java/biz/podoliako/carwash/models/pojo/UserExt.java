package biz.podoliako.carwash.models.pojo;


import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.entity.UserCompensation;

import java.util.Date;
import java.util.Set;


public class UserExt extends User {


    private Set<Integer> carWashPermissionSet;

    private UserCompensation compensation;

    private String login = null;

    public Set<Integer> getCarWashPermissionSet() {
        return carWashPermissionSet;
    }

    public void setCarWashPermissionSet(Set<Integer> carWashPermissionSet) {
        this.carWashPermissionSet = carWashPermissionSet;
    }

    public UserCompensation getCompensation() {
        return compensation;
    }

    public void setCompensation(UserCompensation compensation) {
        this.compensation = compensation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserExt userExt = (UserExt) o;

        if (!super.getId().equals(userExt.getId())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId().hashCode();
        return result;
    }
}

package biz.podoliako.carwash.services.impl;

import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.services.AuthorizationService;
import biz.podoliako.carwash.models.pojo.UserExt;
import biz.podoliako.carwash.services.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Component("AuthorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private DaoFactory daoFactory;


    @Autowired
    private MD5 md5;

    @Override
    public UserExt getUser(String login, String password) throws SQLException {
        password = MD5.hashing(password);
        Integer userId = daoFactory.getUserDao().getUserIdByLogPass(login, password);

        UserExt userExt;

        if (userId == null) {
            userExt = null;
        }else {
            userExt = daoFactory.getUserDao().getUserbyId(userId);
            userExt.setCarWashPermissionSet(daoFactory.getUserDao().getUserPermission(userId));
        }

        return userExt;
    }

    @Override
    public Integer getCarWashOwnerId(String login, String password) {
        return null;
    }

    @Override
    public Integer getBusinessPartnerId(String login, String password) {
        return null;
    }

    @Override
    public Integer getManagerId(String login, String password) {
        return null;
    }


    @Override
    public void setBusinessPartnerSession(Integer businessPartnerId, Integer ownerId) {

    }

    @Override
    public void setManagerSession(Integer managerId, Integer ownerId) {

    }

    @Override
    public void setUserSession(UserExt userExt, HttpSession session) {
        session.setAttribute("CurrentCarWashUser", userExt);
    }

    @Override
    public void setChoosenCarWashSession(Integer carWashId, HttpSession session) {
        session.setAttribute("ChoosenCarWashId", carWashId);
    }
}

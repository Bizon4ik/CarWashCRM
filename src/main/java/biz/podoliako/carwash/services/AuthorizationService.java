package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.pojo.UserExt;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface AuthorizationService {

    UserExt getUser(String login, String password) throws SQLException;

    Integer getCarWashOwnerId (String login, String password);

    Integer getBusinessPartnerId (String login, String password);

    Integer getManagerId (String login, String password);

    void setBusinessPartnerSession(Integer businessPartnerId, Integer ownerId);

    void setManagerSession(Integer managerId, Integer ownerId);


    void setUserSession(UserExt userExt, HttpSession session);


    void setChoosenCarWashSession(Integer carWashId, HttpSession session);
}

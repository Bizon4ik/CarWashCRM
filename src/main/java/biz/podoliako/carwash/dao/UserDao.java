package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.pojo.UserExt;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.services.entity.WasherManInBoxWithRate;
import biz.podoliako.carwash.view.WasherManInBoxWithName;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UserDao  {

    UserExt getUserbyId(Integer id) throws SQLException;

    UserExt authorizeUser(String login, String password);

    boolean isLoginExist(String login) throws SQLException;

    void addUserWithLogin(AddUserForm userForm) throws SQLException;

    void addUserWithOutLogin(AddUserForm userForm) throws SQLException;

    List<UserExt> selectAllUserInCarWash(Integer carWashid) throws SQLException;

    Integer getUserIdByLogPass(String login, String password) throws SQLException;

    Set<Integer> getUserPermission(Integer userId) throws SQLException;

    Set<UserExt> selectAllWasherManInCarWash(Integer washId);

    void addWasherManInBox(WasherManInBox washerManInBox);

    Set<WasherManInBoxWithName> selectWasherManInBoxWithName(Integer carWashId, int box);

    Set<WasherManInBoxWithName> selectAvailableAndCurrentWasherManInBox(Integer washId, Integer boxNum);

    Set<WasherManInBox> selectAllWasherManInBox(Integer washId, Integer boxNum);

    Set<Integer> selectUserIdsWasherManInBox(Integer washId, Integer boxNum);

    void insertFinishTimeForWasherManInBox(Integer userId, Date date);

    Set<WasherManInBoxWithRate> selectAllWasherManInBoxWithRate(Integer washId, Integer boxNumber);

    /*-------------------- Persistance --------------------------- */

    public User find (Integer id);

    User persist(User user);
}

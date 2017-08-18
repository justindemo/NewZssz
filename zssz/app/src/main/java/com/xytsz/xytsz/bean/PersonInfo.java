package com.xytsz.xytsz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/1.
 *
 */
public class PersonInfo implements Serializable {
    /**
     * m_bDirty : true
     * m_objKey :
     * subEntites : []
     * _department_id : 1
     * _headurl : http://110.173.0.231:10003/headimg/bbb.jpg
     * _id : 9
     * _ison : 1
     * _jurisdiction : 1
     * _login_id : xyt
     * _lotcode : 1
     * _password : 49BA59ABBE56E057
     * _role_id : 1
     * _sex : 男
     * _taskison : 1
     * _telephone : 1364564564
     * _username : 向阳天
     */

    private boolean m_bDirty;
    private String m_objKey;
    private int _department_id;
    private String _headurl;
    private int _id;
    private int _ison;
    private int _jurisdiction;
    private String _login_id;
    private int _lotcode;
    private String _password;
    private int _role_id;
    private String _sex;
    private int _taskison;
    private String _telephone;
    private String _username;
    private List<?> subEntites;

    public boolean isM_bDirty() {
        return m_bDirty;
    }

    public void setM_bDirty(boolean m_bDirty) {
        this.m_bDirty = m_bDirty;
    }

    public String getM_objKey() {
        return m_objKey;
    }

    public void setM_objKey(String m_objKey) {
        this.m_objKey = m_objKey;
    }

    public int get_department_id() {
        return _department_id;
    }

    public void set_department_id(int _department_id) {
        this._department_id = _department_id;
    }

    public String get_headurl() {
        return _headurl;
    }

    public void set_headurl(String _headurl) {
        this._headurl = _headurl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_ison() {
        return _ison;
    }

    public void set_ison(int _ison) {
        this._ison = _ison;
    }

    public int get_jurisdiction() {
        return _jurisdiction;
    }

    public void set_jurisdiction(int _jurisdiction) {
        this._jurisdiction = _jurisdiction;
    }

    public String get_login_id() {
        return _login_id;
    }

    public void set_login_id(String _login_id) {
        this._login_id = _login_id;
    }

    public int get_lotcode() {
        return _lotcode;
    }

    public void set_lotcode(int _lotcode) {
        this._lotcode = _lotcode;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public int get_role_id() {
        return _role_id;
    }

    public void set_role_id(int _role_id) {
        this._role_id = _role_id;
    }

    public String get_sex() {
        return _sex;
    }

    public void set_sex(String _sex) {
        this._sex = _sex;
    }

    public int get_taskison() {
        return _taskison;
    }

    public void set_taskison(int _taskison) {
        this._taskison = _taskison;
    }

    public String get_telephone() {
        return _telephone;
    }

    public void set_telephone(String _telephone) {
        this._telephone = _telephone;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public List<?> getSubEntites() {
        return subEntites;
    }

    public void setSubEntites(List<?> subEntites) {
        this.subEntites = subEntites;
    }


   /* *//**
     * m_bDirty : true
     * m_objKey :
     * subEntites : []
     * _department_id : 0
     * _id : 2
     * _ison : 1
     * _jurisdiction : 1
     * _login_id : wmm
     * _lotcode : 1
     * _password : 49BA59ABBE56E057
     * _role_id : 1
     * _sex : 男
     * _taskison : 1
     * _telephone : 13888888888
     * _username : 王蒙蒙
     *//*

    private boolean m_bDirty;
    private String m_objKey;
    private int _department_id;
    private int _id;
    private int _ison;
    private int _jurisdiction;
    private String _login_id;
    private int _lotcode;
    private String _password;
    //角色
    private int _role_id;
    private String _sex;
    private int _taskison;
    private String _telephone;
    private String _username;
    private List<?> subEntites;

    public boolean isM_bDirty() {
        return m_bDirty;
    }

    public void setM_bDirty(boolean m_bDirty) {
        this.m_bDirty = m_bDirty;
    }

    public String getM_objKey() {
        return m_objKey;
    }

    public void setM_objKey(String m_objKey) {
        this.m_objKey = m_objKey;
    }

    public int get_department_id() {
        return _department_id;
    }

    public void set_department_id(int _department_id) {
        this._department_id = _department_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_ison() {
        return _ison;
    }

    public void set_ison(int _ison) {
        this._ison = _ison;
    }

    public int get_jurisdiction() {
        return _jurisdiction;
    }

    public void set_jurisdiction(int _jurisdiction) {
        this._jurisdiction = _jurisdiction;
    }

    public String get_login_id() {
        return _login_id;
    }

    public void set_login_id(String _login_id) {
        this._login_id = _login_id;
    }

    public int get_lotcode() {
        return _lotcode;
    }

    public void set_lotcode(int _lotcode) {
        this._lotcode = _lotcode;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public int get_role_id() {
        return _role_id;
    }

    public void set_role_id(int _role_id) {
        this._role_id = _role_id;
    }

    public String get_sex() {
        return _sex;
    }

    public void set_sex(String _sex) {
        this._sex = _sex;
    }

    public int get_taskison() {
        return _taskison;
    }

    public void set_taskison(int _taskison) {
        this._taskison = _taskison;
    }

    public String get_telephone() {
        return _telephone;
    }

    public void set_telephone(String _telephone) {
        this._telephone = _telephone;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public List<?> getSubEntites() {
        return subEntites;
    }

    public void setSubEntites(List<?> subEntites) {
        this.subEntites = subEntites;
    }*/



}

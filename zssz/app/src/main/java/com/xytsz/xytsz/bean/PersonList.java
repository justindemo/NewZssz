package com.xytsz.xytsz.bean;

import java.util.List;

/**
 * Created by admin on 2017/4/1.
 */
public class PersonList {


    private List<PersonListBean> personList;

    public List<PersonListBean> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonListBean> personList) {
        this.personList = personList;
    }

    public static class PersonListBean {
        /**
         * id : 1
         * name : 赵忠茂
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

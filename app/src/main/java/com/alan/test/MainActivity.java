package com.alan.test;

import androidx.fragment.app.Fragment;

import com.alan.db.DBExecutor;
import com.alan.framework.activity.HomeBaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public class MainActivity extends HomeBaseActivity {

    @Override
    protected void initView() {
        super.initView();
        Person person = new Person();
        person.setName("王大治");
        person.setAge(11);
        person.setCreateTime(11111);
        person.setSid(111111);
        DBExecutor.insert(person);
        DBExecutor.replace(person);
        DBExecutor.update(person);
        DBExecutor.delete(person, person.getName());
        DBExecutor.deleteAll(Person.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "王大治");
        map.put("age", "11");
        Person p = DBExecutor.find(Person.class, map);
        String sql = "select * from person where name='王大治'";
        p = DBExecutor.find(Person.class, sql);

        List<Person> list = DBExecutor.findList(Person.class, map);

    }

    @Override
    protected Fragment[] getFragmentArray() {
        return new Fragment[0];
    }
}

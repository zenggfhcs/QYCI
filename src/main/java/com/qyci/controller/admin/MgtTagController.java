package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.dao.*;
import com.qyci.entity.CompTag;
import com.qyci.entity.CompTeachTag;
import com.qyci.entity.TopicsTag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/tag")
public class MgtTagController {
    @GetMapping("/tag-comp")
    public String comp(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTagDao tagDao = session.getMapper(CompTagDao.class);

        List<CompTag> parents = tagDao.selectAllParents();
        model.put("parents", parents);
        List<CompTag> sons = tagDao.selectAll();
        model.put("sons", sons);

        session.close();
        return "/admin/tag/tag-comp::content";
    }

    @ResponseBody
    @PostMapping("/tag-comp/delete")
    public Object deleteCompTag(@RequestBody CompTag tag, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTagsInfoDao infoDao = session.getMapper(CompTagsInfoDao.class);
        infoDao.deleteByTagId(tag.getId());
        session.commit();

        CompTagDao tagDao = session.getMapper(CompTagDao.class);
        int value = 0;
        //
        if (tag.getId() % 100000 == 0) {
            tagDao.deleteByParents(tag);
            tag.setParents(null);
            value = tagDao.updateParents(tag);
            session.commit();
        }
        value += tagDao.deleteByPrimaryKey(tag.getId());

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }

        return JSON.toJSONString(value);
    }

    @GetMapping("/tag-teach")
    public String teach(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);

        List<CompTeachTag> parents = tagDao.selectAllParents();
        model.put("parents", parents);
        List<CompTeachTag> sons = tagDao.selectAll();
        model.put("sons", sons);

        session.close();
        return "/admin/tag/tag-teach::content";
    }

    @ResponseBody
    @PostMapping("/tag-teach/delete")
    public Object deleteTeachTag(@RequestBody CompTeachTag tag, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachTagsInfoDao infoDao = session.getMapper(CompTeachTagsInfoDao.class);
        infoDao.deleteByTagId(tag.getId());
        session.commit();

        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);
        int value = 0;

        if (tag.getId() % 100000 == 0) {
            tagDao.deleteByParents(tag);
            tag.setParents(null);
            value = tagDao.updateParents(tag);
            session.commit();
        }
        value *= tagDao.deleteByPrimaryKey(tag.getId());

        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }

    @GetMapping("/tag-topic")
    public String topic(ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsTagDao tagDao = session.getMapper(TopicsTagDao.class);

        List<TopicsTag> tags = tagDao.selectAll();
        model.put("tags", tags);

        session.close();
        return "/admin/tag/tag-topic::content";
    }

    @ResponseBody
    @PostMapping("/tag-topic/delete")
    public Object deleteTopicTag(@RequestBody TopicsTag tag, ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsTagDao tagDao = session.getMapper(TopicsTagDao.class);

        int value = tagDao.deleteByPrimaryKey(tag.getId());

        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }

    @ResponseBody
    @PostMapping("/topic/insert")
    public Object insertTopicTag(@RequestBody TopicsTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        TopicsTagDao tagDao = session.getMapper(TopicsTagDao.class);
        int value;
        value = tagDao.insert(tag);

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }

        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/topic/update")
    public Object updateTopicTag(@RequestBody TopicsTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        TopicsTagDao tagDao = session.getMapper(TopicsTagDao.class);
        int value;
        value = tagDao.updateByPrimaryKey(tag);

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/teach/insert")
    public Object insertTeachTag(@RequestBody CompTeachTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);
        int value;
        if (tag.getId() == null) {// insert parents
            int id = tagDao.selectParentsCount() + 1;
            tag.setId(id * 100000);
            value = tagDao.insert(tag);
            tag.setParents(tag.getId());
            value *= tagDao.updateParents(tag);
        }
        else {
            int id = tagDao.selectSubsetCount(tag);
            tag.setParents(tag.getId());
            tag.setId(tag.getParents() + id);
            value = tagDao.insert(tag);
        }

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/teach/update")
    public Object updateTeachTag(@RequestBody CompTeachTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);
        int value;
        value = tagDao.updateNameByKey(tag);

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/comp/insert")
    public Object insertCompTag(@RequestBody CompTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        CompTagDao tagDao = session.getMapper(CompTagDao.class);
        int value;
        if (tag.getId() == null) {// insert parents
            int id = tagDao.selectParentsCount() + 1;
            tag.setId(id * 100000);
            value = tagDao.insert(tag);
            tag.setParents(tag.getId());
            value *= tagDao.updateParents(tag);
        }
        else {
            int id = tagDao.selectSubsetCount(tag);
            tag.setParents(tag.getId());
            tag.setId(tag.getParents() + id);
            value = tagDao.insert(tag);
        }

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/comp/update")
    public Object updateCompTag(@RequestBody CompTag tag, ModelMap model) {
        System.out.println(tag.getName() + " " + tag.getId());
        SqlSession session = MySession.getSession();
        CompTagDao tagDao = session.getMapper(CompTagDao.class);
        int value;
        value = tagDao.updateNameByKey(tag);

        session.commit();
        session.close();
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(1);
    }
}

package com.landasoft.taoj.service.impl;

import com.landasoft.taoj.common.pojo.*;
import com.landasoft.taoj.constant.IndexUserConstant;
import com.landasoft.taoj.mapper.TUserInfoMapper;
import com.landasoft.taoj.mypojo.IndexUser;
import com.landasoft.taoj.pojo.TUserInfo;
import com.landasoft.taoj.pojo.TUserInfoExample;
import com.landasoft.taoj.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人工客服相关Service接口实现
 * @author zhaoyuan
 * @date 2020,July 17
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private TUserInfoMapper userInfoMapper;

    @Override
    public LayuiIMInitResult getLayuiIMInitData(String adminUserName) {
        if(StringUtils.isBlank(adminUserName))
            return new LayuiIMInitResult("1","Must param adminUserName is black",null);

        TUserInfoExample example = new TUserInfoExample();
        TUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(adminUserName);

        List<TUserInfo> userInfoList = userInfoMapper.selectByExample(example);

        if(null == userInfoList || 0 == userInfoList.size())
            return new LayuiIMInitResult("1","No data was found",null);

        TUserInfo userInfo = userInfoList.get(0);

        //逻辑上我的信息
        LayuiIMMainPanelIndividual layuiIMMainPanelIndividual = new LayuiIMMainPanelIndividual();
        layuiIMMainPanelIndividual.setUsername(userInfo.getAlies());
        layuiIMMainPanelIndividual.setId(userInfo.getId());
        layuiIMMainPanelIndividual.setStatus("online");
        layuiIMMainPanelIndividual.setSign("一个平平常常的日子,细的雨丝夹着一星半点的雪花,正纷纷淋淋地向大地飘洒着。时令己快到凉蛰,雪当然再不会存留,往往还没等落地,就已经消失得无踪无影了。黄土高原严寒而漫长的冬天看来不要过去,但那真正温暖的春天还远远地没有到来");
        layuiIMMainPanelIndividual.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4272914372,1764472136&fm=26&gp=0.jpg");

        //好友信息
        List<LayuiIMMainPanelIndividual> list = new ArrayList<>();

        Map<String, IndexUser> indexUserMap = IndexUserConstant.getIndexUserMap();
        for (String key : indexUserMap.keySet()) {
            IndexUser indexUser = indexUserMap.get(key);
            list.add(new LayuiIMMainPanelIndividual(indexUser.getAlias(),indexUser.getId(),indexUser.getSign(),indexUser.getAvatar()));
        }

        LayuiIMFriendResult layuiIMFriendResult = new LayuiIMFriendResult();
        layuiIMFriendResult.setId("19940705");
        layuiIMFriendResult.setGroupname("门户用户");
        layuiIMFriendResult.setList(list);

        List<LayuiIMFriendResult> friendResults = new ArrayList<>();
        friendResults.add(layuiIMFriendResult);

        //群信息
        LayuiIMGroupResult layuiIMGroupResult = new LayuiIMGroupResult();
        layuiIMGroupResult.setId("19491001");
        layuiIMGroupResult.setGroupname("无聊群");
        layuiIMGroupResult.setAvatar("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1594973140&di=d6ab4f3b32e87794ddc81ae4f26efa7b&src=http://dingyue.ws.126.net/2019/05/07/01aef09e39ca45a38a0a00bf41ab671b.jpeg");

        List<LayuiIMGroupResult> groupResults = new ArrayList<>();
        groupResults.add(layuiIMGroupResult);

        //响应数据
        LayuiIMMainPanelDataResult layuiIMMainPanelDataResult = new
                LayuiIMMainPanelDataResult();
        layuiIMMainPanelDataResult.setMine(layuiIMMainPanelIndividual);
        layuiIMMainPanelDataResult.setFriend(friendResults);
        layuiIMMainPanelDataResult.setGroup(groupResults);

        LayuiIMInitResult layuiIMInitResult = new LayuiIMInitResult();
        layuiIMInitResult.setCode("0");
        layuiIMInitResult.setMsg("");
        layuiIMInitResult.setData(layuiIMMainPanelDataResult);

        return layuiIMInitResult;
    }
}

package com.example.myblog.dao;

import com.example.myblog.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;
}

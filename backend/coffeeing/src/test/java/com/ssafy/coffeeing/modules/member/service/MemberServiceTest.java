package com.ssafy.coffeeing.modules.member.service;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.MemberInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.NicknameChangeRequest;
import com.ssafy.coffeeing.modules.member.dto.ProfileImageChangeRequest;
import com.ssafy.coffeeing.modules.member.mapper.MemberMapper;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.member.util.MemberUtil;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RecordApplicationEvents
class MemberServiceTest extends ServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberUtil memberUtil;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @DisplayName("점수 증가시 저장된 점수가 증가한다.")
    @Test
    void Given_ExperienceRecord_When_AddExperienceEvent_Then_Success() {
        // given
        Member member = MemberTestDummy.createGeneralMember("닉네이무", "1234", "how@and.why");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ExperienceEvent(75, member.getId()));
        // then
        assertThat(member.getExperience()).isEqualTo(75);

    }

    @DisplayName("점수 증가시 레벨업을 실행하고 점수를 감소시킨다.")
    @Test
    void Given_ActivityConductedEvent_When_AddExperienceEvent_Then_Success() {
        // given
        Member member = MemberTestDummy.createGeneralMember("얍", "1234", "a@a.com");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ExperienceEvent(150, member.getId()));
        // then
        assertAll(
                () -> assertThat(member.getExperience()).isEqualTo(25),
                () -> assertThat(member.getMemberLevel()).isEqualTo(1)
        );
    }

    @DisplayName("마이페이지에서 회원정보 요청시 닉네임, 이미지만 반환한다.")
    @Test
    void Given_SecurityMemberContext_When_GetMemberInfo_Then_Success() {
        //given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        //when
        MemberInfoResponse memberBaseInfoResponse = memberService.getMemberInfo(generalMember.getId());

        //then
        assertThat(memberBaseInfoResponse).isEqualTo(
                MemberMapper.supplyBaseInfoResponseFrom(generalMember)
        );
    }

    @DisplayName("멤버의 경험치, 레벨, 레벨업까지 필요 경험치량을 반환한다.")
    @Test
    void Given_Member_When_GetExperienceInfo_Then_Success() {
        // given
        memberService.addExperience(new ExperienceEvent(150, generalMember.getId()));
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ExperienceInfoResponse experienceInfoResponse = memberService.getMemberExperience();

        // then
        assertEquals(
                experienceInfoResponse,
                MemberMapper.supplyExperienceInfoResponseOf(
                        generalMember,
                        memberUtil.calculateLevelUpExperience(
                                generalMember.getMemberLevel()
                        )
                )
        );
    }

    @DisplayName("멤버의 프로필 이미지를 변경한다.")
    @Test
    void Given_MemberProfileImageUrl_When_UpdateMemberProfileImage_Then_Success() {
        // given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        String newProfileImageUrl = "http://some-aws-directory:I23m52A93g39e";
        // when
        memberService.updateMemberProfileImage(new ProfileImageChangeRequest(newProfileImageUrl));
        // then
        assertThat(generalMember.getProfileImage()).isEqualTo(newProfileImageUrl);
    }

    @DisplayName("멤버 닉네임을 변경한다.")
    @Test
    void Given_MemberNickname_When_UpdateMemberNickname_Then_Success() {
        // given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        String newNickname = "제네럴 백";
        // when
        memberService.updateMemberNickname(new NicknameChangeRequest(newNickname));
        // then
        assertThat(generalMember.getNickname()).isEqualTo(newNickname);

    }



}

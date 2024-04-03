package com.found_404.funco.member.service;

import static com.found_404.funco.rank.domain.type.RankType.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.dto.MemberAssetInfo;
import com.found_404.funco.member.dto.MemberInfo;
import com.found_404.funco.member.dto.response.MemberResponse;
import com.found_404.funco.member.exception.MemberErrorCode;
import com.found_404.funco.member.exception.MemberException;
import com.found_404.funco.rank.domain.type.RankType;
import com.found_404.funco.rank.dto.response.RankResponse;
import com.found_404.funco.trade.dto.HoldingCoinsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final RedisTemplate<String, Object> rankZSetRedisTemplate;

	public MemberResponse readMember(Long loginMemberId, Long memberId) {
		List<HoldingCoinsDto> holdingCoinsDto = memberRepository.findHoldingCoinsByMemberId(memberId); // 보유 코인 정보
		MemberInfo memberInfo = memberRepository.findMemberInfoByMemberId(memberId);
		ZSetOperations<String, Object> zSetOperations = rankZSetRedisTemplate.opsForZSet();
		
		return MemberResponse.builder()
			.memberId(memberId)
			.nickname(memberInfo.nickname())
			.profileUrl(memberInfo.profileUrl())
			.introduction(memberInfo.introduction())
			.assetRank(getRankingByMemberId(memberId, ASSET, zSetOperations))
			.followingCashRank(getRankingByMemberId(memberId, FOLLOWER_CASH, zSetOperations))
			.memberAssetInfo(MemberAssetInfo.builder()
				.cash(memberInfo.cash())
				.coins(holdingCoinsDto)
				.build())
			.topCoins(memberRepository.findRecentTradedCoinByMemberId(memberId))
			.followerCash(memberRepository.getFollowingCashByMemberId(memberId))
			.followingCash(memberRepository.getFollowerCashByMemberId(memberId))
			.isFollow(memberRepository.isFollowedByMemberId(loginMemberId, memberId))
			.build();
	}

	private Long getRankingByMemberId(Long memberId, RankType rankType, ZSetOperations<String, Object> zSetOperations) {
		Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(rankType.toString(),
			0, -1);

		AtomicInteger index = new AtomicInteger(0); // 인덱스를 저장할 AtomicInteger 생성
		typedTuples.stream()
			.map(tuple -> (RankResponse)tuple.getValue()).filter(Objects::nonNull)
			.filter(rankResponse -> {
				index.incrementAndGet(); // 인덱스 증가
				return Objects.equals(rankResponse.member().id(), memberId); // 조건 확인
			})
			.findFirst()
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

		return index.longValue();
	}
}

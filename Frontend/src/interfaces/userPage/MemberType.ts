export interface TopCoinsType {
  ticker: string;
  createdAt: string;
}

export interface MemberType {
  memberId: number;
  nickname: string;
  profileUrl: string;
  introduction: string;
  assetRank: number;
  followingCashRank: number;
  memberAssetInfo: {
    cash: number;
    coins: {
      ticker: string;
      volume: number;
    }[];
  };
  followingCash: number;
  followerCash: number;
  isFollow: boolean;
  topCoins: TopCoinsType[];
}

export default MemberType;

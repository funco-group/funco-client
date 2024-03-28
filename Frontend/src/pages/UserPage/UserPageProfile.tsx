import {
  ProfileButton,
  ProfileDetailContainer,
  UserPageProfileContainer,
} from "./UserPageProfile.styled";
import { ComponentTitleH3 } from "./styled";
import MemberType from "@/interfaces/common/MemberType";

interface UserPageProfileProps {
  isCurrentUser: boolean;
  member: MemberType;
}

function UserPageProfile({ isCurrentUser, member }: UserPageProfileProps) {
  return (
    <UserPageProfileContainer>
      <ComponentTitleH3>프로필</ComponentTitleH3>
      <ProfileDetailContainer>
        <img src={member.profileUrl} alt="member-profile" />
        {isCurrentUser ? (
          <div>
            <ProfileButton>닉네임 수정</ProfileButton>{" "}
            <ProfileButton>한 줄 소개 수정</ProfileButton>
          </div>
        ) : (
          <ProfileButton>팔로우</ProfileButton>
        )}
      </ProfileDetailContainer>
    </UserPageProfileContainer>
  );
}

export default UserPageProfile;